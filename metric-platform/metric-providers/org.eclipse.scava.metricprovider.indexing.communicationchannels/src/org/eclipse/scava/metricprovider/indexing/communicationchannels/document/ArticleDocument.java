package org.eclipse.scava.metricprovider.indexing.communicationchannels.document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArticleDocument extends DocumentAbstract {

	
	private String article_id;
	private String communication_channel_id;
	private String project_name;
	private String message_body;
	private String subject;
	private String creator;
	private Date created_at;
	
	// NLP
	private List<String> emotional_dimension;
	private String sentiment;
	private String plain_text;
	private String request_reply_classification;
	private String content_class;
	private Boolean contains_code;
	private List<String> threads_id;

	public ArticleDocument(String uid, String projectName, String collectionName,
			String articleId,String subject, String messageBody, String creator, Date createdAt) {

		this.uid = uid;
		this.article_id = articleId; 
		this.project_name = projectName;
		this.message_body = messageBody;
		this.creator = creator;
		this.created_at = createdAt;
		this.communication_channel_id = collectionName;
		this.subject = subject;
	}

	public String getSubject() {
		return subject;
	}
	
	public String getUid() {
		return uid;
	}
	public String getProject_name() {
		return project_name;
	}
	public String getMessage_body() {
		return message_body;
	}
	public String getCreator() {
		return creator;
	}
	public Date getCreated_at() {
		return created_at;
	}

	public String getArticle_id() {
		return article_id;
	}

	public String getCommunication_channel_id() {
		return communication_channel_id;
	}

	public List<String> getThreads_id() {
		return threads_id;
	}
	
	public String getSentiment() {
		return sentiment;
	}
	
	public String getPlain_text() {
		return plain_text;
	}
	
	public String getRequest_reply_classification() {
		return request_reply_classification;
	}
	
	public String getContent_class() {
		return content_class;
	}
	
	public Boolean getContains_code() {
		return contains_code;
	}

	public void setCollection_name(String newsgroup_name) {
		this.communication_channel_id = newsgroup_name;
	}

	public void addThread_id(String thread_id) {
		if(this.threads_id==null)
			this.threads_id = new ArrayList<String>();
		this.threads_id.add(thread_id);
	}
	
	public void addEmotional_dimension(String emotional_dimension) {
		if(this.emotional_dimension==null)
			this.emotional_dimension = new ArrayList<String>();
		this.emotional_dimension.add(emotional_dimension);
	}
	
	public void setSentiment(String sentiment) {
		this.sentiment = sentiment;
	}
	
	public void setPlain_text(String plain_text) {
		this.plain_text = plain_text;
	}
	
	public void setRequest_reply_classification(String request_reply_classification) {
		this.request_reply_classification = request_reply_classification;
	}
	
	public void setContent_class(String content_class) {
		this.content_class = content_class;
	}
	
	public void setContains_code(Boolean contains_code) {
		this.contains_code = contains_code;
	}

}
