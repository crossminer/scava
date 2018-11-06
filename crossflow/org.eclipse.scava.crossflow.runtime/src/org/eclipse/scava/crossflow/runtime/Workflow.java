package org.eclipse.scava.crossflow.runtime;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.jmx.DestinationViewMBean;
import org.eclipse.scava.crossflow.runtime.permanentqueues.ControlTopic;
import org.eclipse.scava.crossflow.runtime.permanentqueues.ControlTopicConsumer;
import org.eclipse.scava.crossflow.runtime.permanentqueues.ResultsBroadcaster;
import org.eclipse.scava.crossflow.runtime.permanentqueues.TaskStatusPublisher;
import org.eclipse.scava.crossflow.runtime.permanentqueues.TaskStatusPublisherConsumer;
import org.eclipse.scava.crossflow.runtime.utils.ControlSignal;
import org.eclipse.scava.crossflow.runtime.utils.TaskStatus;

import com.beust.jcommander.Parameter;

public abstract class Workflow {

	@Parameter(names = { "-name" }, description = "The name of the workflow")
	protected String name;
	protected Cache cache;
	@Parameter(names = {
			"-mode" }, description = "Must be master_bare, master or worker", converter = ModeConverter.class)
	protected Mode mode = Mode.MASTER;
	@Parameter(names = { "-master" }, description = "IP of the master")
	protected String master = "localhost";
	protected BrokerService brokerService;

	protected boolean enableCache = true;

	private Collection<Runnable> onTerminate = new LinkedList<Runnable>();

	private HashSet<String> activeJobs = new HashSet<String>();
	protected HashSet<Channel> activeQueues = new HashSet<Channel>();

	protected TaskStatusPublisher taskStatusPublisher = null;
	protected ResultsBroadcaster resultsBroadcaster = null;
	protected ControlTopic controlTopic = null;

	// for master to keep track of terminated workers
	protected Collection<String> terminatedWorkerIds = new HashSet<String>();
	//
	protected Collection<String> activeWorkerIds = new HashSet<String>();

	/**
	 * used to manually add local workers to master as they may be enabled too
	 * quickly to be registered using the control topic when on the same machine
	 */
	public void addActiveWorkerId(String id) {
		activeWorkerIds.add(id);
	}

	// terminate workflow on master after this time (ms) regardless of confirmation
	// from workers
	private int terminationTimeout = 10000;

	public void setTerminationTimeout(int timeout) {
		terminationTimeout = timeout;
	}

	public int getTerminationTimeout() {
		return terminationTimeout;
	}

	protected void connect() throws Exception {
		taskStatusPublisher = new TaskStatusPublisher(this);
		resultsBroadcaster = new ResultsBroadcaster(this);
		controlTopic = new ControlTopic(this);
		activeQueues.add(taskStatusPublisher);
		activeQueues.add(resultsBroadcaster);
		// do not add control topic to activequeues as it has to be managed explicitly
		// in terminate
		// activeQueues.add(controlTopic);

		controlTopic.addConsumer(new ControlTopicConsumer() {

			@Override
			public void consumeControlTopic(ControlSignal signal) {
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
			taskStatusPublisher.addConsumer(new TaskStatusPublisherConsumer() {

				@Override
				public void consumeTaskStatusPublisher(TaskStatus status) {
					switch (status.getStatus()) {

					case INPROGRESS:
						activeJobs.add(status.getCaller());
						break;
					case WAITING:
						activeJobs.remove(status.getCaller());
						try {
							checkForTermination();
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;

					default:
						break;

					}
				}

			});

	}

	boolean terminationInProgress = false;
	boolean alreadyWaitedToTerminate = false;
	boolean terminating = false;

	private void checkForTermination() throws Exception {
		// System.err.println("checkForTermination entered");
		if (!terminating && !terminationInProgress) {
			terminationInProgress = true;

			if (activeJobs.size() == 0 && areQueuesEmpty())
				try {

					if (alreadyWaitedToTerminate) {
						alreadyWaitedToTerminate = false;

						terminating = true;
						terminate();

					} else {

						alreadyWaitedToTerminate = true;
						terminationInProgress = false;
						Thread.sleep(5000);
						checkForTermination();

					}

				} catch (Exception e) {
					e.printStackTrace();
				}

		}
		terminationInProgress = false;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isCacheEnabled() {
		return enableCache;
	}

	public void setEnableCache(boolean ec) {
		enableCache = ec;
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

	public String getBroker() {
		return "tcp://" + master + ":61616";
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}

	public Mode getMode() {
		return mode;
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

	public void stopBroker() {
		try {
			brokerService.deleteAllMessages();
			// for(Channel d : activeQueues) {
			// brokerService.removeDestination(d.get);
			// }
			// brokerService.setUseJmx(false);
			brokerService.stopGracefully("", "", 1000, 1000);
			// brokerService.stop();
			System.out.println("terminated broker (" + getName() + ")");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() throws Exception {
		run(0);
	}

	public abstract void run(int i) throws Exception;

//	private ObjectName getQueueObjectName(String type, String queueName) throws Exception {
//		// String url = "service:jmx:rmi:///jndi/rmi://" + master + ":1099/jmxrmi";
//		// JMXConnector connector = JMXConnectorFactory.connect(new JMXServiceURL(url));
//		// MBeanServerConnection connection = connector.getMBeanServerConnection();
//		// return connection;
//
//		String amqDomain = "org.apache.activemq";
//
//		// The parameters for an ObjectName
//		Hashtable<String, String> params = new Hashtable<String, String>();
//		params.put("Type", type);
//		params.put("BrokerName", master);
//		params.put("Destination", queueName);
//
//		// Create an ObjectName
//		ObjectName queueObjectName = ObjectName.getInstance(amqDomain, params);
//
//		return queueObjectName;
//
//	}

	private boolean areQueuesEmpty() throws Exception {
		boolean ret = true;

//		    ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616/");
//
//		    ActiveMQConnection connection = (ActiveMQConnection) connectionFactory.createConnection();
//		    DestinationSource ds = connection.getDestinationSource();
//
//		    connection.start();
//
//		    Set<ActiveMQQueue> queues = ds.getQueues();
//		    System.out.println(queues);
//
//		    for (ActiveMQQueue activeMQQueue : queues)
//		            System.out.println(activeMQQueue.getQueueName());
//		    
//		    Set<ActiveMQTopic> topics = ds.getTopics();
//		    System.out.println(topics);
//
//		    for (ActiveMQTopic activeMQTopic : topics)
//		            System.out.println(activeMQTopic);
//		    
//		    connection.close();

		for (Channel c : activeQueues) {
			for (String postId : c.getPostIds()) {

				// System.err.println("checking size of queue: " + postId);

				ChannelTypes destinationType = c.type();

				// ObjectName channel = getQueueObjectName(destinationType.toString(), postId);

				// DestinationViewMBean mbView = (DestinationViewMBean)
				// brokerService.getManagementContext().newProxyInstance(channel,
				// DestinationViewMBean.class, true);

				String url = "service:jmx:rmi:///jndi/rmi://" + master + ":1099/jmxrmi";
				JMXConnector connector = JMXConnectorFactory.connect(new JMXServiceURL(url));
				MBeanServerConnection connection = connector.getMBeanServerConnection();

				ObjectName channel = new ObjectName("org.apache.activemq:type=Broker,brokerName=" + master
						+ ",destinationType=" + destinationType + ",destinationName=" + postId);

				DestinationViewMBean mbView = MBeanServerInvocationHandler.newProxyInstance(connection, channel,
						DestinationViewMBean.class, true);

				long remainingMessages = mbView.getQueueSize();

				connector.close();

				if (remainingMessages > 0)
					ret = false;

				// System.err.println(postId + " size: " + remainingMessages);

			}
		}

		return ret;
	}

	private void terminate() throws Exception {

		// master graceful termination logic
		if (isMaster()) {

			// ask all workers to terminate
			controlTopic.send(new ControlSignal(ControlSignals.TERMINATION, getName()));

			long startTime = System.currentTimeMillis();
			// wait for workers to terminate or for the termination timeout
			while ((System.currentTimeMillis() - startTime) < terminationTimeout) {
				// System.err.println(activeWorkerIds);
				// System.err.println(terminatedWorkerIds);
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
		for (Channel activeQueue : activeQueues)
			activeQueue.stop();

		if (isMaster()) {

			controlTopic.stop();

			for (Runnable onT : onTerminate)
				onT.run();

			//
			stopBroker();
			//

		} else {
			controlTopic.send(new ControlSignal(ControlSignals.ACKNOWLEDGEMENT, getName()));
			controlTopic.stop();
		}

		System.out.println("workflow " + getName() + " terminated.");
	}

	public void manualTermination() throws Exception {
		terminate();
	}

	public void addShutdownHook(Runnable runnable) {
		onTerminate.add(runnable);
	}

}
