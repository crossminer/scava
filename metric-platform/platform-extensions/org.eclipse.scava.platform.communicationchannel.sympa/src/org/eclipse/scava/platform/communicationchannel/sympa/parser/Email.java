package org.eclipse.scava.platform.communicationchannel.sympa.parser;


import java.util.Date;

public class Email {
	
	private String messageId;
	private String from;
	private String to;
	private String subject;
	private String text;
	private String replyTo;
	private Date date;
	private String[] references;
	
	public Email(Message message) {
		super();
		messageId = message.getMessageId();
		from = message.getFrom();
		to = message.getTo();
		date = message.getDate();
		replyTo = message.getReplyTo();
		subject = message.getSubject();
		text = message.getText();
		references = message.getReferences().toArray(new String[message.getReferences().size()]);
	}

	public String getFrom() {
		return from;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public String getText() {
		return text;
	}
	
	public String getReplyTo() {
		return replyTo;
	}
	
	public String getMessageId() {
		return messageId;
	}

	public Date getDate() {
		return date;
	}

	public String getTo() {
		return to;
	}

	public String[] getReferences() {
		return references;
	}

	public void print() {
		System.out.println("messageId: |" + messageId +"|");
		System.out.println("\tfrom: |" + from +"|");
		System.out.println("\tto: |" + to +"|");
		System.out.println("\treplyTo: |" + replyTo +"|");
		System.out.println("\tdate: |" + date +"|");
		System.out.println("\tsubject: |" + subject +"|");
		System.out.println("\treferences:");
		for (String reference: references) {
			System.out.println("\t\t|" + reference +"|");
		}
		System.out.println("\ttext: |" + text +"|\n");
	}
}
