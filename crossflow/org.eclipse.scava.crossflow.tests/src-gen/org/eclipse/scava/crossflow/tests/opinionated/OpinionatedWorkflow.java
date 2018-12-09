package org.eclipse.scava.crossflow.tests.opinionated;

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
import org.eclipse.scava.crossflow.runtime.permanentqueues.*;



public class OpinionatedWorkflow extends Workflow {
	
	public static OpinionatedWorkflow run(String[] args) throws Exception {
		Moded moded = new Moded();
		new JCommander(moded, args);
		OpinionatedWorkflow app = new OpinionatedWorkflow(moded.getMode());
		new JCommander(app, args);
		app.run();
		return app;
	}
	
	public static void main(String[] args) throws Exception {
		run(args);
	}
	
	@Parameter(names = { "-favouriteWord"}, description = "favouriteWord workflow parameter")
	protected String favouriteWord;
	
	public void setFavouriteWord(String favouriteWord) {
		this.favouriteWord = favouriteWord;
	}
	
	public String getFavouriteWord() {
		return favouriteWord;
	}
	
	// streams
	protected Words words;
	
	private boolean createBroker = true;
	
	// tasks
	protected WordSource wordSource;
	protected OccurencesMonitor occurencesMonitor;
	
	public OpinionatedWorkflow() {
		this(Mode.MASTER);
	}
	
	public OpinionatedWorkflow(Mode mode) {
		super();
		this.name = "OpinionatedWorkflow";
		this.mode = mode;
		if (isMaster()) {
		wordSource = new WordSource();
		wordSource.setWorkflow(this);
		}
		
		if (isWorker()) {
			if (!tasksToExclude.contains("OccurencesMonitor")) {
				occurencesMonitor = new OccurencesMonitor();
				occurencesMonitor.setWorkflow(this);
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
					
					words = new Words(OpinionatedWorkflow.this);
					activeQueues.add(words);
					
					if (isMaster()) {
							wordSource.setResultsBroadcaster(resultsBroadcaster);
							wordSource.setWords(words);
					}
					
					if (isWorker()) {
						if (!tasksToExclude.contains("OccurencesMonitor")) {
								occurencesMonitor.setResultsBroadcaster(resultsBroadcaster);
								words.addConsumer(occurencesMonitor, "OccurencesMonitor");			
						}
					}
					
					
					if (isMaster()){
						wordSource.produce();
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		
		}).start();
	
	}				
	
	public Words getWords() {
		return words;
	}
	
	public WordSource getWordSource() {
		return wordSource;
	}
	public OccurencesMonitor getOccurencesMonitor() {
		return occurencesMonitor;
	}

}

