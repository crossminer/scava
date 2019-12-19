package org.eclipse.scava.crossflow.runtime.utils;

import java.io.Serializable;

public class ControlSignal implements Serializable {

	private static final long serialVersionUID = 6410095687416313726L;

	public enum ControlSignals {
		TERMINATION, ACKNOWLEDGEMENT, WORKER_ADDED, WORKER_REMOVED, CANCEL_JOB
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((senderId == null) ? 0 : senderId.hashCode());
		result = prime * result + ((signal == null) ? 0 : signal.hashCode());
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
		ControlSignal other = (ControlSignal) obj;
		if (senderId == null) {
			if (other.senderId != null)
				return false;
		} else if (!senderId.equals(other.senderId))
			return false;
		if (signal != other.signal)
			return false;
		return true;
	}
	
}
