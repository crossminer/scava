package org.eclipse.scava.crossflow.runtime;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.jmx.DestinationViewMBean;
import org.eclipse.scava.crossflow.runtime.utils.ControlSignal;
import org.eclipse.scava.crossflow.runtime.utils.TaskStatus;

import com.beust.jcommander.Parameter;

public abstract class Workflow extends Moded {

	@Parameter(names = { "-name" }, description = "The name of the workflow")
	protected String name;
	protected Cache cache;
	
	@Parameter(names = { "-master" }, description = "IP of the master")
	protected String master = "localhost";
	protected BrokerService brokerService;

	protected boolean cacheEnabled = true;
	private HashSet<String> activeJobs = new HashSet<String>();
	protected HashSet<Channel> activeChannels = new HashSet<Channel>();

	protected BuiltinTopic<TaskStatus> taskStatusTopic = null;
	protected BuiltinTopic<Object[]> resultsTopic = null;
	protected BuiltinTopic<ControlSignal> controlTopic = null;
	
	// for master to keep track of active and terminated workers
	protected Collection<String> activeWorkerIds = new HashSet<String>();
	protected Collection<String> terminatedWorkerIds = new HashSet<String>();
	
	// excluded tasks from workers
	protected Collection<String> tasksToExclude = new LinkedList<String>();
	
	public void excludeTasks(Collection<String> tasks){
		tasksToExclude = tasks;
	}
	
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
		taskStatusTopic = new BuiltinTopic<TaskStatus>(this, "TaskStatusPublisher");
		resultsTopic = new BuiltinTopic<Object[]>(this, "ResultsBroadcaster");
		controlTopic = new BuiltinTopic<ControlSignal>(this, "ControlTopic");
	}
	
	protected void connect() throws Exception {
		
		taskStatusTopic.init();
		resultsTopic.init();
		controlTopic.init();
		
		activeChannels.add(taskStatusTopic);
		// XXX Should we be checking this queue (resultsBroadcaster) for termination?
		//activeQueues.add(resultsBroadcaster);
		// do not add control topic to activequeues as it has to be managed explicitly
		// in terminate
		// activeQueues.add(controlTopic);

		controlTopic.addConsumer(new BuiltinTopicConsumer<ControlSignal>() {

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
							e.printStackTrace();
						}
				}

			}
		});
		

		// XXX if the worker sends this before the master is listening to this topic
		// this information is lost which affects termiantion
		if (!isMaster())
			controlTopic.send(new ControlSignal(ControlSignals.WORKER_ADDED, getName()));

		if (isMaster())
			taskStatusTopic.addConsumer(new BuiltinTopicConsumer<TaskStatus>() {

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
							checkForTermination();
							break;
						}
						default:
							break;
					}
				}

			});

	}
	
	boolean aboutToTerminate = false;
	
	public void checkForTermination() {
		if (activeJobs.size() == 0 && areChannelsEmpty()) {
			aboutToTerminate = true;
			new Timer().schedule(new TimerTask() {
				
				@Override
				public void run() {
					try {
						if (aboutToTerminate && areChannelsEmpty()) {
							terminate();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			}, 2000);
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
	
	public String getBroker() {
		return "tcp://" + master + ":61616";
	}
	
	public enum TaskStatuses {
		STARTED, WAITING, INPROGRESS, BLOCKED, FINISHED
	};

	public enum ControlReasons {
		INTENTFORPRODUCTION, TERMINATION
	};

	public enum ChannelTypes {
		Queue, Topic, UNKNOWN
	}

	public enum ControlSignals {
		TERMINATION, ACKNOWLEDGEMENT, WORKER_ADDED, WORKER_REMOVED
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

	private boolean areChannelsEmpty() {
		
		boolean result = true;
		
		try {
			for (Channel c : activeChannels) {
				for (String postId : c.getPostIds()) {
	
					ChannelTypes destinationType = c.type();
					String url = "service:jmx:rmi:///jndi/rmi://" + master + ":1099/jmxrmi";
					JMXConnector connector = JMXConnectorFactory.connect(new JMXServiceURL(url));
					MBeanServerConnection connection = connector.getMBeanServerConnection();
	
					ObjectName channel = new ObjectName("org.apache.activemq:type=Broker,brokerName=" + master
							+ ",destinationType=" + destinationType + ",destinationName=" + postId);
	
					DestinationViewMBean mbView = MBeanServerInvocationHandler.newProxyInstance(connection, channel,
							DestinationViewMBean.class, true);
	
					long remainingMessages = 0;
					
					if ( destinationType == ChannelTypes.Queue ) {
						remainingMessages = mbView.getQueueSize();
					} else if ( destinationType == ChannelTypes.Topic ) {
						if ( mbView.getInFlightCount() <= 1 ) {
							// FIXME find out why inflight count is 1 instead of zero when the workflow is done
							remainingMessages = 0;
						}
					}
	
					connector.close();
	
					if (remainingMessages > 0)
						result = false;
	
				}
			}
		}
		catch (Exception ex) {
			// If an exception occurs it means
			// that at least one of the channels
			// is closed/null
			return true;
		}
		
		return result;
		
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
				stopBroker();
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
	
	public BuiltinTopic<TaskStatus> getTaskStatusTopic() {
		return taskStatusTopic;
	}
	
	public BuiltinTopic<Object[]> getResultsTopic() {
		return resultsTopic;
	}
	
	public BuiltinTopic<ControlSignal> getControlTopic() {
		return controlTopic;
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
}
