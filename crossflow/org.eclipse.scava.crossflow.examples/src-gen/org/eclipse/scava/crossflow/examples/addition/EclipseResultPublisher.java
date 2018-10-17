package org.eclipse.scava.crossflow.examples.addition;

import javax.jms.Connection;
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

public class EclipseResultPublisher {

	protected Destination destination;
	protected Destination pre;
	protected Destination post;
	protected Session session;
	protected Workflow workflow;

	public EclipseResultPublisher(Workflow workflow) throws Exception {
		this.workflow = workflow;
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(workflow.getBroker());
		connectionFactory.setTrustAllPackages(true);
		Connection connection = connectionFactory.createConnection();
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		destination = session.createQueue("EclipseResultsPublisher");
		pre = session.createQueue("EclipseResultsPublisherPre");
		post = session.createQueue("EclipseResultsPublisherPost");

		if (workflow.isMaster()) {
			MessageConsumer preConsumer = session.createConsumer(pre);
			preConsumer.setMessageListener(new MessageListener() {

				@Override
				public void onMessage(Message message) {
					try {
						MessageProducer producer2 = session.createProducer(destination);
						producer2.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
						producer2.send(message);
					}

					catch (Exception ex) {
						ex.printStackTrace();
					}
				}

			});

		}
	}

	public void send(Object[] result) {
		try {
			MessageProducer producer = session.createProducer(pre);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			ObjectMessage message = session.createObjectMessage();
			message.setObject(result);
			producer.send(message);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void addConsumer(EclipseResultPublisherConsumer consumer) throws Exception {
		MessageConsumer messageConsumer = session.createConsumer(post);
		messageConsumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {
				ObjectMessage objectMessage = (ObjectMessage) message;
				try {
					Object[] job = (Object[]) objectMessage.getObject();
					consumer.consumeEclipseResultPublisher((Object[]) job);
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
	}

}