package org.eclipse.scava.metricprovider.indexing.communicationchannels.document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArticleDocument extends DocumentAbstract {

	
	private Long aritcle_number;
	private String collection_name;
	private String message_thread_id;
	private String project_name;
	private String message_body;
	private String subject;
	private String creator;
	private Date created_at;
	
	// NLP
	private List<String> emotional_dimension = new ArrayList<>();
	private String sentiment;
	private String plain_text;
	private String request_reply_classification;
	private String content_class;
	private Boolean contains_code;
	

	public ArticleDocument(String uid, String projectName, String collectionName, String messageThreadID,
			long articleNumber,String subject, String messageBody, String creator, Date createdAt) {

		this.uid = uid;
		this.aritcle_number = articleNumber; 
		this.project_name = projectName;
		this.message_body = messageBody;
		this.creator = creator;
		this.created_at = createdAt;
		this.collection_name = collectionName;
		this.subject = subject;
		this.message_thread_id = messageThreadID;
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

	public long getAritcle_number() {
		return aritcle_number;
	}

	public String getCollection_name() {
		return collection_name;
	}

	public String getMessage_thread_id() {
		return message_thread_id;
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

	public void setAritcle_number(long aritcle_number) {
		this.aritcle_number = aritcle_number;
	}

	public void setCollection_name(String newsgroup_name) {
		this.collection_name = newsgroup_name;
	}

	public void setMessage_thread_id(String message_thread_id) {
		this.message_thread_id = message_thread_id;
	}
	
	public void addEmotional_dimension(String emotional_dimension) {
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
