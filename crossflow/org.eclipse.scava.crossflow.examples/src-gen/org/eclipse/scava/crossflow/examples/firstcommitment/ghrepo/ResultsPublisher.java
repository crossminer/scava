package org.eclipse.scava.crossflow.examples.firstcommitment.ghrepo;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Collection;
import java.util.List;
import java.util.LinkedList;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.Job;
import org.eclipse.scava.crossflow.runtime.Channel;
import org.eclipse.scava.crossflow.runtime.Workflow.ChannelTypes;

public class ResultsPublisher implements Channel {
	
	protected Map<String, ActiveMQDestination> destination;
	protected Map<String, ActiveMQDestination> pre;
	protected Map<String, ActiveMQDestination> post;
	protected Connection connection;
	protected Session session;
	protected Workflow workflow;
	protected List<MessageConsumer> consumers = new LinkedList<MessageConsumer>();
	
	public ResultsPublisher(Workflow workflow) throws Exception {
		this.workflow = workflow;
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(workflow.getBroker());
		connectionFactory.setTrustAllPackages(true);
		connection = connectionFactory.createConnection();
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		destination = new HashMap<String, ActiveMQDestination>();
		pre = new HashMap<String, ActiveMQDestination>();
		post = new HashMap<String, ActiveMQDestination>();
		
		pre.put("EmptySink", (ActiveMQDestination) session.createQueue("ResultsPublisherPre.EmptySink"));
		destination.put("EmptySink", (ActiveMQDestination) session.createQueue("ResultsPublisherDestination.EmptySink"));
		post.put("EmptySink", (ActiveMQDestination) session.createQueue("ResultsPublisherPost.EmptySink"));
		
	}
	
	public void send(Result result, String taskId) {
		try {
			ActiveMQDestination d = null;
			// if the sender is one of the targets of this channel, it has re-sent a message
			// so it should only be put in the relevant physical queue
			if ((d = pre.get(taskId)) != null) {
				MessageProducer producer = session.createProducer(d);
				producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
				ObjectMessage message = session.createObjectMessage();
				result.setDestination("ResultsPublisher");
				message.setObject(result);
				producer.send(message);
				producer.close();
			} else
				// otherwise the sender must be the source of this channel so intends to
				// propagate its messages to all the physical queues
				for (Entry<String, ActiveMQDestination> e : pre.entrySet()) {			
					MessageProducer producer = session.createProducer(e.getValue());
					producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
					ObjectMessage message = session.createObjectMessage();
					result.setDestination("ResultsPublisher");
					message.setObject(result);
					producer.send(message);
					producer.close();
				}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void addConsumer(ResultsPublisherConsumer consumer, String consumerId) throws Exception {
	
		ActiveMQDestination preQueue = pre.get(consumerId);
		ActiveMQDestination destQueue = destination.get(consumerId);
		ActiveMQDestination postQueue = post.get(consumerId);
		
		if (workflow.isMaster()) {
			MessageConsumer preConsumer = session.createConsumer(preQueue);
			consumers.add(preConsumer);
			preConsumer.setMessageListener(new MessageListener() {

				@Override
				public void onMessage(Message message) {
					try {
						
						Job job = (Job) ((ObjectMessage) message).getObject();
						if (workflow.isCacheEnabled() && workflow.getCache().hasCachedOutputs(job)) {
							for (Job output : workflow.getCache().getCachedOutputs(job)) {
								if (output.getDestination().equals("ResultsPublisher")) {
									MessageProducer producer = session.createProducer(preQueue);
									producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
									ObjectMessage m = session.createObjectMessage();
									m.setObject(output);
									producer.send(m);
								} else {
									//XXX should not be the case?
									System.err.println(
											output.getDestination() + " destination found in ResultsPublisher pre consumer");
								}
							}
						} else {
							MessageProducer producer = session.createProducer(destQueue);
							producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
							producer.send(message);
							producer.close();
						}
						
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				
			});
			
			MessageConsumer destinationConsumer = session.createConsumer(destQueue);
			consumers.add(destinationConsumer);
			destinationConsumer.setMessageListener(new MessageListener() {

				@Override
				public void onMessage(Message message) {
					try {
						ObjectMessage objectMessage = (ObjectMessage) message;
						Job job = (Job) objectMessage.getObject();
						if (workflow.isCacheEnabled() && !job.isCached()) {
							workflow.getCache().cache(job);
						}
						MessageProducer producer = session.createProducer(postQueue);
						producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
						producer.send(message);
						producer.close();
					}
					catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				
			});
		}
			
		//only connect if the consumer exists (for example it will not in a master_bare situation)
		if(consumer!=null) {
		
		MessageConsumer messageConsumer = session.createConsumer(postQueue);
		consumers.add(messageConsumer);
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
	}
	
	}
	
	public Collection<String> getPostIds() {
		List<String> ret = new LinkedList<String>();
		for (ActiveMQDestination d : post.values())
			ret.add(d.getPhysicalName());
		return ret;
	}
	
	public void stop() throws JMSException {
		for (MessageConsumer c : consumers)
			c.close();
		session.close();
		connection.close();
	}

	@Override
	public ChannelTypes type() {
		if (destination.values().iterator().next().isQueue())
			return ChannelTypes.Queue;
		if (destination.values().iterator().next().isTopic())
			return ChannelTypes.Topic;
		return ChannelTypes.UNKNOWN;
	}

}