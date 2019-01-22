package org.eclipse.scava.crossflow.runtime;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.command.ActiveMQDestination;

public abstract class JobStream<T extends Job> implements Stream {
	
	protected Map<String, ActiveMQDestination> destination;
	protected Map<String, ActiveMQDestination> pre;
	protected Map<String, ActiveMQDestination> post;
	protected Connection connection;
	protected Session session;
	protected Workflow workflow;
	protected List<MessageConsumer> consumers = new LinkedList<MessageConsumer>();
	protected Task cacheManagerTask = new Task() {
		
		@Override
		public Workflow getWorkflow() {
			return workflow;
		}
		
		@Override
		public String getId() {
			return "CacheManager";
		}
	};
	
	public JobStream(Workflow workflow) throws Exception {
		this.workflow = workflow;
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(workflow.getBroker());
		connectionFactory.setTrustAllPackages(true);
		connection = connectionFactory.createConnection();
		connection.start();
		session = connection.createSession(false, ActiveMQSession.INDIVIDUAL_ACKNOWLEDGE);
				
		destination = new HashMap<String, ActiveMQDestination>();
		pre = new HashMap<String, ActiveMQDestination>();
		post = new HashMap<String, ActiveMQDestination>();
	}
	
	public void send(T job, String taskId) {
		try {
			ActiveMQDestination d = null;
			// if the sender is one of the targets of this stream, it has re-sent a message
			// so it should only be put in the relevant physical queue
			if ((d = pre.get(taskId)) != null) {
				MessageProducer producer = session.createProducer(d);
				producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
				job.setDestination(getClass().getSimpleName());
				producer.send(session.createTextMessage(workflow.getSerializer().toString(job)));
				producer.close();
			} else
				// otherwise the sender must be the source of this stream so intends to
				// propagate its messages to all the physical queues
				for (Entry<String, ActiveMQDestination> e : pre.entrySet()) {			
					MessageProducer producer = session.createProducer(e.getValue());
					producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
					job.setDestination(this.getClass().getSimpleName());
					producer.send(session.createTextMessage(workflow.getSerializer().toString(job)));
					producer.close();
				}
		} catch (Exception ex) {
			workflow.reportInternalException(ex);
		}
	}
	
	public Collection<String> getDestinationNames() {
		List<String> ret = new LinkedList<String>();
		for (ActiveMQDestination d : post.values())
			ret.add(d.getPhysicalName());
		return ret;
	}
	
	public void stop() throws JMSException {
		for (MessageConsumer c : consumers)
			c.close();
		session.close();
		connection.close();
	}

	@Override
	public boolean isBroadcast() {
		return destination.values().iterator().next().isTopic();
	}
}
