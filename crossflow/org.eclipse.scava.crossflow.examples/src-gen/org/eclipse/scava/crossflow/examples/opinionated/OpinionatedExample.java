package org.eclipse.scava.crossflow.examples.opinionated;

import java.util.LinkedList;
import java.util.Collection;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import org.apache.activemq.broker.BrokerService;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.Cache;
import org.eclipse.scava.crossflow.runtime.utils.TaskStatus;

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
	protected EclipseTaskStatusPublisher eclipseTaskStatusPublisher;
	
	// tasks
	protected WordSource wordSource;
	protected OccurencesMonitor occurencesMonitor;
	
	// excluded tasks from workers
	protected Collection<String> tasksToExclude = new LinkedList<String>();
	
	public void excludeTasks(Collection<String> tasks){
		tasksToExclude = tasks;
	}
	
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

		eclipseTaskStatusPublisher = new EclipseTaskStatusPublisher(this);
		
		words = new Words(this);
		
		if(isMaster() || !tasksToExclude.contains("WordSource")) {
		wordSource = new WordSource();
		wordSource.setWorkflow(this);
		wordSource.setWords(words);
		}
	
		if(isMaster() || !tasksToExclude.contains("OccurencesMonitor")) {
		occurencesMonitor = new OccurencesMonitor();
		occurencesMonitor.setWorkflow(this);
		
			words.addConsumer(occurencesMonitor);
			
	
		}
	
		
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
	
	public void setTaskInProgess(Object caller) {
		eclipseTaskStatusPublisher.send(new TaskStatus(TaskStatuses.INPROGRESS, caller.getClass().getName(), ""));
	}

	public void setTaskWaiting(Object caller) {
		eclipseTaskStatusPublisher.send(new TaskStatus(TaskStatuses.WAITING, caller.getClass().getName(), ""));
	}

	public void setTaskBlocked(Object caller, String reason) {
		eclipseTaskStatusPublisher.send(new TaskStatus(TaskStatuses.BLOCKED, caller.getClass().getName(), reason));
	}

	public void setTaskUnblocked(Object caller) {
		eclipseTaskStatusPublisher.send(new TaskStatus(TaskStatuses.INPROGRESS, caller.getClass().getName(), ""));
	}

}