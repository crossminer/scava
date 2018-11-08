package org.eclipse.scava.crossflow.runtime;

import com.beust.jcommander.Parameter;

public class Workflow {
	
	@Parameter(names = { "-name"}, description = "The name of the workflow")
	protected String name;
	protected Cache cache;
	@Parameter(names = { "-mode"}, description = "Must be master or worker", converter=ModeConverter.class)
	protected Mode mode = Mode.MASTER;
	@Parameter(names = { "-master"}, description = "IP of the master")
	protected String master = "localhost";
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Cache getCache() {
		return cache;
	}
	
	public void setCache(Cache cache) {
		this.cache = cache;
	}
	
	public boolean isMaster() {
		return mode == Mode.MASTER;
	}
	
	public String getBroker() {
		return "tcp://" + master + ":61616";
	}
	
	public void setMode(Mode mode) {
		this.mode = mode;
	}
	
	public Mode getMode() {
		return mode;
	}
	
	public enum TaskStatuses{STARTED,WAITING,INPROGRESS,BLOCKED,FINISHED};
	
}
