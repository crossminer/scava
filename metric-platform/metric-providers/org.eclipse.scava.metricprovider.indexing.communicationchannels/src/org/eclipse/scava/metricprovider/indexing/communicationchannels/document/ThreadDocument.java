package org.eclipse.scava.metricprovider.indexing.communicationchannels.document;

public class ThreadDocument extends DocumentAbstract {

	private String communication_channel_id;
	private String project_name;
	private String subject;
	private int thread_id;
	
	public ThreadDocument(String uid, String projectName, String collectionName, int threadId, String subject) {
		this.uid=uid;
		this.project_name=projectName;
		this.communication_channel_id = collectionName;
		this.thread_id = threadId;
		this.subject=subject;
	}

	public String getCommunication_channel_id() {
		return communication_channel_id;
	}

	public String getProject_name() {
		return project_name;
	}

	public String getSubject() {
		return subject;
	}

	public int getThread_id() {
		return thread_id;
	}

}
