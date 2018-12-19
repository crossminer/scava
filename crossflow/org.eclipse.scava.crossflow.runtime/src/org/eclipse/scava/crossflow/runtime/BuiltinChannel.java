package org.eclipse.scava.crossflow.runtime;

import java.io.Serializable;
import java.util.ArrayList;
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
import org.eclipse.scava.crossflow.runtime.Workflow.ChannelType;

public class BuiltinChannel<T extends Serializable> implements Channel {
	
	protected ActiveMQDestination destination;
	protected Connection connection;
	protected Session session;
	protected Workflow workflow;
	protected List<MessageConsumer> consumers = new LinkedList<>();
	protected List<BuiltinChannelConsumer<T>> pendingConsumers = new ArrayList<>();
	protected String name;
	protected boolean broadcast;
	
	public BuiltinChannel(Workflow workflow, String name) {
		this(workflow, name, true);
	}
	
	public BuiltinChannel(Workflow workflow, String name, boolean broadcast) {
		this.workflow = workflow;
		this.name = name;
		this.broadcast = broadcast;
	}
	
	public void init() throws Exception {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(workflow.getBroker());
		connectionFactory.setTrustAllPackages(true);
		connection = connectionFactory.createConnection();
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		if (broadcast) destination = (ActiveMQDestination) session.createTopic(name);
		else destination = (ActiveMQDestination) session.createQueue(name);
		
		for (BuiltinChannelConsumer<T> pendingConsumer : pendingConsumers) {
			addConsumer(pendingConsumer);
		}
		pendingConsumers.clear();
		
	}
	
	public void send(T t) throws Exception {
		MessageProducer producer = session.createProducer(destination);
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		producer.setPriority(9);
		ObjectMessage message = session.createObjectMessage();
		message.setObject(t);
		producer.send(message);
		producer.close();
	}

	public void addConsumer(BuiltinChannelConsumer<T> consumer) throws Exception {
		
		if (session == null) {
			pendingConsumers.add(consumer);
			return;
		}
		
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

	public void stop() {
		try {
			for (MessageConsumer c : consumers)
				c.close();
			session.close();
			connection.close();
		}
		catch (Exception ex) {
			// Nothing to do at this stage
		}
	}

	@Override
	public ChannelType getType() {
		if (destination.isQueue())
			return ChannelType.Queue;
		if (destination.isTopic())
			return ChannelType.Topic;
		return ChannelType.UNKNOWN;
	}

	@Override
	public Collection<String> getPhysicalNames() {
		return Collections.singleton(destination.getPhysicalName());
	}
	
	public String getName() {
		return name;
	}
	
}
