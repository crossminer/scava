package org.eclipse.scava.platform.communicationchannel.mbox;

import org.eclipse.scava.platform.communicationchannel.mbox.parser.Email;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelArticle;
import org.eclipse.scava.repository.model.cc.mbox.Mbox;

public class MboxEmail extends CommunicationChannelArticle{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String recipient;
	private String replyTo;
	
	public MboxEmail (Email email, Mbox mbox) {
		this.setCommunicationChannel(mbox);;
		this.setDate(email.getDate());
		this.setUser(email.getFrom());
		this.setArticleId(email.getMessageId());
		this.setReferences(email.getReferences());
		this.replyTo=email.getReplyTo();
		this.setSubject(email.getSubject());
		this.setText(email.getText());
		this.recipient=email.getTo();
		this.setArticleNumber(this.getDate().getTime());
	}

	/**
	 * @return the recipient
	 */
	public String getRecipient() {
		return recipient;
	}

	/**
	 * @return the replyTo
	 */
	public String getReplyTo() {
		return replyTo;
	}

	
	

}
