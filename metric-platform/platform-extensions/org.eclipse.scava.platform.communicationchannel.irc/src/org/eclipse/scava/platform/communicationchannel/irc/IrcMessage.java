package org.eclipse.scava.platform.communicationchannel.irc;

import org.eclipse.scava.platform.communicationchannel.irc.parser.Message;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelArticle;

public class IrcMessage extends CommunicationChannelArticle {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String time;
	private String user;
	private String text;
	private int userId;
	private int msgId;
	private String channelName;
	

	public IrcMessage (Message message, String channelName) {
		msgId = message.getId();
		user = message.getUser();
		userId = message.getUserId();
		text = message.getText();
		time = message.getTime();
		this.channelName = channelName;
	}


	public String getTime() {
		return time;
	}


	public String getUser() {
		return user;
	}


	public String getText() {
		return text;
	}


	public int getUserId() {
		return userId;
	}


	public int getMsgId() {
		return msgId;
	}


	public String getChannelName() {
		return channelName;
	}	
	
	

}
