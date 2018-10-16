package org.eclipse.scava.crossflow.examples.opinionated;

import com.beust.jcommander.JCommander;
import org.apache.activemq.broker.BrokerService;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.Cache;
import com.beust.jcommander.Parameter;

public class OpinionatedExample extends Workflow {
	
	public static void main(String[] args) throws Exception {
		OpinionatedExample app = new OpinionatedExample();
		new JCommander(app, args);
		app.run();
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
	
	// tasks
	protected WordSource wordSource;
	protected OccurencesMonitor occurencesMonitor;
	
	
	public OpinionatedExample() {
		this.name = "OpinionatedExample";
	}
	
	public void run() throws Exception {
	
		if (isMaster()) {
			cache = new Cache(this);
			BrokerService broker = new BrokerService();
			broker.setUseJmx(true);
			broker.addConnector(getBroker());
			broker.start();
		}
		
		words = new Words(this);
		
		wordSource = new WordSource();
		wordSource.setWorkflow(this);
		wordSource.setWords(words);
		
		occurencesMonitor = new OccurencesMonitor();
		occurencesMonitor.setWorkflow(this);
		words.addConsumer(occurencesMonitor);
		
		
		if (isMaster()){
			wordSource.produce();
		}
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