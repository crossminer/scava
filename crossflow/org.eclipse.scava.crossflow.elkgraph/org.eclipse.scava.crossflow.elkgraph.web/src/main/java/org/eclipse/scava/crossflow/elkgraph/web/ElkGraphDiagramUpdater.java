package org.eclipse.scava.crossflow.elkgraph.web;

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

	Timer timer;
	private LanguageAwareDiagramServer languageAwareDiagramServer;
	private static String bareSubject = "StreamMetadataBroadcaster";
	private String experimentId = "";

	// URL of the JMS server
    private static String url = "tcp://localhost:61616";
    
    public ElkGraphDiagramUpdater(LanguageAwareDiagramServer languageAwareDiagramServer) {
    	this.languageAwareDiagramServer = languageAwareDiagramServer;
    	timer = new Timer();
    	SModelRoot sModelRoot = languageAwareDiagramServer.getModel();
    	
    	MessageConsumer messageConsumer = null;
    	
    	System.out.println("sModelRoot=" + sModelRoot);
    	
    	if ( sModelRoot.getChildren() != null ) {
    		try {
    			messageConsumer = createConsumer(sModelRoot);
    		} catch (JMSException e) {
    			e.printStackTrace();
    		}
    	}
    	
	}
    
    private MessageConsumer createConsumer(SModelRoot sModelRoot) throws JMSException {
    	// Getting JMS connection from the server
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        System.out.println("start");
        
        // find label containing experimentId
        for ( SModelElement sModelElement : sModelRoot.getChildren() ) {
        	if ( sModelElement instanceof SLabel && ((SLabel)sModelElement).getId().contentEquals("experiment") ) 
        		experimentId = ((SLabel)sModelElement).getText();
        		break;
        }
 
        // Creating session for sending messages
        Session session = connection.createSession(false,
                Session.AUTO_ACKNOWLEDGE);
        System.out.println("createSession");
        
     // Create the destination (Topic or Queue)
        Destination destination = null;
        	destination = session.createTopic(bareSubject + "." + experimentId);
                
      MessageConsumer messageConsumer = session.createConsumer(destination);
      
      messageConsumer.setMessageListener(new MessageListener() {
	
			@Override
			public void onMessage(Message message) {
				System.out.println("onMessage");
				TextMessage textMessage = (TextMessage) message;
				System.out.println("TextMessage casting");

				try {
					Serializer serializer = new Serializer();
					StreamMetadata streamMetadata = (StreamMetadata) serializer.toObject(textMessage.getText());
					System.out.println("streamMetadata = " + streamMetadata);
					
					 // replace label text with data from Crossflow monitoring queue
					for ( SModelElement sModelElement : sModelRoot.getChildren() ) {
						if ( sModelElement instanceof SNode ) {
							
							for ( SModelElement sModelElementChild : sModelElement.getChildren() ) {
								if ( sModelElementChild instanceof SLabel && ((SLabel)sModelElementChild).getText().startsWith("Q_") ) {
									SLabel sLabel = (SLabel) sModelElementChild;
									
									for ( Stream stream : streamMetadata.getStreams() ) {
										if ( stream.getName().contentEquals(bareSubject + "." + sLabel.getText().substring(2)) ) {
											System.out.println("stream = " + stream);
											
											// TODO: replace this by the use of nodes contained inside sModelElementChild and assign their labels instead
											sLabel.setText(sLabel.getText() + " (S: " + stream.getNumberOfSubscribers() + ")"); 
											
										}// if stream name equals label text
										
									}// stream metadata iteration
									
								}// if elk node element is a Crossflow queue
								
							}// elk node element iteration
							
						}// if elk model element is elk node
												
					}// elk root model element iteration
					
					
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
