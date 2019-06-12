package org.eclipse.scava.platform.communicationchannel.sympa.parser;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.mail.util.MimeMessageParser;
import org.apache.commons.net.nntp.Article;

public class PlainTextMessage extends Message {
	
	private MimeMessage mimeMessage;
	private MimeMessageParser mimeMessageParser;

	public PlainTextMessage(File file) {
		super();
		parse(file);
	}

	@Override
	public String getMessageId() {
		String messageID = "";
		try {
			messageID = mimeMessage.getMessageID();
		} catch (MessagingException e) {
			System.err.println("MESSAGEID NOT FOUND");
			e.printStackTrace();
		}
		if (messageID==null || messageID.length()==0)
			System.err.println("MESSAGEID NULL OR ZERO LENGTH");
		return messageID;
	}

	@Override
	public String getTo() {
		List<Address> to = null;
		try {
			to = mimeMessageParser.getTo();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("TO NOT FOUND FROM PARSER");
			System.err.println("e.toString(): " + e.toString());
		}
		if ((to != null) && (to.size()>0))
			return to.get(0).toString();
		String toString = "";
		try {
			toString = mimeMessage.getHeader("To", " ");
		} catch (MessagingException e) {
			System.err.println("TO NOT FOUND FROM MIME-MESSAGE DIRECTLY");
			e.printStackTrace();
		}
		if ((toString != null) && (toString.length()>0))
			return toString;
		System.err.println("TO NULL OR ZERO LENGTH");		
		return "";
	}

	@Override
	public Date getDate() {
		Date date = null;
		try {
			date =  mimeMessage.getSentDate();
		} catch (MessagingException e) {
			e.printStackTrace();
			System.err.println("DATE NOT FOUND");
		}
		if (date==null || date.toString().length()==0)
			System.err.println("DATE NULL OR ZERO LENGTH");
		return date;
	}

	@Override
	public String getFrom() {
		String from = "";
		try {
			from = mimeMessageParser.getFrom();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("FROM NOT FOUND");
		}
		if (from==null || from.length()==0)
			System.err.println("FROM NULL OR ZERO LENGTH");
		return from;
	}

	@Override
	public String getSubject() {
		String subject = "";
		try {
			subject = mimeMessageParser.getSubject();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("SUBJECT NOT FOUND");
		}
		if (subject==null || subject.length()==0)
			System.err.println("SUBJECT NULL OR ZERO LENGTH");
		return subject;
	}

	@Override
	public String getReplyTo() {
		String replyTo = "";
		try {
			mimeMessageParser.getReplyTo();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("REPLY TO NOT FOUND");
		}
//		if (replyTo==null || replyTo.length()==0)
//			System.out.println("REPLY TO NULL OR ZERO LENGTH");
		return replyTo;
	}

	@Override
	public String getText() {
		return mimeMessageParser.getPlainContent();
	}

	@Override
	public Set<String> getReferences() {
		String referenceString = "";
		try {
			referenceString = mimeMessage.getHeader("References", " ");
		} catch (MessagingException e) {
			e.printStackTrace();
			System.err.println("REFERENCES NOT FOUND");
		}
		Set<String> references = new TreeSet<String>();
		if (referenceString == null) {
//			System.err.println("REFERENCES NULL");
			return references;
		}
		referenceString = referenceString.trim();
		if (referenceString.length()==0) {
//			System.err.println("REFERENCES ZERO LENGTH");
			return references;
		}
		for (String reference: referenceString.replaceAll("\\s", "").split(">")) {
			references.add(reference+">");
		}
		return references;
	}

	@Override
	public Article toArticle() {
		Article article = new Article();
		article.setArticleId(getMessageId());
		article.setDate(getDate().toString());
		article.setFrom(getFrom());
		article.setSubject(getSubject());
		for (String reference: getReferences())
			article.addReference(reference);
		return article;
	}
	
	private void parse(File file) {
		InputStream is = null;
		try {
			is = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			mimeMessage = new MimeMessage(null, is);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		mimeMessageParser = new MimeMessageParser(mimeMessage);
		try {
			mimeMessageParser.parse();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void print() {
		System.out.println("MessageId: " + getMessageId());
		System.out.println("Date: " + getDate());
		System.out.println("From: " + getFrom());
		System.out.println("To: " + getTo());
		System.out.println("replyTo: " + getReplyTo());
		System.out.println("Subject: " + getSubject());
		System.out.println("References: " + getReferences());
		System.out.println("Text: " + getText());
	}

}
