package org.eclipse.scava.crossflow.runtime;

import java.io.Serializable;

public class FailedJob implements Serializable {

	private static final long serialVersionUID = -8868868495593187850L;
	
	protected Job job;
	protected Exception exception;
	protected String worker;
	protected String task;

	public FailedJob(Job job, Exception exception, String worker, String task) {
		super();
		this.job = job;
		this.exception = exception;
		this.worker = worker;
		this.task = task;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public String getWorker() {
		return worker;
	}

	public void setWorker(String worker) {
		this.worker = worker;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public String toString() {
		return job + " | " + exception + " " + worker + " " + task;
	}

}
