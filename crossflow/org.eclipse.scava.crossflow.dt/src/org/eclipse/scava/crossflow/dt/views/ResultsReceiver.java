package org.eclipse.scava.crossflow.dt.views;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.eclipse.scava.crossflow.examples.firstcommitment.ghrepo.EclipseResultPublisherConsumer;
import org.eclipse.scava.crossflow.runtime.Job;

public class ResultsReceiver {

	protected Destination results;
	protected Session session;

	public ResultsReceiver() throws Exception {

		// FIXME hard-coded
		QueueConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://" + "localhost" + ":61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		results = session.createTopic("EclipseResultsPublisher");

		if (results == null)
			throw new Exception("Cannot find topic: EclipseResultsPublisher at: tcp://localhost:61616");

		// Queue queue = (Queue) namingContext.lookup("ResultsPublisher");

	}

	public void addConsumer(EclipseResultPublisherConsumer consumer) throws Exception {
		MessageConsumer messageConsumer = session.createConsumer(results);
		messageConsumer.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message message) {
				// System.out.println("message received in Receiver");
				// System.out.println(message);
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