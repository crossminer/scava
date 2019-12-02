package org.eclipse.scava.platform.communicationchannel.mbox.parser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.apache.commons.mail.util.MimeMessageParser;
import org.apache.commons.net.nntp.Article;
import org.eclipse.scava.platform.logging.OssmeterLogger;

public class MBoxMessage extends Message {
	
	private MimeMessage mimeMessage;
	private MimeMessageParser mimeMessageParser;
	private Session session;
	
	private static OssmeterLogger logger;
	
	static
	{
		logger = (OssmeterLogger) OssmeterLogger.getLogger("platform.communicationchannel.mbox.parser");
	}

	public MBoxMessage(String stringMessage) {
		super();
		Properties props = System.getProperties();
		props.setProperty("mail.mime.address.strict", "false");
		props.setProperty("mail.mime.decodetext.strict", "false");
//		props.setProperty("mail.mime.decodefilename", "true");
//		props.setProperty("mail.mime.decodeparameters", "true");
		props.setProperty("mail.mime.charset", "utf-8");
		props.setProperty("mail.mime.parameters.strict", "false");
		props.setProperty("mail.mime.base64.ignoreerrors", "true");
		props.setProperty("mail.mime.uudecode. ignoreerrors", "true");
		props.setProperty("mail.mime.uudecode.ignoremissingbeginend", "true");
		props.setProperty("mail.mime.multipart.allowempty", "true");
		props.setProperty("mail.mime.ignoreunknownencoding", "true");
		props.setProperty("mail.mime.ignoremultipartencoding", "false");
//		props.setProperty("mail.mime.allowencodedmessages", "true");
		session = Session.getDefaultInstance(props);
		parse(stringMessage);
	}

	@Override
	public String getMessageId() {
		String messageID = "";
		try {
			messageID = mimeMessage.getMessageID();
		} catch (MessagingException e) {
			logger.error("MessageID not found:", e);
		}
		if (messageID==null || messageID.isEmpty())
			logger.warn("MessageID null or zero length.");
		return messageID;
	}

	@Override
	public String getTo() {
		List<Address> to = null;
		try {
			to = mimeMessageParser.getTo();
		} catch (Exception e) {
			logger.error("Field TO not found by the parser: ", e);
		}
		if ((to != null) && (to.size()>0))
			return to.get(0).toString();
		String toString = "";
		try {
			toString = mimeMessage.getHeader("To", " ");
		} catch (MessagingException e) {
			logger.error("Field TO not found from MIME-message directly: ",e);
		}
		if ((toString != null) && (toString.isEmpty()))
			return toString;
		logger.warn("Field TO either null or empty.");		
		return "";
	}

	@Override
	public Date getDate() {
		Date date = null;
		try {
			date =  mimeMessage.getSentDate();
		} catch (MessagingException e) {
			logger.error("Field DATE not found");
		}
		if (date==null || date.toString().isEmpty())
			logger.warn("Field DATE either null or empty.");
		return date;
	}

	@Override
	public String getFrom() {
		String from = "";
		try {
			from = mimeMessageParser.getFrom();
		} catch (Exception e) {
			logger.error("Field FROM not found: ",e);
		}
		if (from==null || from.isEmpty())
			logger.warn("Field FROM either null or empty");
		return from;
	}

	@Override
	public String getSubject() {
		String subject = "";
		try {
			subject = mimeMessageParser.getSubject();
		} catch (Exception e) {
			logger.error("Field SUBJECT not found: ",e);
		}
		if (subject==null || subject.isEmpty())
			logger.warn("Field SUBJECT either null or empty.");
		return subject;
	}

	@Override
	public String getReplyTo() {
		String replyTo = "";
		try {
			mimeMessageParser.getReplyTo();
		} catch (Exception e) {
			logger.error("Field REPLY not found:",e);
		}
		if (replyTo==null || replyTo.isEmpty())
			logger.warn("Field REPLY either null or empty.");
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
			logger.error("Field REFERENCES not found: ",e);
		}
		Set<String> references = new TreeSet<String>();
		if (referenceString == null || references.isEmpty()) {
			logger.warn("Field REFERENCES is null or emtpy");
			return references;
		}
		referenceString = referenceString.trim();
		if (referenceString.isEmpty()) {
			logger.warn("Field REFERENCES is empty");
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
		if(mimeMessageParser!=null)
		{
			article.setArticleId(getMessageId());
			if (getDate()!=null)
				article.setDate(getDate().toString());
			article.setFrom(getFrom());
			article.setSubject(getSubject());
			for (String reference: getReferences())
				article.addReference(reference);
		}
		return article;
	}

	private void parse(String stringMessage) {
		logger.debug("Parsing message:"+stringMessage);
		InputStream is = new ByteArrayInputStream(stringMessage.toString().getBytes(Charset.forName("UTF-8")));
		try {
			mimeMessage = new MimeMessage(session, is);
			mimeMessageParser = new MimeMessageParser(mimeMessage);
			try {
				mimeMessageParser.parse();
			} catch (Exception e) {
				logger.error("Error while parsing MIME message: ", e);
			}
		} catch (MessagingException e) {
			logger.error("Error while converting message into MIME: ",e);
		}
	}
	
	@Override
	public void print() {
		System.out.println("MESSAGE---");
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
