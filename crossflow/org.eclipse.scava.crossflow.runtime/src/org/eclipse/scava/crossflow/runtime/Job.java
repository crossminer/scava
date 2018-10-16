package org.eclipse.scava.crossflow.runtime;

import java.io.Serializable;
import java.util.UUID;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class Job implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 666L;

	public Job() {
		this.id = UUID.randomUUID().toString();
	}
	
	protected String id;
	protected String correlationId;
	protected String destination;
	protected boolean cached = false;
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}
	
	public String getCorrelationId() {
		return correlationId;
	}
	
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	public String getDestination() {
		return destination;
	}
	
	public boolean isCached() {
		return cached;
	}
	
	public void setCached(boolean cached) {
		this.cached = cached;
	}
	
	public String getXML() {
		String id = this.id;
		String correlationId = this.correlationId;
		boolean cached = this.cached;
		
		this.id = null;
		this.correlationId = null;
		this.cached = false;
		
		String xml = new XStream(new DomDriver()).toXML(this);
		
		this.id = id;
		this.correlationId = correlationId;
		this.cached = cached;
		
		return xml;
	}
	
	public String getHash() {
		return UUID.nameUUIDFromBytes(getXML().getBytes()).toString();
	}
	
}
