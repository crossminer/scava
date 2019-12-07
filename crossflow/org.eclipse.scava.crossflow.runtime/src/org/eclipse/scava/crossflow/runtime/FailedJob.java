package org.eclipse.scava.crossflow.runtime;

import java.io.Serializable;

import org.eclipse.scava.crossflow.runtime.utils.ExceptionUtils;

public class FailedJob implements Serializable {

	private static final long serialVersionUID = -8868868495593187850L;

	protected Job job;
	protected String reason;
	protected String stacktrace;
	protected String task;
	protected String workflow;
	
	public FailedJob() {
		;
	}
	
	public FailedJob(Job job, Exception exception, Task task) {
		this.job = job;
		this.reason = exception.getMessage();
		this.stacktrace = ExceptionUtils.getStackTrace(exception);
		this.task = task.getName();
		this.workflow = task.getWorkflow().getName();
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getStacktrace() {
		return stacktrace;
	}

	public void setStacktrace(String stacktrace) {
		this.stacktrace = stacktrace;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public String getWorkflow() {
		return workflow;
	}

	public void setWorkflow(String workflow) {
		this.workflow = workflow;
	}

}
