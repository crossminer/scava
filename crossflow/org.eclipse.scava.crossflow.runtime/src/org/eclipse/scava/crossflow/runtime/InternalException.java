package org.eclipse.scava.crossflow.runtime;

import java.io.Serializable;

import org.eclipse.scava.crossflow.runtime.utils.ExceptionUtils;

public class InternalException implements Serializable {

	private static final long serialVersionUID = 3379426884982685293L;
	
	protected String reason;
	protected String stacktrace;
	protected String senderId;

	public InternalException() {
		;
	}
	
	public InternalException(String reason, String stacktrace, String senderId) {
		this.reason = reason;
		this.stacktrace = stacktrace;
		this.senderId = senderId;
	}

	public InternalException(Exception exception, String senderId) {
		this(exception.getMessage(), ExceptionUtils.getStackTrace(exception), senderId);
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

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

}
