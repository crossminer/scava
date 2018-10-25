package org.eclipse.scava.crossflow.runtime.utils;

import java.io.Serializable;

import org.eclipse.scava.crossflow.runtime.Workflow.TaskStatuses;

public class TaskStatus implements Serializable{

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
	
}
