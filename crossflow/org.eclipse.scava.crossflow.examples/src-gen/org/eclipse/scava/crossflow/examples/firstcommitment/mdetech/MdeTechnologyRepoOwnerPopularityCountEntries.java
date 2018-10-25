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

public class MdeTechnologyRepoOwnerPopularityCountEntries {
	
	protected Destination destination;
	protected Destination pre;
	protected Destination post;
	protected Session session;
	protected Workflow workflow;
	
	public MdeTechnologyRepoOwnerPopularityCountEntries(Workflow workflow) throws Exception {
		this.workflow = workflow;
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(workflow.getBroker());
		connectionFactory.setTrustAllPackages(true);
		Connection connection = connectionFactory.createConnection();
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		destination = session.createQueue("MdeTechnologyRepoOwnerPopularityCountEntries");
		pre = session.createQueue("MdeTechnologyRepoOwnerPopularityCountEntriesPre");
		post = session.createQueue("MdeTechnologyRepoOwnerPopularityCountEntriesPost");
		
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
									((MdeTechnologyExample) workflow).getMdeTechnologies().send((ExtensionKeywordTuple) output);
								}
								if (output.getDestination().equals("MdeTechnologyRepoEntries")) {
									((MdeTechnologyExample) workflow).getMdeTechnologyRepoEntries().send((ExtensionKeywordStargazersTuple) output);
								}
								if (output.getDestination().equals("MdeTechnologyClonedRepoEntriesForAuthorCounter")) {
									((MdeTechnologyExample) workflow).getMdeTechnologyClonedRepoEntriesForAuthorCounter().send((ExtensionKeywordStargazersRemoteRepoUrlTuple) output);
								}
								if (output.getDestination().equals("MdeTechnologyClonedRepoEntriesForFileCounter")) {
									((MdeTechnologyExample) workflow).getMdeTechnologyClonedRepoEntriesForFileCounter().send((ExtensionKeywordStargazersRemoteRepoUrlTuple) output);
								}
								if (output.getDestination().equals("MdeTechnologyClonedRepoEntriesForOwnerPopularityCounter")) {
									((MdeTechnologyExample) workflow).getMdeTechnologyClonedRepoEntriesForOwnerPopularityCounter().send((ExtensionKeywordStargazersRemoteRepoUrlTuple) output);
								}
								if (output.getDestination().equals("MdeTechnologyRepoAuthorCountEntries")) {
									((MdeTechnologyExample) workflow).getMdeTechnologyRepoAuthorCountEntries().send((ExtensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple) output);
								}
								if (output.getDestination().equals("MdeTechnologyRepoFileCountEntries")) {
									((MdeTechnologyExample) workflow).getMdeTechnologyRepoFileCountEntries().send((ExtensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple) output);
								}
								if (output.getDestination().equals("MdeTechnologyRepoOwnerPopularityCountEntries")) {
									((MdeTechnologyExample) workflow).getMdeTechnologyRepoOwnerPopularityCountEntries().send((ExtensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple) output);
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
	
	public void send(ExtensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple) {
		try {
			MessageProducer producer = session.createProducer(pre);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			ObjectMessage message = session.createObjectMessage();
			extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple.setDestination("MdeTechnologyRepoOwnerPopularityCountEntries");
			message.setObject(extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple);
			producer.send(message);
			
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void addConsumer(MdeTechnologyRepoOwnerPopularityCountEntriesConsumer consumer) throws Exception {
		MessageConsumer messageConsumer = session.createConsumer(post);
		messageConsumer.setMessageListener(new MessageListener() {
	
			@Override
			public void onMessage(Message message) {
				ObjectMessage objectMessage = (ObjectMessage) message;
				try {
					ExtensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple = (ExtensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple) objectMessage.getObject();
					consumer.consumeMdeTechnologyRepoOwnerPopularityCountEntriesActual(extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple);
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}	
		});
	}
	
}