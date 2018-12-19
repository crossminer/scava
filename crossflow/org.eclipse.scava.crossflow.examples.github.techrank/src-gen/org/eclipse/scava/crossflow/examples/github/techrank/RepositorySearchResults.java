package org.eclipse.scava.crossflow.examples.github.techrank;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Collection;
import java.util.List;
import java.util.LinkedList;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.Job;
import org.eclipse.scava.crossflow.runtime.Stream;

public class RepositorySearchResults extends Stream<RepositorySearchResult> {
		
	public RepositorySearchResults(Workflow workflow) throws Exception {
		super(workflow);
		
		pre.put("ResultsSink", (ActiveMQDestination) session.createQueue("RepositorySearchResultsPre.ResultsSink." + workflow.getInstanceId()));
		destination.put("ResultsSink", (ActiveMQDestination) session.createQueue("RepositorySearchResultsDestination.ResultsSink." + workflow.getInstanceId()));
		post.put("ResultsSink", (ActiveMQDestination) session.createQueue("RepositorySearchResultsPost.ResultsSink." + workflow.getInstanceId()));
		
		for (String consumerId : pre.keySet()) {
			ActiveMQDestination preQueue = pre.get(consumerId);
			ActiveMQDestination destQueue = destination.get(consumerId);
			ActiveMQDestination postQueue = post.get(consumerId);
			
			if (workflow.isMaster()) {
				MessageConsumer preConsumer = session.createConsumer(preQueue);
				consumers.add(preConsumer);
				preConsumer.setMessageListener(new MessageListener() {
	
					@Override
					public void onMessage(Message message) {
						try {
								MessageProducer producer = session.createProducer(destQueue);
								producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
								producer.send(message);
								producer.close();
							
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
					
				});
				
				MessageConsumer destinationConsumer = session.createConsumer(destQueue);
				consumers.add(destinationConsumer);
				destinationConsumer.setMessageListener(new MessageListener() {
	
					@Override
					public void onMessage(Message message) {
						try {
							ObjectMessage objectMessage = (ObjectMessage) message;
							Job job = (Job) objectMessage.getObject();
							if (workflow.getCache() != null && !job.isCached()) {
								workflow.getCache().cache(job);
							}
							MessageProducer producer = session.createProducer(postQueue);
							producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
							producer.send(message);
							producer.close();
						}
						catch (Exception ex) {
							ex.printStackTrace();
						}
					}
					
				});
			}
		}
	}
	
	public void addConsumer(RepositorySearchResultsConsumer consumer, String consumerId) throws Exception {
	
		ActiveMQDestination postQueue = post.get(consumerId);
		
		//only connect if the consumer exists (for example it will not in a master_bare situation)
		if(consumer!=null) {
		
			MessageConsumer messageConsumer = session.createConsumer(postQueue);
			consumers.add(messageConsumer);
			messageConsumer.setMessageListener(new MessageListener() {
		
				@Override
				public void onMessage(Message message) {
					ObjectMessage objectMessage = (ObjectMessage) message;
					try {
						RepositorySearchResult repositorySearchResult = (RepositorySearchResult) objectMessage.getObject();
						consumer.consumeRepositorySearchResultsWithNotifications(repositorySearchResult);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}	
			});
		}
	
	}

}