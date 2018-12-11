package org.eclipse.scava.crossflow.tests.crawler;

import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.permanentqueues.*;

public abstract class UrlAnalyserBase extends Task  implements UrlsToAnalyseConsumer{
		
	protected CrawlerWorkflow workflow;
	
	public void setWorkflow(CrawlerWorkflow workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	public String getId(){
		return "UrlAnalyser:"+workflow.getName();
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
	
	
	@Override
	public final void consumeUrlsToAnalyseWithNotifications(Url url) {
		workflow.setTaskInProgess(this);
		consumeUrlsToAnalyse(url);
		workflow.setTaskWaiting(this);
	}
	
	public abstract void consumeUrlsToAnalyse(Url url);
	
	
	
}