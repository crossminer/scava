package org.eclipse.scava.crossflow.examples.firstcommitment.mdetech;

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

public class MdeTechnologyRepoFileCountEntries {
	
	protected Destination destination;
	protected Destination pre;
	protected Destination post;
	protected Session session;
	protected Workflow workflow;
	
	public MdeTechnologyRepoFileCountEntries(Workflow workflow) throws Exception {
		this.workflow = workflow;
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(workflow.getBroker());
		connectionFactory.setTrustAllPackages(true);
		Connection connection = connectionFactory.createConnection();
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		destination = session.createQueue("MdeTechnologyRepoFileCountEntries");
		pre = session.createQueue("MdeTechnologyRepoFileCountEntriesPre");
		post = session.createQueue("MdeTechnologyRepoFileCountEntriesPost");
		
		if (workflow.isMaster()) {
			MessageConsumer preConsumer = session.createConsumer(pre);
			preConsumer.setMessageListener(new MessageListener() {

				@Override
				public void onMessage(Message message) {
					try {
						
						Job job = (Job) ((ObjectMessage) message).getObject();
						if (workflow.getCache().hasCachedOutputs(job)) {
							for (Job output : workflow.getCache().getCachedOutputs(job)) {
								if (output.getDestination().equals("MdeTechnologies")) {
									((MdeTechnologyExample) workflow).getMdeTechnologies().send((StringStringTuple) output);
								}
								if (output.getDestination().equals("MdeTechnologyRepoEntries")) {
									((MdeTechnologyExample) workflow).getMdeTechnologyRepoEntries().send((StringStringIntegerTuple) output);
								}
								if (output.getDestination().equals("MdeTechnologyClonedRepoEntries")) {
									((MdeTechnologyExample) workflow).getMdeTechnologyClonedRepoEntries().send((StringStringIntegerStringTuple) output);
								}
								if (output.getDestination().equals("MdeTechnologyRepoAuthorCountEntries")) {
									((MdeTechnologyExample) workflow).getMdeTechnologyRepoAuthorCountEntries().send((StringStringIntegerStringIntegerTuple) output);
								}
								if (output.getDestination().equals("MdeTechnologyRepoFileCountEntries")) {
									((MdeTechnologyExample) workflow).getMdeTechnologyRepoFileCountEntries().send((StringStringIntegerStringIntegerTuple) output);
								}
								if (output.getDestination().equals("MdeTechnologyRepoOwnerPopularityCountEntries")) {
									((MdeTechnologyExample) workflow).getMdeTechnologyRepoOwnerPopularityCountEntries().send((StringStringIntegerStringIntegerTuple) output);
								}
							}
						}
						else {
							MessageProducer producer = session.createProducer(destination);
							producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
							producer.send(message);
						}
						
					}
					catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				
			});
			
			MessageConsumer destinationConsumer = session.createConsumer(destination);
			destinationConsumer.setMessageListener(new MessageListener() {

				@Override
				public void onMessage(Message message) {
					try {
						ObjectMessage objectMessage = (ObjectMessage) message;
						Job job = (Job) objectMessage.getObject();
						if (!job.isCached()) {
							workflow.getCache().cache(job);
						}
						MessageProducer producer = session.createProducer(post);
						producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
						producer.send(message);
					}
					catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				
			});
		}
	}
	
	public void send(StringStringIntegerStringIntegerTuple stringStringIntegerStringIntegerTuple) {
		try {
			MessageProducer producer = session.createProducer(pre);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			ObjectMessage message = session.createObjectMessage();
			stringStringIntegerStringIntegerTuple.setDestination("MdeTechnologyRepoFileCountEntries");
			message.setObject(stringStringIntegerStringIntegerTuple);
			producer.send(message);
			
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void addConsumer(MdeTechnologyRepoFileCountEntriesConsumer consumer) throws Exception {
		MessageConsumer messageConsumer = session.createConsumer(post);
		messageConsumer.setMessageListener(new MessageListener() {
	
			@Override
			public void onMessage(Message message) {
				ObjectMessage objectMessage = (ObjectMessage) message;
				try {
					StringStringIntegerStringIntegerTuple stringStringIntegerStringIntegerTuple = (StringStringIntegerStringIntegerTuple) objectMessage.getObject();
					consumer.consumeMdeTechnologyRepoFileCountEntriesActual(stringStringIntegerStringIntegerTuple);
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}	
		});
	}
	
}