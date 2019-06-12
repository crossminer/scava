package org.eclipse.scava.platform.communicationchannel.mbox;

import org.eclipse.scava.platform.communicationchannel.mbox.parser.Email;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelArticle;
import org.eclipse.scava.repository.model.cc.mbox.Mbox;

public class MboxEmail extends CommunicationChannelArticle{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String sender;
	private String recipient;
	private String replyTo;
	
	public MboxEmail (Email email, Mbox mbox) {
		this.setCommunicationChannel(mbox);;
		this.setDate(email.getDate());
		this.setSender(email.getFrom());
		this.setArticleId(email.getMessageId());
		this.setReferences(email.getReferences());
		this.setReplyTo(email.getReplyTo());
		this.setSubject(email.getSubject());
		this.setText(email.getText());
		this.setRecipient(email.getTo());
		this.setArticleNumber(this.getDate().getTime());
	}

	/**
	 * @return the sender
	 */
	public String getSender() {
		return sender;
	}

	/**
	 * @return the recipient
	 */
	public String getRecipient() {
		return recipient;
	}

	/**
	 * @param sender the sender to set
	 */
	public void setSender(String sender) {
		this.sender = sender;
	}

	/**
	 * @param recipient the recipient to set
	 */
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	/**
	 * @return the replyTo
	 */
	public String getReplyTo() {
		return replyTo;
	}

	/**
	 * @param replyTo the replyTo to set
	 */
	public void setReplyTo(String replyTo) {
		this.replyTo = replyTo;
	}
	
	

}
