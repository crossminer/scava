package org.eclipse.scava.platform.communicationchannel.irc;

import org.eclipse.scava.platform.communicationchannel.irc.parser.Message;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelArticle;
import org.eclipse.scava.repository.model.cc.irc.Irc;

public class IrcMessage extends CommunicationChannelArticle {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int userId;
	

	public IrcMessage (Message message, Irc irc) {
		this.setArticleId(message.getId());
		this.setArticleNumber(message.getDate().getTime());
		this.setCommunicationChannel(irc);
		//this.setMessageThreadId(message.getThreadId);
		//subject
		this.setText(message.getText());
		this.setUser(message.getUser());
		this.setDate(message.getDate());
		userId = message.getUserId();
	}

	public int getUserId() {
		return userId;
	}	

}
