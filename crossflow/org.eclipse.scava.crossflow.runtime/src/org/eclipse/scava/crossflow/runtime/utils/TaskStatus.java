package org.eclipse.scava.crossflow.runtime.utils;

import java.io.Serializable;

public class TaskStatus implements Serializable{
	
	public enum TaskStatuses {
		STARTED, WAITING, INPROGRESS, BLOCKED, FINISHED
	};
	
	private TaskStatuses status;
	private String caller;
	private String reason;
	
	public TaskStatus(TaskStatuses status, String caller, String reason){
		this.setStatus(status);
		this.setCaller(caller);
		this.setReason(reason);
	}

	public TaskStatuses getStatus() {
		return status;
	}

	private void setStatus(TaskStatuses status) {
		this.status = status;
	}

	public String getCaller() {
		return caller;
	}

	private void setCaller(String caller) {
		this.caller = caller;
	}

	public String getReason() {
		return reason;
	}

	private void setReason(String reason) {
		this.reason = reason;
	}
	
	@Override
	public String toString() {
		return status+" | caller: "+caller+" reason: "+reason;
	}
	
}
