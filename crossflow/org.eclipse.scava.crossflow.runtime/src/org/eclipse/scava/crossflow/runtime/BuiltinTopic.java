package org.eclipse.scava.crossflow.runtime;

import java.io.Serializable;
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
import org.eclipse.scava.crossflow.runtime.Workflow.ChannelTypes;

public class BuiltinTopic<T extends Serializable> implements Channel {
	
	protected ActiveMQDestination destination;
	protected Connection connection;
	protected Session session;
	protected Workflow workflow;
	protected List<MessageConsumer> consumers = new LinkedList<MessageConsumer>();
	
	public BuiltinTopic(Workflow workflow, String name) throws Exception {
		this.workflow = workflow;
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(workflow.getBroker());
		connectionFactory.setTrustAllPackages(true);
		connection = connectionFactory.createConnection();
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		destination = (ActiveMQDestination) session.createTopic(name);
	}
	
	public void send(T t) {
		try {
			MessageProducer producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			ObjectMessage message = session.createObjectMessage();
			message.setObject(t);
			producer.send(message);
			producer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void addConsumer(BuiltinTopicConsumer<T> consumer) throws Exception {
		MessageConsumer messageConsumer = session.createConsumer(destination);
		consumers.add(messageConsumer);
		messageConsumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {
				ObjectMessage objectMessage = (ObjectMessage) message;
				try {
					consumer.consume((T) objectMessage.getObject());
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
