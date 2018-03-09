/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.communicationchannel.nntp.local.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class ArticleData extends Pongo {
	
	protected List<String> references = null;
	
	
	public ArticleData() { 
		super();
		dbObject.put("references", new BasicDBList());
		URL.setOwningType("org.eclipse.scava.platform.communicationchannel.nntp.local.model.ArticleData");
		ARTICLENUMBER.setOwningType("org.eclipse.scava.platform.communicationchannel.nntp.local.model.ArticleData");
		ARTICLEID.setOwningType("org.eclipse.scava.platform.communicationchannel.nntp.local.model.ArticleData");
		DATE.setOwningType("org.eclipse.scava.platform.communicationchannel.nntp.local.model.ArticleData");
		FROM.setOwningType("org.eclipse.scava.platform.communicationchannel.nntp.local.model.ArticleData");
		SUBJECT.setOwningType("org.eclipse.scava.platform.communicationchannel.nntp.local.model.ArticleData");
		REFERENCES.setOwningType("org.eclipse.scava.platform.communicationchannel.nntp.local.model.ArticleData");
		BODY.setOwningType("org.eclipse.scava.platform.communicationchannel.nntp.local.model.ArticleData");
	}
	
	public static StringQueryProducer URL = new StringQueryProducer("url"); 
	public static NumericalQueryProducer ARTICLENUMBER = new NumericalQueryProducer("articleNumber");
	public static StringQueryProducer ARTICLEID = new StringQueryProducer("articleId"); 
	public static StringQueryProducer DATE = new StringQueryProducer("date"); 
	public static StringQueryProducer FROM = new StringQueryProducer("from"); 
	public static StringQueryProducer SUBJECT = new StringQueryProducer("subject"); 
	public static StringQueryProducer BODY = new StringQueryProducer("body"); 
	public static ArrayQueryProducer REFERENCES = new ArrayQueryProducer("references");
	
	
	public String getUrl() {
		return parseString(dbObject.get("url")+"", "");
	}
	
	public ArticleData setUrl(String url) {
		dbObject.put("url", url);
		notifyChanged();
		return this;
	}
	public int getArticleNumber() {
		return parseInteger(dbObject.get("articleNumber")+"", 0);
	}
	
	public ArticleData setArticleNumber(int articleNumber) {
		dbObject.put("articleNumber", articleNumber);
		notifyChanged();
		return this;
	}
	public String getArticleId() {
		return parseString(dbObject.get("articleId")+"", "");
	}
	
	public ArticleData setArticleId(String articleId) {
		dbObject.put("articleId", articleId);
		notifyChanged();
		return this;
	}
	public String getDate() {
		return parseString(dbObject.get("date")+"", "");
	}
	
	public ArticleData setDate(String date) {
		dbObject.put("date", date);
		notifyChanged();
		return this;
	}
	public String getFrom() {
		return parseString(dbObject.get("from")+"", "");
	}
	
	public ArticleData setFrom(String from) {
		dbObject.put("from", from);
		notifyChanged();
		return this;
	}
	public String getSubject() {
		return parseString(dbObject.get("subject")+"", "");
	}
	
	public ArticleData setSubject(String subject) {
		dbObject.put("subject", subject);
		notifyChanged();
		return this;
	}
	public String getBody() {
		return parseString(dbObject.get("body")+"", "");
	}
	
	public ArticleData setBody(String body) {
		dbObject.put("body", body);
		notifyChanged();
		return this;
	}
	
	public List<String> getReferences() {
		if (references == null) {
			references = new PrimitiveList<String>(this, (BasicDBList) dbObject.get("references"));
		}
		return references;
	}
	
	
	
}
