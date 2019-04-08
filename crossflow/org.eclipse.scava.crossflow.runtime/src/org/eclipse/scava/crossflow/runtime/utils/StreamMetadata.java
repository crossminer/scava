package org.eclipse.scava.crossflow.runtime.utils;

import java.io.Serializable;

public class StreamMetadata  implements Serializable{
	private String name;
	private long size;
	private long inFlight;
	private boolean isTopic;
	private long numberOfSubscribers;

	public StreamMetadata(String name, long size, long inFlight, boolean isTopic, long numberOfSubscribers) {
		this.name = name;
		this.size = size;
		this.inFlight = inFlight;
		this.isTopic = isTopic;
		this.numberOfSubscribers = numberOfSubscribers;
	};

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public long getSize() {
		return size;
	}

	public long getInFlight() {
		return inFlight;
	}

	public boolean isTopic() {
		return isTopic;
	}

	public long getNumberOfSubscribers() {
		return numberOfSubscribers;
	}

	public void setNumberOfSubscribers(int numberOfSubscribers) {
		this.numberOfSubscribers = numberOfSubscribers;
	}

	@Override
	public String toString() {
		return "name: " + name + " | size: " + size + " | inFlight: " + inFlight + " | isTopic: " + isTopic
				+ " | numberOfSubscribers: " + numberOfSubscribers;
	}
}