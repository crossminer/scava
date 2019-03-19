package org.eclipse.scava.crossflow.web.elkgraph;

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

	private static final String IN_FLIGHT_LABEL_PRE = "inF: ";
	private static final String STREAM_NAME_POST = "Post.";
	private static final String QUEUE_ID_PRE = "Q_";
	private static final String GRAPH_ID_PRE = "G_";
	private static final String SUBSCRIBER_LABEL_PRE = "(Sub: ";
	private static Timer updateTimer;
	private LanguageAwareDiagramServer languageAwareDiagramServer;
	private static String bareSubject = "StreamMetadataBroadcaster";
	private String experimentId = "";
	MessageConsumer messageConsumer = null;

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
				} catch (JMSException e) {
					e.printStackTrace();
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
				System.out.println("Received model update message .. ");
				TextMessage textMessage = (TextMessage) message;

				try {
					Serializer serializer = new Serializer();
					StreamMetadata streamMetadata = (StreamMetadata) serializer.toObject(textMessage.getText());
					// System.out.println("streamMetadata = " + streamMetadata);

					// replace label text with data from Crossflow monitoring queue
					for (SModelElement sModelElement : sModelRoot.getChildren()) {
						if (sModelElement instanceof SNode
								&& sModelElement.getId().startsWith(GRAPH_ID_PRE + experimentId + "." + QUEUE_ID_PRE)) {

							for (SModelElement sModelElementChild : sModelElement.getChildren()) {
								if (sModelElementChild instanceof SLabel) {
									SLabel sLabel = (SLabel) sModelElementChild;

									for (Stream stream : streamMetadata.getStreams()) {
										// System.out.println("stream.getName() = " + stream.getName());
										String sLabelText = sLabel.getText();
										if (sLabelText.contains(SUBSCRIBER_LABEL_PRE))
											sLabelText = sLabelText.substring(0, sLabelText.indexOf(SUBSCRIBER_LABEL_PRE));

										if (stream.getName().startsWith(sLabelText + STREAM_NAME_POST)) {
											// System.out.println("stream = " + stream);

											// TODO: replace this by the use of nodes contained inside
											// sModelElementChild and assign their labels instead
											sLabel.setText(sLabelText + " " + SUBSCRIBER_LABEL_PRE
													+ stream.getNumberOfSubscribers() + " | " + IN_FLIGHT_LABEL_PRE + stream.getInFlight() + ")");

										} // if stream name equals label text

									} // stream metadata iteration

								} // if elk node element is a Crossflow queue

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