package org.eclipse.scava.crossflow.examples.firstcommitment.ghrepo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.jmx.DestinationViewMBean;
import org.apache.activemq.command.ActiveMQDestination;
import org.eclipse.scava.crossflow.runtime.Job;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.Workflow.ControlReasons;
import org.eclipse.scava.crossflow.runtime.utils.ControlMessage;

public class ResultsPublisher {

	private Map<String, ActiveMQDestination> destination;
	private Map<String, ActiveMQDestination> pre;
	private Map<String, ActiveMQDestination> post;
	private ActiveMQDestination controlIn;
	private ActiveMQDestination controlOut;
	private Session session;
	private Workflow workflow;

	private Set<String> incomingTasks = new HashSet<>();
	
	public ResultsPublisher(Workflow workflow) throws Exception {
		
		this.workflow = workflow;
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(workflow.getBroker());
		connectionFactory.setTrustAllPackages(true);
		Connection connection = connectionFactory.createConnection();
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		destination = new HashMap<String, ActiveMQDestination>();
		pre = new HashMap<String, ActiveMQDestination>();
		post = new HashMap<String, ActiveMQDestination>();
		controlIn = (ActiveMQDestination) session.createQueue("ResultsPublisherControlQueue");
		controlOut = (ActiveMQDestination) session.createTopic("ResultsPublisherControlTopic");

		if (workflow.isMaster()) {
			MessageConsumer controlConsumer = session.createConsumer(controlIn);
			controlConsumer.setMessageListener(new MessageListener() {

				Set<String> terminatedIncomingTasks = new HashSet<>();

				@Override
				public void onMessage(Message message) {
					try {
						// if multiple incoming tasks contributing to this
						// queue,
						// note which one sent termination
						// also for multiple workers sending termination
						// signals?

						// when all incoming tasks send termination do (how do
						// we know what incoming tasks are?)

						// wait/trigger when all post queues are empty do
						ControlMessage msg = (ControlMessage) ((ObjectMessage) message).getObject();
						ControlReasons reason = msg.getControlReason();
						String callerId = msg.getCallerId();

						//
						if (reason.equals(ControlReasons.INTENTFORPRODUCTION)) {
							//
							incomingTasks.add(callerId);
							System.err.println(
									ResultsPublisher.this.getClass().getName() + ":" + workflow.getName() + "\ngot intent for production signal by: "+callerId);
							//
						} else if (reason.equals(ControlReasons.TERMINATION)) {

							terminatedIncomingTasks.add(callerId);

							if (terminatedIncomingTasks.equals(incomingTasks)) {

								String brokerIp = "localhost";

								String url = "service:jmx:rmi:///jndi/rmi://" + brokerIp + ":1099/jmxrmi";
								JMXConnector connector = JMXConnectorFactory.connect(new JMXServiceURL(url));
								MBeanServerConnection connection = connector.getMBeanServerConnection();
								// get queue size

								String destinationType = "Queue";

								long remainingMessages = 1;
								while (remainingMessages > 0) {
									//any better way to handle this?
									remainingMessages = 0;
									for (ActiveMQDestination d : post.values()) {

										ObjectName nameConsumers = new ObjectName(
												"org.apache.activemq:type=Broker,brokerName=" + brokerIp
														+ ",destinationType=" + destinationType + ",destinationName="
														+ d.getPhysicalName());

										DestinationViewMBean mbView = MBeanServerInvocationHandler.newProxyInstance(
												connection, nameConsumers, DestinationViewMBean.class, true);
										remainingMessages += mbView.getQueueSize();
										System.out.println(d.getPhysicalName() + " size: " + remainingMessages);

										// remove all consumers from post queues

										// send termination messages to all
										// consumers
										// (including
										// the
										// id of this queue so they know who
										// sent
										// it)
									}
								}
								//queues all empty:
								MessageProducer producer = session.createProducer(controlOut);
								producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
								ObjectMessage terminationSignal = session.createObjectMessage();
								terminationSignal.setObject(new ControlMessage(ControlReasons.TERMINATION, ResultsPublisher.this.getClass().getName()));
								producer.send(terminationSignal);
								System.err.println(
										ResultsPublisher.this.getClass().getName() + ":" + workflow.getName() + " sent termination signal!");
							} else {
								System.out.println(
										"trying to terminate locigcal queue ResultsPublisher but there are still active producers:");
								System.out.println("incomingTasks: " + incomingTasks);
								System.out.println("terminated tasks: " + terminatedIncomingTasks);
							}
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			});
		}
	}

	public void sendTerminationSignal(String taskId) {
		try {
			MessageProducer producer = session.createProducer(controlIn);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			ObjectMessage message = session.createObjectMessage();
			message.setObject(new ControlMessage(ControlReasons.TERMINATION, taskId));
			producer.send(message);
		} catch (JMSException e) {
			e.printStackTrace();
		}

	}

	public void sendIntentForProduction(String taskId) {
		try {
			MessageProducer producer = session.createProducer(controlIn);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			ObjectMessage message = session.createObjectMessage();
			message.setObject(new ControlMessage(ControlReasons.INTENTFORPRODUCTION, taskId));
			producer.send(message);
		} catch (JMSException e) {
			e.printStackTrace();
		}

	}
	
	public void send(Result result, String taskId) {
		try {
			ActiveMQDestination d = null;
			// if the sender is one of the targets of this channel, it has
			// re-sent a message
			// so it should only be put in the relevant physical queue
			if ((d = pre.get(taskId)) != null) {
				MessageProducer producer = session.createProducer(d);
				producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
				ObjectMessage message = session.createObjectMessage();
				result.setDestination("ResultsPublisher");
				message.setObject(result);
				producer.send(message);
			} else
				// otherwise the sender must be the source of this channel so
				// intends to
				// propagate its messages to all the physical queues
				for (Entry<String, ActiveMQDestination> e : pre.entrySet()) {
				MessageProducer producer = session.createProducer(e.getValue());
				producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
				ObjectMessage message = session.createObjectMessage();
				result.setDestination("ResultsPublisher");
				message.setObject(result);
				producer.send(message);
				}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void addConsumer(ResultsPublisherConsumer consumer, String consumerId) throws Exception {

		// XXX use runtime class as ID of consumer as tasks are unique

		ActiveMQDestination preQueue = (ActiveMQDestination) session.createQueue("ResultsPublisherPre-" + consumerId);
		pre.put(consumerId, preQueue);

		ActiveMQDestination destQueue = (ActiveMQDestination) session.createQueue("ResultsPublisherDestination-" + consumerId);
		destination.put(consumerId, destQueue);

		ActiveMQDestination postQueue = (ActiveMQDestination) session.createQueue("ResultsPublisherPost-" + consumerId);
		post.put(consumerId, postQueue);

		//

		if (workflow.isMaster()) {
			MessageConsumer preConsumer = session.createConsumer(preQueue);
			preConsumer.setMessageListener(new MessageListener() {

				@Override
				public void onMessage(Message message) {
					try {

						Job job = (Job) ((ObjectMessage) message).getObject();
						if (workflow.getCache() != null && workflow.getCache().hasCachedOutputs(job)) {
							for (Job output : workflow.getCache().getCachedOutputs(job)) {
								if (output.getDestination().equals("GhRepos")) {
									((GhRepoExample) workflow).getGhRepos().send((GhRepo) output,
											this.getClass().getName());
								}
								if (output.getDestination().equals("ResultsPublisher")) {
									((GhRepoExample) workflow).getResultsPublisher().send((Result) output,
											this.getClass().getName());
								}
							}
						} else {
							MessageProducer producer = session.createProducer(destQueue);
							producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
							producer.send(message);
						}

					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}

			});

			MessageConsumer destinationConsumer = session.createConsumer(destQueue);
			destinationConsumer.setMessageListener(new MessageListener() {

				@Override
				public void onMessage(Message message) {
					try {
						ObjectMessage objectMessage = (ObjectMessage) message;
						Job job = (Job) objectMessage.getObject();
						if (!job.isCached() && workflow.getCache() != null) {
							workflow.getCache().cache(job);
						}
						MessageProducer producer = session.createProducer(postQueue);
						producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
						producer.send(message);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}

			});
		}

		// only connect if the consumer exists (for example it will not in a
		// master_bare situation)
		if (consumer != null) {

			MessageConsumer messageConsumer = session.createConsumer(postQueue);
			messageConsumer.setMessageListener(new MessageListener() {

				@Override
				public void onMessage(Message message) {
					ObjectMessage objectMessage = (ObjectMessage) message;
					try {
						Result result = (Result) objectMessage.getObject();
						consumer.consumeResultsPublisherActual(result);
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
			});
			
			//subscribe to the control topic as well
			
			MessageConsumer controlMessageConsumer = session.createConsumer(controlOut);
			controlMessageConsumer.setMessageListener(new MessageListener() {

				@Override
				public void onMessage(Message message) {
					ObjectMessage objectMessage = (ObjectMessage) message;
					try {
						ControlMessage cm = (ControlMessage) objectMessage.getObject();
						consumer.processTerminationMessage(cm);
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
			});	
		}

	}

}