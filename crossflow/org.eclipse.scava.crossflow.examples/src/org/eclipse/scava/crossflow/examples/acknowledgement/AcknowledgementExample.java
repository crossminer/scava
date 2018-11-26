package org.eclipse.scava.crossflow.examples.acknowledgement;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageConsumer;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.broker.BrokerService;

public class AcknowledgementExample {

	
	public static void main(String[] args) throws Exception {
		
		new AcknowledgementExample().run();
		
	}
	
	public void run() throws Exception {
		BrokerService broker = new BrokerService();
		broker.setUseJmx(true);
		broker.setPersistent(false);
		broker.addConnector("tcp://localhost:61616");
		broker.start();
		
		ActiveMQConnectionFactory connectionFactory = 
				new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
		ActiveMQConnection connection = (ActiveMQConnection) connectionFactory.createConnection();
		connection.start();
		
		ActiveMQSession session = (ActiveMQSession) connection.
				createSession(false, ActiveMQSession.INDIVIDUAL_ACKNOWLEDGE);
		Queue queue = session.createQueue("queue");
		
		createConsumer(session, queue, "Consumer 1", false);
		createConsumer(session, queue, "Consumer 2", true);
		
		MessageProducer producer = session.createProducer(queue);
		for (int i=0;i<10;i++) {
			producer.send(session.createTextMessage("Message " + i));
			Thread.sleep(100);
		}
		
	}
	
	public void createConsumer(ActiveMQSession session, Queue queue, String name, boolean acknowledge) throws Exception {
		
		final ActiveMQMessageConsumer consumer = (ActiveMQMessageConsumer) session.createConsumer(queue);
		
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				try {
					System.out.println(((TextMessage) message).getText() + " received by " + name);
					if (!acknowledge) {
						// This will cause all unacknowledged messages to be returned to the broker
						consumer.close();
						// Re-initialise the consumer
						createConsumer(session, queue, name, acknowledge);
					}
				}
				catch (Exception ex) {
					if (ex instanceof RuntimeException) throw (RuntimeException) ex;
					else throw new RuntimeException(ex);
				}
			}
		});
		
	}
	
	
}
