package org.eclipse.scava.crossflow.tests.crawler;

import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.Workflow;

public abstract class UrlSourceBase extends Task {
		
	protected CrawlerWorkflow workflow;
	
	public void setWorkflow(CrawlerWorkflow workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	public String getId(){
		return "UrlSource:"+workflow.getName();
	}
	
	protected Urls urls;
	
	protected void setUrls(Urls urls) {
		this.urls = urls;
	}
	
	private Urls getUrls() {
		return urls;
	}
	
	public void sendToUrls(Url url) {
		getUrls().send(url, this.getClass().getName());
	}
	
	
	
	
	public abstract void produce();
	
}