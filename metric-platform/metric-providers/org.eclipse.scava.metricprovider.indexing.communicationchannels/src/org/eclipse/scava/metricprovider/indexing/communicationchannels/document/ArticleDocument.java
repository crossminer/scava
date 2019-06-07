package org.eclipse.scava.metricprovider.indexing.communicationchannels.document;

import java.util.Date;

public class ArticleDocument extends Document {

	private String article_id;
	private Long aritcle_number;
	private String collection_name;
	private String message_thread_id;
	

	public ArticleDocument(String uid, String projectName, String messageBody, String creator, Date createdAt,
			String newsgroupName, String subject, String messageThreadID, long articleNumber, String articleId) {

		this.uid = uid;
		this.article_id = articleId;
		this.aritcle_number = articleNumber; 
		this.project_name = projectName;
		this.message_body = messageBody;
		this.creator = creator;
		this.created_at = createdAt;
		this.collection_name = newsgroupName;
		this.subject = subject;
		this.message_thread_id = messageThreadID;
	}

	public String getArticle_id() {
		return article_id;
	}

	public long getAritcle_number() {
		return aritcle_number;
	}

	public String getCollection_name() {
		return collection_name;
	}

	public String getMessage_thread_id() {
		return message_thread_id;
	}

	public void setArticle_id(String article_id) {
		this.article_id = article_id;
	}

	public void setAritcle_number(long aritcle_number) {
		this.aritcle_number = aritcle_number;
	}

	public void setCollection_name(String newsgroup_name) {
		this.collection_name = newsgroup_name;
	}

	public void setMessage_thread_id(String message_thread_id) {
		this.message_thread_id = message_thread_id;
	}

}
