package org.eclipse.scava.crossflow.runtime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
	protected MessageConsumer messageConsumer;
	protected List<BuiltinTopicConsumer<T>> consumers = new ArrayList<>();
	protected String name;
	
	public BuiltinTopic(Workflow workflow, String name) {
		this.workflow = workflow;
		this.name = name;
	}
	
	public void init() throws Exception {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(workflow.getBroker());
		connectionFactory.setTrustAllPackages(true);
		connection = connectionFactory.createConnection();
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		destination = (ActiveMQDestination) session.createTopic(name);
		
		
		messageConsumer = session.createConsumer(destination);
		messageConsumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {
				ObjectMessage objectMessage = (ObjectMessage) message;
				for (BuiltinTopicConsumer<T> consumer : consumers) {
					new Timer().schedule(new TimerTask() {
						
						@Override
						public void run() {
							try {
								consumer.consume((T) objectMessage.getObject());
							} catch (JMSException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}, 0);
					
				}
			}
		});
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

	public void addConsumer(BuiltinTopicConsumer<T> consumer) throws Exception {
		consumers.add(consumer);
	}

	public void stop() throws JMSException {
		messageConsumer.close();
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
	
	public String getName() {
		return name;
	}
	
}
