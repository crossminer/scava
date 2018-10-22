package org.eclipse.scava.crossflow.examples.firstcommitment.ghrepo;

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
import org.eclipse.scava.crossflow.runtime.utils.TaskStatus;

public class EclipseTaskStatusPublisher {

	protected Destination destination;

	protected Session session;
	protected Workflow workflow;

	public EclipseTaskStatusPublisher(Workflow workflow) throws Exception {
		this.workflow = workflow;
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(workflow.getBroker());
		connectionFactory.setTrustAllPackages(true);
		Connection connection = connectionFactory.createConnection();
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		destination = session.createTopic("EclipseTaskStatusPublisher");

	}

	public void send(TaskStatus taskStatus) {
		try {
			MessageProducer producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			ObjectMessage message = session.createObjectMessage();
			message.setObject(taskStatus);
			producer.send(message);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void addConsumer(EclipseTaskStatusPublisherConsumer consumer) throws Exception {
		MessageConsumer messageConsumer = session.createConsumer(destination);
		messageConsumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {
				ObjectMessage objectMessage = (ObjectMessage) message;
				try {
					TaskStatus status = (TaskStatus) objectMessage.getObject();
					consumer.consumeEclipseTaskStatusPublisher(status);
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
	}

}