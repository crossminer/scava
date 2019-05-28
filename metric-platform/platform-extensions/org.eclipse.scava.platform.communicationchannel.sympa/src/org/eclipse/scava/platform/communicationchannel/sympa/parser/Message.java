package org.eclipse.scava.platform.communicationchannel.sympa.parser;

import java.util.Date;
import java.util.Set;

import org.apache.commons.net.nntp.Article;

public abstract class Message {

	abstract public String getMessageId();

	abstract public String getTo();

	abstract public Date getDate();

	abstract public String getFrom();

	abstract public String getSubject();
	
	abstract public String getReplyTo();
	
	abstract public String getText();

	abstract public Set<?> getReferences();
	
	abstract public Article toArticle();
	
	public abstract void print();

}
