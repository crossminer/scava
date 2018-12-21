package org.eclipse.scava.crossflow.runtime;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.jmx.DestinationViewMBean;
import org.eclipse.scava.crossflow.runtime.utils.ControlSignal;
import org.eclipse.scava.crossflow.runtime.utils.ControlSignal.ControlSignals;
import org.eclipse.scava.crossflow.runtime.utils.TaskStatus;
import org.eclipse.scava.crossflow.runtime.utils.TaskStatus.TaskStatuses;

import com.beust.jcommander.Parameter;

public abstract class Workflow {

	@Parameter(names = { "-name" }, description = "The name of the workflow")
	protected String name;
	protected Cache cache;
	
	@Parameter(names = { "-master" }, description = "IP of the master")
	protected String master = "localhost";
	
	@Parameter(names = { "-port" }, description = "Port of the master")
	protected int port = 61616;
	
	protected BrokerService brokerService;
	
	@Parameter(names = { "-instance" }, description = "The instance of the master (to contribute to)")
	protected String instanceId;
	
	@Parameter(names = {
	"-mode" }, description = "Must be master_bare, master or worker", converter = ModeConverter.class)
	protected Mode mode = Mode.MASTER;
	
	protected boolean createBroker = true;
	protected boolean cacheEnabled = true;
	private List<String> activeJobs = new ArrayList<String>();
	protected HashSet<Channel> activeChannels = new HashSet<Channel>();
	
	protected File inputDirectory = new File("").getAbsoluteFile();
	protected File outputDirectory = new File("").getParentFile();
	protected File tempDirectory = null;
	
	protected BuiltinChannel<TaskStatus> taskStatusTopic = null;
	protected BuiltinChannel<Object[]> resultsTopic = null;
	protected BuiltinChannel<ControlSignal> controlTopic = null;
	protected BuiltinChannel<FailedJob> failedJobsQueue = null;
	protected BuiltinChannel<InternalException> internalExceptionsQueue = null;
	
	protected List<FailedJob> failedJobs = null;
	protected List<InternalException> internalExceptions = null;
	
	// for master to keep track of active and terminated workers
	protected Collection<String> activeWorkerIds = new HashSet<String>();
	protected Collection<String> terminatedWorkerIds = new HashSet<String>();
	
	// excluded tasks from workers
	protected Collection<String> tasksToExclude = new LinkedList<String>();
	
	public void excludeTasks(Collection<String> tasks){
		tasksToExclude = tasks;
	}
	
	public void createBroker(boolean createBroker) {
		this.createBroker = createBroker;
	}
	
	protected Timer terminationTimer;
	boolean aboutToTerminate = false;
	protected boolean terminated = false;

	/**
	 * used to manually add local workers to master as they may be enabled too
	 * quickly to be registered using the control topic when on the same machine
	 */
	public void addActiveWorkerId(String id) {
		activeWorkerIds.add(id);
	}

	// terminate workflow on master after this time (ms) regardless of confirmation from workers
	private int terminationTimeout = 10000;

	public void setTerminationTimeout(int timeout) {
		terminationTimeout = timeout;
	}

	public int getTerminationTimeout() {
		return terminationTimeout;
	}
	
	public Workflow() {
		taskStatusTopic = new BuiltinChannel<TaskStatus>(this, "TaskStatusPublisher");
		resultsTopic = new BuiltinChannel<Object[]>(this, "ResultsBroadcaster");
		controlTopic = new BuiltinChannel<ControlSignal>(this, "ControlTopic");
		failedJobsQueue = new BuiltinChannel<FailedJob>(this, "FailedJobs", false);
		internalExceptionsQueue = new BuiltinChannel<InternalException>(this, "InternalExceptions", false);
		
		instanceId = UUID.randomUUID().toString();
	}
	
	protected void connect() throws Exception {
		
		if (tempDirectory == null) {
			tempDirectory = Files.createTempDirectory("crossflow").toFile();
		}
		taskStatusTopic.init();
		resultsTopic.init();
		controlTopic.init();
		failedJobsQueue.init();
		internalExceptionsQueue.init();
		
		activeChannels.add(taskStatusTopic);
		activeChannels.add(failedJobsQueue);
		activeChannels.add(internalExceptionsQueue);
		
		// XXX Should we be checking this queue (resultsBroadcaster) for termination?
		//activeQueues.add(resultsBroadcaster);
		// do not add control topic to activequeues as it has to be managed explicitly
		// in terminate
		// activeQueues.add(controlTopic);

		controlTopic.addConsumer(new BuiltinChannelConsumer<ControlSignal>() {

			@Override
			public void consume(ControlSignal signal) {
				// System.err.println("consumeControlTopic on " + getName() + " : " +
				// signal.getSignal() + " : " + signal.getSenderId());
				if (isMaster()) {
					switch (signal.getSignal()) {
					case ACKNOWLEDGEMENT:
						terminatedWorkerIds.add(signal.getSenderId());
						break;
					case WORKER_ADDED:
						activeWorkerIds.add(signal.getSenderId());
						break;
					case WORKER_REMOVED:
						activeWorkerIds.remove(signal.getSenderId());
						break;

					default:
						break;
					}

				} else {
					if (signal.getSignal().equals(ControlSignals.TERMINATION))
						try {
							terminate();
						} catch (Exception e) {
							unrecoverableException(e);
						}
				}

			}
		});
		

		// XXX if the worker sends this before the master is listening to this topic
		// this information is lost which affects termiantion
		if (!isMaster())
			controlTopic.send(new ControlSignal(ControlSignals.WORKER_ADDED, getName()));

		if (isMaster()) {
			taskStatusTopic.addConsumer(new BuiltinChannelConsumer<TaskStatus>() {

				@Override
				public void consume(TaskStatus status) {
					switch (status.getStatus()) {

						case INPROGRESS: {
							activeJobs.add(status.getCaller());
							cancelTermination();
							break;
						}
						case WAITING: {
							activeJobs.remove(status.getCaller());
							break;
						}
						default:
							break;
					}
				}

			});
			
			failedJobs = new ArrayList<>();
			failedJobsQueue.addConsumer(new BuiltinChannelConsumer<FailedJob>() {
				
				@Override
				public void consume(FailedJob failedJob) {
					failedJob.getException().printStackTrace();
					failedJobs.add(failedJob);
				}
			});
			
			internalExceptions = new ArrayList<>();
			internalExceptionsQueue.addConsumer(new BuiltinChannelConsumer<InternalException>() {
				
				@Override
				public void consume(InternalException internalException) {
					internalException.getException().printStackTrace();
					internalExceptions.add(internalException);
				}
			});
			
			terminationTimer = new Timer();
			
			terminationTimer.schedule(new TimerTask() {
				
				@Override
				public void run() {
					
					boolean canTerminate = activeJobs.size() == 0 && areChannelsEmpty();
					
					if (aboutToTerminate) {
						if (canTerminate) {
							terminate();
						}
					}
					else {
						aboutToTerminate = canTerminate;
					}
				}
			}, 0, 2000);
			
		}
	}
	

	
	public void cancelTermination() {
		aboutToTerminate = false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInstanceId() {
		return instanceId;
	}
	
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	
	public Cache getCache() {
		return cache;
	}

	public void setCache(Cache cache) {
		this.cache = cache;
	}

	public boolean isMaster() {
		return mode == Mode.MASTER || mode == Mode.MASTER_BARE;
	}

	public boolean isWorker() {
		return mode == Mode.MASTER || mode == Mode.WORKER;
	}
	
	public Mode getMode() {
		return mode;
	}
	
	public void setMaster(String master) {
		this.master = master;
	}
	
	public String getMaster() {
		return master;
	}
	
	public int getPort() {
		return port;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public String getBroker() {
		return "tcp://" + master + ":" + port;
	}
	
	public void stopBroker() throws Exception {
		brokerService.deleteAllMessages();
		brokerService.stopGracefully("", "", 1000, 1000);
		System.out.println("terminated broker (" + getName() + ")");
	}

	public void run() throws Exception {
		run(0);
	}
	public abstract void run(int delay) throws Exception;

	private synchronized boolean areChannelsEmpty() {
		
		try {
			for (Channel c : new ArrayList<>(activeChannels)) {
				for (String postId : c.getPhysicalNames()) {
					
					
					String destinationType = c.isBroadcast() ? "Topic" : "Queue";
					
					String url = "service:jmx:rmi:///jndi/rmi://" + master + ":1099/jmxrmi";
					JMXConnector connector = JMXConnectorFactory.connect(new JMXServiceURL(url));
					MBeanServerConnection connection = connector.getMBeanServerConnection();
					
					ObjectName channel = new ObjectName("org.apache.activemq:type=Broker,brokerName=" + master
							+ ",destinationType=" + destinationType + ",destinationName=" + postId);
	
					DestinationViewMBean mbView = MBeanServerInvocationHandler.newProxyInstance(connection, channel,
							DestinationViewMBean.class, true);
						
					try {
						if (!c.isBroadcast()) {
							if (mbView.getQueueSize() > 0) {
								return false;
							}
						} else { 
							if ( mbView.getInFlightCount() > 1 ) {
								return false;
							}
						}
					}
					catch (Exception ex) {
						// Ignore exception
					}
	
					connector.close();
	
				}
			}
		}
		catch (Exception ex) {
			unrecoverableException(ex);
			return true;
		}
		
		return true;
		
	}

	public synchronized void terminate() {
		
		if (terminated) return;
		
		try {
			// master graceful termination logic
			if (isMaster()) {
	
				// ask all workers to terminate
				controlTopic.send(new ControlSignal(ControlSignals.TERMINATION, getName()));
	
				long startTime = System.currentTimeMillis();
				// wait for workers to terminate or for the termination timeout
				while ((System.currentTimeMillis() - startTime) < terminationTimeout) {
					if (terminatedWorkerIds.equals(activeWorkerIds)) {
						System.out.println("all workers terminated, terminating master...");
						break;
					}
					Thread.sleep(100);
				}
				System.out.println("terminating master...");
	
			}
	
			// termination logic
			System.out.println("terminating workflow... (" + getName() + ")");
	
			// stop all channel connections
			for (Channel activeQueue : activeChannels) {
				activeQueue.stop();
			}
	
			resultsTopic.stop();
			
			if (isMaster()) {
				controlTopic.stop();
				System.out.println("createBroker: " + createBroker);
				if (createBroker) {
					stopBroker();
				}
			} else {
				controlTopic.send(new ControlSignal(ControlSignals.ACKNOWLEDGEMENT, getName()));
				controlTopic.stop();
			}
			terminated = true;
			System.out.println("workflow " + getName() + " terminated.");
		}
		catch (Exception ex) {
			// There is nothing to do at this stage
		}
	}
 	
	public boolean hasTerminated() {
		return terminated;
	}
	
	public BuiltinChannel<TaskStatus> getTaskStatusTopic() {
		return taskStatusTopic;
	}
	
	public BuiltinChannel<Object[]> getResultsTopic() {
		return resultsTopic;
	}
	
	public BuiltinChannel<ControlSignal> getControlTopic() {
		return controlTopic;
	}
	
	public BuiltinChannel<FailedJob> getFailedJobsQueue() {
		return failedJobsQueue;
	}
	
	public List<FailedJob> getFailedJobs() {
		return failedJobs;
	}
	
	public List<InternalException> getInternalExceptions() {
		return internalExceptions;
	}
	
	public void reportInternalException(Exception ex) {
		try {
			internalExceptionsQueue.send(new InternalException(ex, this.getName()));
		} catch (Exception e) {
			unrecoverableException(e);
		}
	}
	
	public void unrecoverableException(Exception ex) {
		ex.printStackTrace();
	}
	
	public void setTaskInProgess(Task caller) throws Exception {
		taskStatusTopic.send(new TaskStatus(TaskStatuses.INPROGRESS, caller.getId(), ""));
	}

	public void setTaskWaiting(Task caller) throws Exception {
		taskStatusTopic.send(new TaskStatus(TaskStatuses.WAITING, caller.getId(), ""));
	}

	public void setTaskBlocked(Task caller, String reason) throws Exception {
		taskStatusTopic.send(new TaskStatus(TaskStatuses.BLOCKED, caller.getId(), reason));
	}

	public void setTaskUnblocked(Task caller) throws Exception {
		taskStatusTopic.send(new TaskStatus(TaskStatuses.INPROGRESS, caller.getId(), ""));
	}

	public File getInputDirectory() {
		return inputDirectory;
	}

	public void setInputDirectory(File inputDirectory) {
		this.inputDirectory = inputDirectory;
	}

	public File getOutputDirectory() {
		return outputDirectory;
	}

	public void setOutputDirectory(File outputDirectory) {
		this.outputDirectory = outputDirectory;
	}

	public File getTempDirectory() {
		return tempDirectory;
	}

	public void setTempDirectory(File tempDirectory) {
		this.tempDirectory = tempDirectory;
	}
	
}
