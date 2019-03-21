package org.eclipse.scava.crossflow.elkgraph.web;

import java.net.ConnectException;
import java.util.Timer;
import java.util.TimerTask;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.eclipse.scava.crossflow.runtime.Serializer;
import org.eclipse.scava.crossflow.runtime.utils.StreamMetadata;
import org.eclipse.scava.crossflow.runtime.utils.StreamMetadata.Stream;

import io.typefox.sprotty.api.SLabel;
import io.typefox.sprotty.api.SModelElement;
import io.typefox.sprotty.api.SModelRoot;
import io.typefox.sprotty.api.SNode;
import io.typefox.sprotty.server.xtext.LanguageAwareDiagramServer;

public class ElkGraphDiagramUpdater {

	private static final String IN_FLIGHT_COUNT = "_InFlightCount";
	private static final String SUBSCRIBER_COUNT = "_SubscriberCount";
	private static final String FAILED_JOBS_COUNT = "_FailedJobs";
	private static final String INTERNAL_EXCEPTION_COUNT = "_InternalExceptions";
	
	private static final String IN_FLIGHT_LABEL_PRE = "InFlight: ";
	private static final String SUBSCRIBER_LABEL_PRE = "Subscribers: ";
	private static final String FAILED_JOBS_LABEL_PRE = "Failed Jobs: ";
	private static final String INTERNAL_EXCEPTION_LABEL_PRE = "Exceptions: ";
	
	private static final String METADATA_STREAM_ID = "StreamMetadataBroadcaster";
	private static final String QUEUE_ID_PRE = "Q_";
	private static final String GRAPH_ID_PRE = "G_";
	private static Timer updateTimer;
	private LanguageAwareDiagramServer languageAwareDiagramServer;
	private static String bareSubject = "StreamMetadataBroadcaster";
	private String experimentId = "";
	MessageConsumer messageConsumer = null;
	private boolean running = false;

	// URL of the JMS server
	private static String url = "tcp://localhost:61616";

	public ElkGraphDiagramUpdater(LanguageAwareDiagramServer languageAwareDiagramServer, int delay, int interval) {
		this.languageAwareDiagramServer = languageAwareDiagramServer;
		updateTimer = new Timer();
		updateTimer.scheduleAtFixedRate(new CrossflowDiagramUpdaterTask(), delay, interval);

		SModelRoot sModelRoot = languageAwareDiagramServer.getModel();
	}

	class CrossflowDiagramUpdaterTask extends TimerTask {
		public void run() {
			SModelRoot sModelRoot = languageAwareDiagramServer.getModel();

			if (sModelRoot.getChildren() != null) {
				try {
					messageConsumer = createConsumer(sModelRoot);
					if (running == false) {
						System.out.println("Successfully created CrossflowDiagramUpdater consumer.\n");
						running = true;
					}

				} catch (JMSException e) {
					running = false;
					if (e.getCause() instanceof ConnectException) {
						System.err.println(
								"Unable to connect to ActiveMQ broker.\nIs it running (may require starting a Crossflow workflow)?.\n");
					} else {
						e.printStackTrace();
					}
				}
			}

			languageAwareDiagramServer.updateModel(sModelRoot);
		}
	}

	private MessageConsumer createConsumer(SModelRoot sModelRoot) throws JMSException {

		// Getting JMS connection from the server
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		Connection connection = connectionFactory.createConnection();
		connection.start();

		// find label containing experimentId
		if (sModelRoot != null && sModelRoot.getId() != "")
			experimentId = sModelRoot.getId().substring(GRAPH_ID_PRE.length());

		// Creating session for sending messages
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		// Create the destination (Topic or Queue)
		Destination destination = session.createTopic(bareSubject + "." + experimentId);

		MessageConsumer messageConsumer = session.createConsumer(destination);

		messageConsumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {
//				System.out.println("Received model update message .. ");
				TextMessage textMessage = (TextMessage) message;

				try {
					Serializer serializer = new Serializer();
					StreamMetadata streamMetadata = (StreamMetadata) serializer.toObject(textMessage.getText());
//					System.out.println("streamMetadata = " + streamMetadata);

					// replace label text with data from Crossflow monitoring queue
					for (SModelElement sModelElement : sModelRoot.getChildren()) {

						if (sModelElement instanceof SNode
								&& sModelElement.getId().startsWith(GRAPH_ID_PRE + experimentId + "." + QUEUE_ID_PRE)
								|| sModelElement.getId().startsWith(GRAPH_ID_PRE + experimentId + "." + METADATA_STREAM_ID)) {
		
							for (SModelElement sModelElementChild : sModelElement.getChildren()) {

								if (sModelElementChild instanceof SNode) {
//									System.out.println("sModelElementChild.id = " + sModelElementChild.getId());
									
									for (SModelElement sModelElementChildChild : sModelElementChild.getChildren()) {
//										System.out.println("sModelElementChildChild.id = " + sModelElementChildChild.getId());										
										
										if (sModelElementChildChild instanceof SLabel) {
											SLabel sLabel = (SLabel) sModelElementChildChild;

											for (Stream stream : streamMetadata.getStreams()) {
//												System.out.println("stream.getName() = " + stream.getName());
												
												// update workflow data
												if (sModelElementChild.getId().contains(IN_FLIGHT_COUNT)) {
													sLabel.setText(IN_FLIGHT_LABEL_PRE + stream.getInFlight());
												}
												else if (sModelElementChild.getId().contains(SUBSCRIBER_COUNT)) {
													sLabel.setText(SUBSCRIBER_LABEL_PRE + stream.getNumberOfSubscribers());
												}
												// update workflow meta data
												else if (sModelElementChildChild.getId().contains(FAILED_JOBS_COUNT)) {
													sLabel.setText(FAILED_JOBS_LABEL_PRE + stream.getSize());
												}
												else if (sModelElementChildChild.getId().contains(INTERNAL_EXCEPTION_COUNT)) {
													sLabel.setText(INTERNAL_EXCEPTION_LABEL_PRE + stream.getSize());
												}
												
											} // stream metadata iteration

										} // if elk node element is a Crossflow queue

									} // if elk node child element is elk node

								} // elk node child element iteration

							} // elk node element iteration

						} // if elk model element is elk node

					} // elk root model element iteration

				} catch (Exception ex) {
					System.err.println(ex);
				} finally {
					try {
						message.acknowledge();
					} catch (Exception ex) {
						System.err.println(ex);
					}
				}
			}
		});

		return messageConsumer;
	}

}
