package org.eclipse.scava.crossflow.tests.crawler;

import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.permanentqueues.*;

public abstract class UrlCollectorBase extends Task  implements UrlsConsumer{
		
	protected CrawlerWorkflow workflow;
	
	public void setWorkflow(CrawlerWorkflow workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	public String getId(){
		return "UrlCollector:"+workflow.getName();
	}
	
	protected UrlsToAnalyse urlsToAnalyse;
	
	protected void setUrlsToAnalyse(UrlsToAnalyse urlsToAnalyse) {
		this.urlsToAnalyse = urlsToAnalyse;
	}
	
	private UrlsToAnalyse getUrlsToAnalyse() {
		return urlsToAnalyse;
	}
	
	public void sendToUrlsToAnalyse(Url url) {
		getUrlsToAnalyse().send(url, this.getClass().getName());
	}
	
	
	@Override
	public final void consumeUrlsWithNotifications(Url url) {
		workflow.setTaskInProgess(this);
		consumeUrls(url);
		workflow.setTaskWaiting(this);
	}
	
	public abstract void consumeUrls(Url url);
	
	
	
}