package org.eclipse.scava.crossflow.nlp.stackexchange.gen;

import java.util.List;

import javax.jms.DeliveryMode;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQDestination;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.Job;
import org.eclipse.scava.crossflow.runtime.JobStream;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.activemq.command.ActiveMQBytesMessage;

public class PlainTextQueue extends JobStream<Post> {
		
	public PlainTextQueue(Workflow workflow, boolean enablePrefetch) throws Exception {
		super(workflow);
		
		ActiveMQDestination postQ;
			pre.put("PlainText", (ActiveMQDestination) session.createQueue("PlainTextQueuePre.PlainText." + workflow.getInstanceId()));
			destination.put("PlainText", (ActiveMQDestination) session.createQueue("PlainTextQueueDestination.PlainText." + workflow.getInstanceId()));
			postQ = (ActiveMQDestination) session.createQueue("PlainTextQueuePost.PlainText." + workflow.getInstanceId()
					+ (enablePrefetch?"":"?consumer.prefetchSize=1"));		
			post.put("PlainText", postQ);			
		
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
							workflow.cancelTermination();
							String messageText = "";
							if (message instanceof ActiveMQTextMessage) {
    							ActiveMQTextMessage amqMessage = (ActiveMQTextMessage) message;
    							messageText = amqMessage.getText();
							} else {
    							ActiveMQBytesMessage bm = (ActiveMQBytesMessage) message;
    							byte data[] = new byte[(int) bm.getBodyLength()];
    							bm.readBytes(data);
    							messageText = new String(data);
							}
							Job job = (Job) workflow.getSerializer().toObject(messageText);
							
							if (workflow.getCache() != null && workflow.getCache().hasCachedOutputs(job)) {
								
								workflow.setTaskInProgess(cacheManagerTask);
								List<Job> cachedOutputs = workflow.getCache().getCachedOutputs(job);
								workflow.setTaskWaiting(cacheManagerTask);
								
								for (Job output : cachedOutputs) {
									if (output.getDestination().equals("CodeDetectorQueue")) {
										workflow.cancelTermination();
										((StackExchangeIndexingWorkFlow) workflow).getCodeDetectorQueue().send((Post) output, consumerId);
									}
									
								}
							} else {
								MessageProducer producer = session.createProducer(destQueue);
								producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
								producer.send(message);
								producer.close();
							}
							
						} catch (Exception ex) {
							workflow.reportInternalException(ex);
						} finally { 
							try {
								message.acknowledge();
							} catch (Exception ex) {
								workflow.reportInternalException(ex);
							} 
						}
					}					
				});
				
				MessageConsumer destinationConsumer = session.createConsumer(destQueue);
				consumers.add(destinationConsumer);
				destinationConsumer.setMessageListener(new MessageListener() {
	
					@Override
					public void onMessage(Message message) {
						try {
							workflow.cancelTermination();
							String messageText = "";
							if (message instanceof ActiveMQTextMessage) {
    							ActiveMQTextMessage amqMessage = (ActiveMQTextMessage) message;
    							messageText = amqMessage.getText();
							} else {
    							ActiveMQBytesMessage bm = (ActiveMQBytesMessage) message;
    							byte data[] = new byte[(int) bm.getBodyLength()];
    							bm.readBytes(data);
    							messageText = new String(data);
							}
							
							Job job = (Job) workflow.getSerializer().toObject(messageText);
							if (workflow.getCache() != null && !job.isCached())
								if(job.isTransactional())
									workflow.getCache().cacheTransactionally(job);
								else
									workflow.getCache().cache(job);
							if(job.isTransactionSuccessMessage())
								return;
							MessageProducer producer = session.createProducer(postQueue);
							producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
							producer.send(message);
							producer.close();
						}
						catch (Exception ex) {
							workflow.reportInternalException(ex);
						} finally { 
							try {
								message.acknowledge();
							} catch (Exception ex) {
								workflow.reportInternalException(ex);
							} 
						}
					}					
				});
			}
		}
	}
	
	public void addConsumer(PlainTextQueueConsumer consumer, String consumerId) throws Exception {
	
		ActiveMQDestination postQueue = post.get(consumerId);
		
		//only connect if the consumer exists (for example it will not in a master_bare situation)
		if(consumer!=null) {
		
			MessageConsumer messageConsumer = session.createConsumer(postQueue);
			consumers.add(messageConsumer);
			messageConsumer.setMessageListener(new MessageListener() {
		
				@Override
				public void onMessage(Message message) {
					String messageText = "";
					try {
						if (message instanceof ActiveMQTextMessage) {
							ActiveMQTextMessage amqMessage = (ActiveMQTextMessage) message;
							messageText = amqMessage.getText();
						} else {
							ActiveMQBytesMessage bm = (ActiveMQBytesMessage) message;
							byte data[] = new byte[(int) bm.getBodyLength()];
							bm.readBytes(data);
							messageText = new String(data);
						}
						Post post = (Post) workflow.getSerializer().toObject(messageText);
						consumer.consumePlainTextQueueWithNotifications(post);
					} catch (Exception ex) {
						workflow.reportInternalException(ex);
					} finally { 
						try {
							message.acknowledge();
						} catch (Exception ex) {
							workflow.reportInternalException(ex);
						} 
					}
				}	
			});
		}
	
	}

}

