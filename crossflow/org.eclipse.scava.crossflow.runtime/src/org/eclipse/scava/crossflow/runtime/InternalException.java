package org.eclipse.scava.crossflow.runtime;

import java.io.Serializable;

public class InternalException implements Serializable {
	
	protected Exception exception;
	protected String worker;
	
	public InternalException(Exception exception, String worker) {
		super();
		this.exception = exception;
		this.worker = worker;
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
	
}
