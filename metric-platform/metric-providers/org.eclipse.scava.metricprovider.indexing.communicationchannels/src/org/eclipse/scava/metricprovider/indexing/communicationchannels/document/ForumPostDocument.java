package org.eclipse.scava.metricprovider.indexing.communicationchannels.document;

import java.util.Date;

public class ForumPostDocument extends Document {

	String post_id;
	String forum_id;
	String topic_id;
	String forum_name;

	public ForumPostDocument(String uid, String projectName, String messageBody, String creator, Date createdAt,
			String forumID, String topicId, String articleId, String subject, String forum_name) {

		this.uid = uid;
		this.post_id = articleId;
		this.forum_id = forumID;
		this.project_name = projectName;
		this.message_body = messageBody;
		this.creator = creator;
		this.topic_id = topicId;
		this.created_at = createdAt;
		this.subject = subject;
		this.forum_name = forum_name;
		
	}

	public String getPost_id() {
		return post_id;
	}

	public String getForum_id() {
		return forum_id;
	}

	public String getTopic_id() {
		return topic_id;
	}

	public void setPost_id(String post_id) {
		this.post_id = post_id;
	}

	public void setForum_id(String forum_id) {
		this.forum_id = forum_id;
	}

	public void setTopic_id(String topic_id) {
		this.topic_id = topic_id;
	}

	public String getForum_name() {
		return forum_name;
	}

	public void setForum_name(String forum_name) {
		this.forum_name = forum_name;
	}

}
