package org.eclipse.scava.crossflow.runtime.utils;

import java.io.Serializable;

public class ControlSignal implements Serializable {

	private static final long serialVersionUID = 6410095687416313726L;

	public enum ControlSignals {
		TERMINATION, ACKNOWLEDGEMENT, WORKER_ADDED, WORKER_REMOVED
	}
	
	private ControlSignals signal;
	private String senderId;

	public ControlSignal(ControlSignals signal, String senderId) {
		setSignal(signal);
		setSenderId(senderId);
	}

	public ControlSignal() {
	}

	public ControlSignals getSignal() {
		return signal;
	}

	public void setSignal(ControlSignals signal) {
		this.signal = signal;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

}
