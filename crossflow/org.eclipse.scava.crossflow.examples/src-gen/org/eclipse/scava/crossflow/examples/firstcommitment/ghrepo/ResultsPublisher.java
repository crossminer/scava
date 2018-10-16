package org.eclipse.scava.crossflow.examples.firstcommitment.ghrepo;

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

public class ResultsPublisher {

	protected Destination destination;
	protected Destination pre;
	protected Destination post;
	protected Session session;
	protected Workflow workflow;

	public ResultsPublisher(Workflow workflow) throws Exception {
		this.workflow = workflow;
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(workflow.getBroker());
		Connection connection = connectionFactory.createConnection();
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		destination = session.createQueue("ResultsPublisher");
		pre = session.createQueue("ResultsPublisherPre");
		post = session.createQueue("ResultsPublisherPost");

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
			// result.setDestination("ResultsPublisher");
			message.setObject(result);
			producer.send(message);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void addConsumer(ResultsPublisherConsumer consumer) throws Exception {
		MessageConsumer messageConsumer = session.createConsumer(post);
		messageConsumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {
				ObjectMessage objectMessage = (ObjectMessage) message;
				try {
					Object[] job = (Object[]) objectMessage.getObject();
					consumer.consumeResultsPublisher((Object[]) job);
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
	}

}