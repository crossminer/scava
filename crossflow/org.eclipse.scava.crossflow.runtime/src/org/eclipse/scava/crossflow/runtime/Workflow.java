package org.eclipse.scava.crossflow.runtime;

import java.util.HashSet;

import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.jmx.DestinationViewMBean;
import org.apache.activemq.command.ActiveMQDestination;
import org.eclipse.scava.crossflow.runtime.permanentqueues.ResultsBroadcaster;
import org.eclipse.scava.crossflow.runtime.permanentqueues.TaskStatusPublisher;
import org.eclipse.scava.crossflow.runtime.permanentqueues.TaskStatusPublisherConsumer;
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

	private Runnable onTerminate = null;

	private HashSet<String> activeJobs = new HashSet<String>();
	protected HashSet<Channel> activeQueues = new HashSet<Channel>();

	protected TaskStatusPublisher taskStatusPublisher = null;
	protected ResultsBroadcaster resultsBroadcaster = null;

	protected void connect() throws Exception {
		taskStatusPublisher = new TaskStatusPublisher(this);
		resultsBroadcaster = new ResultsBroadcaster(this);
		activeQueues.add(taskStatusPublisher);
		activeQueues.add(resultsBroadcaster);

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
		if (!terminating && !terminationInProgress) {
			terminationInProgress = true;

			if (alreadyWaitedToTerminate) {
				alreadyWaitedToTerminate = false;
				if (activeJobs.size() == 0 && areQueuesEmpty())
					try {
						terminating = true;
						terminate();
					} catch (Exception e) {
						e.printStackTrace();
					}
			} else {
				try {
					alreadyWaitedToTerminate = true;
					terminationInProgress = false;
					Thread.sleep(5000);
					checkForTermination();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}

			terminationInProgress = false;
		}
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

	public void stop() {
		try {
			brokerService.deleteAllMessages();
			//for(Channel d : activeQueues) {				
			//	brokerService.removeDestination(d.get);
			//}
			//brokerService.setUseJmx(false);
			brokerService.stopGracefully("", "", 1000, 1000);
			//brokerService.stop();
			System.out.println("terminated ("+getName()+")");
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

		System.out.println("terminating workflow... (" + getName() + ")");

		// stop all channel connections
		for (Channel activeQueue : activeQueues)
			activeQueue.stop();

		if (isMaster()) {

			if (onTerminate != null)
				onTerminate.run();

			//
			stop();
			//

		}
	}

	public void manualTermination() throws Exception {
		terminate();
	}

	public void onTerminate(Runnable runnable) {
		onTerminate = runnable;
	}

}
