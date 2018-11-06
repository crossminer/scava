package org.eclipse.scava.crossflow.runtime.permanentqueues;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQDestination;
import org.eclipse.scava.crossflow.runtime.Channel;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.Workflow.ChannelTypes;
import org.eclipse.scava.crossflow.runtime.utils.ControlSignal;
import org.eclipse.scava.crossflow.runtime.utils.TaskStatus;

public class ControlTopic implements Channel {

	protected ActiveMQDestination destination;
	protected Connection connection;
	protected Session session;
	protected Workflow workflow;
	protected List<MessageConsumer> consumers = new LinkedList<MessageConsumer>();

	public ControlTopic(Workflow workflow) throws Exception {
		this.workflow = workflow;
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(workflow.getBroker());
		connectionFactory.setTrustAllPackages(true);
		connection = connectionFactory.createConnection();
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		destination = (ActiveMQDestination) session.createTopic("ControlTopic");

	}

	public void send(ControlSignal signal) {
		try {
			MessageProducer producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			ObjectMessage message = session.createObjectMessage();
			message.setObject(signal);
			producer.send(message);
			producer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void addConsumer(ControlTopicConsumer consumer) throws Exception {
		MessageConsumer messageConsumer = session.createConsumer(destination);
		consumers.add(messageConsumer);
		messageConsumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {
				ObjectMessage objectMessage = (ObjectMessage) message;
				try {
					ControlSignal status = (ControlSignal) objectMessage.getObject();
					consumer.consumeControlTopic(status);
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void stop() throws JMSException {
		for (MessageConsumer c : consumers)
			c.close();
		session.close();
		connection.close();
	}

	@Override
	public ChannelTypes type() {
		if (destination.isQueue())
			return ChannelTypes.Queue;
		if (destination.isTopic())
			return ChannelTypes.Topic;
		return ChannelTypes.UNKNOWN;
	}

	@Override
	public Collection<String> getPostIds() {
		return Collections.singleton(destination.getPhysicalName());
	}

}