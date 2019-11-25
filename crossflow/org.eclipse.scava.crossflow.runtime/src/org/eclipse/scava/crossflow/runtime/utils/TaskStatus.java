package org.eclipse.scava.crossflow.runtime.utils;

import java.io.Serializable;

public class TaskStatus implements Serializable {

	private static final long serialVersionUID = -8237205597971014811L;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((caller == null) ? 0 : caller.hashCode());
		result = prime * result + ((reason == null) ? 0 : reason.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaskStatus other = (TaskStatus) obj;
		if (caller == null) {
			if (other.caller != null)
				return false;
		} else if (!caller.equals(other.caller))
			return false;
		if (reason == null) {
			if (other.reason != null)
				return false;
		} else if (!reason.equals(other.reason))
			return false;
		if (status != other.status)
			return false;
		return true;
	}
	
}
