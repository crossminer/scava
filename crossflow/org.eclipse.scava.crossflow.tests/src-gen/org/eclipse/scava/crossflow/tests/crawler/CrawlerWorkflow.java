package org.eclipse.scava.crossflow.tests.crawler;

import java.util.LinkedList;
import java.util.Collection;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import org.apache.activemq.broker.BrokerService;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.Cache;
import org.eclipse.scava.crossflow.runtime.DirectoryCache;
import org.eclipse.scava.crossflow.runtime.Mode;
import org.eclipse.scava.crossflow.runtime.Moded;
import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.utils.TaskStatus;

public class CrawlerWorkflow extends Workflow {
	
	public static CrawlerWorkflow run(String[] args) throws Exception {
		Moded moded = new Moded();
		new JCommander(moded, args);
		CrawlerWorkflow app = new CrawlerWorkflow(moded.getMode());
		new JCommander(app, args);
		app.run();
		return app;
	}
	
	public static void main(String[] args) throws Exception {
		run(args);
	}
	
	
	// streams
	protected Urls urls;
	protected UrlsToAnalyse urlsToAnalyse;
	
	private boolean createBroker = true;
	
	// tasks
	protected UrlSource urlSource;
	protected UrlCollector urlCollector;
	protected UrlAnalyser urlAnalyser;
	
	public CrawlerWorkflow() {
		this(Mode.MASTER);
	}
	
	public CrawlerWorkflow(Mode mode) {
		super();
		this.name = "CrawlerWorkflow";
		this.mode = mode;
		if (isMaster()) {
		urlSource = new UrlSource();
		urlSource.setWorkflow(this);
		urlCollector = new UrlCollector();
		urlCollector.setWorkflow(this);
		}
		
		if (isWorker()) {
			if (!tasksToExclude.contains("UrlAnalyser")) {
				urlAnalyser = new UrlAnalyser();
				urlAnalyser.setWorkflow(this);
			}
		}
	}
	
	public void createBroker(boolean createBroker) {
		this.createBroker = createBroker;
	}
	
	/**
	 * Run with initial delay in ms before starting execution (after creating broker
	 * if master)
	 * 
	 * @param delay
	 */
	@Override
	public void run(int delay) throws Exception {
	
		new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					
					if (isMaster()) {
						if (createBroker) {
							brokerService = new BrokerService();
							brokerService.setUseJmx(true);
							brokerService.addConnector(getBroker());
							brokerService.start();
						}
					}

					connect();

					Thread.sleep(delay);
					
					urls = new Urls(CrawlerWorkflow.this);
					activeQueues.add(urls);
					urlsToAnalyse = new UrlsToAnalyse(CrawlerWorkflow.this);
					activeQueues.add(urlsToAnalyse);
					
					if (isMaster()) {
							urlSource.setResultsBroadcaster(resultsBroadcaster);
							urlSource.setUrls(urls);
							urlCollector.setResultsBroadcaster(resultsBroadcaster);
							urls.addConsumer(urlCollector, "UrlCollector");			
							urlCollector.setUrlsToAnalyse(urlsToAnalyse);
					}
					
					if (isWorker()) {
						if (!tasksToExclude.contains("UrlAnalyser")) {
								urlAnalyser.setResultsBroadcaster(resultsBroadcaster);
								urlsToAnalyse.addConsumer(urlAnalyser, "UrlAnalyser");			
								urlAnalyser.setUrls(urls);
						}
					}
					
					
					if (isMaster()){
						urlSource.produce();
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		
		}).start();
	
	}				
	
	public Urls getUrls() {
		return urls;
	}
	public UrlsToAnalyse getUrlsToAnalyse() {
		return urlsToAnalyse;
	}
	
	public UrlSource getUrlSource() {
		return urlSource;
	}
	public UrlCollector getUrlCollector() {
		return urlCollector;
	}
	public UrlAnalyser getUrlAnalyser() {
		return urlAnalyser;
	}

}

