package org.eclipse.scava.crossflow.runtime.utils;

import java.io.Serializable;

import org.eclipse.scava.crossflow.runtime.Workflow.ControlReasons;

public class ControlMessage implements Serializable{

	private ControlReasons reason;
	private String callerId;
	
	public ControlMessage(ControlReasons reason,String id){
		this.setCallerId(id);
		this.setControlReason(reason);
	}

	public String getCallerId() {
		return callerId;
	}

	private void setCallerId(String callerId) {
		this.callerId = callerId;
	}

	public ControlReasons getControlReason() {
		return reason;
	}

	private void setControlReason(ControlReasons reason) {
		this.reason = reason;
	}
	
}
