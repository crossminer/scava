package org.eclipse.scava.crossflow.examples.addition;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.Job;

public class AdditionResults {
	
	protected Map<String, Destination> destination;
	protected Map<String, Destination> pre;
	protected Map<String, Destination> post;
	protected Session session;
	protected Workflow workflow;
	
	public AdditionResults(Workflow workflow) throws Exception {
		this.workflow = workflow;
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(workflow.getBroker());
		connectionFactory.setTrustAllPackages(true);
		Connection connection = connectionFactory.createConnection();
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		destination = new HashMap<String, Destination>();
		pre = new HashMap<String, Destination>();
		post = new HashMap<String, Destination>();
		
	}
	
	public void send(Number number, String taskId) {
		try {
			Destination d = null;
			// if the sender is one of the targets of this channel, it has re-sent a message
			// so it should only be put in the relevant physical queue
			if ((d = pre.get(taskId)) != null) {
				MessageProducer producer = session.createProducer(d);
				producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
				ObjectMessage message = session.createObjectMessage();
				number.setDestination("AdditionResults");
				message.setObject(number);
				producer.send(message);
			} else
				// otherwise the sender must be the source of this channel so intends to
				// propagate its messages to all the physical queues
				for (Entry<String, Destination> e : pre.entrySet()) {			
					MessageProducer producer = session.createProducer(e.getValue());
					producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
					ObjectMessage message = session.createObjectMessage();
					number.setDestination("AdditionResults");
					message.setObject(number);
					producer.send(message);
				}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void addConsumer(AdditionResultsConsumer consumer, String consumerId) throws Exception {

		// XXX use runtime class as ID of consumer as tasks are unique
	
		Destination preQueue = session.createQueue("AdditionResultsPre-" + consumerId);
		pre.put(consumerId, preQueue);	
	
		Destination destQueue = session.createQueue("AdditionResultsDestination-" + consumerId);
		destination.put(consumerId, destQueue);	
	
		Destination postQueue = session.createQueue("AdditionResultsPost-" + consumerId);
		post.put(consumerId, postQueue);
	
		//
	
		if (workflow.isMaster()) {
			MessageConsumer preConsumer = session.createConsumer(preQueue);
			preConsumer.setMessageListener(new MessageListener() {

				@Override
				public void onMessage(Message message) {
					try {
						
						Job job = (Job) ((ObjectMessage) message).getObject();
						if (workflow.getCache().hasCachedOutputs(job)) {
							for (Job output : workflow.getCache().getCachedOutputs(job)) {
								if (output.getDestination().equals("Additions")) {
									((AdditionExample) workflow).getAdditions().send((NumberPair) output, this.getClass().getName());
								}
								if (output.getDestination().equals("AdditionResults")) {
									((AdditionExample) workflow).getAdditionResults().send((Number) output, this.getClass().getName());
								}
							}
						}
						else {
							MessageProducer producer = session.createProducer(destQueue);
							producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
							producer.send(message);
						}
						
					}
					catch (Exception ex) {
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
						if (!job.isCached()) {
							workflow.getCache().cache(job);
						}
						MessageProducer producer = session.createProducer(postQueue);
						producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
						producer.send(message);
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
		messageConsumer.setMessageListener(new MessageListener() {
	
			@Override
			public void onMessage(Message message) {
				ObjectMessage objectMessage = (ObjectMessage) message;
				try {
					Number number = (Number) objectMessage.getObject();
					consumer.consumeAdditionResultsActual(number);
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}	
		});
	}
	
	}
	
}