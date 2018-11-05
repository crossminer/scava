package org.eclipse.scava.plugin.usermonitoring.metric.term;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.scava.plugin.usermonitoring.database.VertexType;
import org.eclipse.scava.plugin.usermonitoring.event.element.ResourceElementStateType;

public enum Milestone implements ITerm {

	SESSION(VertexType.ECLIPSE_CLOSE_EVENT, 15), LAST_SESSION(VertexType.ECLIPSE_CLOSE_EVENT, 1), CODING_SESSION(VertexType.LAUNCH_EVENT, 15), LAST_CODING_SESSION(VertexType.LAUNCH_EVENT, 1),

	ENGAGEMENT(VertexType.ELEMENT_EVENT, 15, new Property("Type", ResourceElementStateType.SAVED.toString())), LAST_ENGAGEMENT(VertexType.ELEMENT_EVENT, 1,
			new Property("Type", ResourceElementStateType.SAVED.toString()));

	private final String event;
	private final int count;
	private final Map<String, String> properties;

	Milestone(String event, int count, Property... properties) {
		this.event = event;
		this.count = count;
		this.properties = Arrays.stream(properties).collect(Collectors.toMap(p -> p.key, p -> p.value));

	}

	public String getMilestone() {
		return event;
	}

	@Override
	public int getCount() {
		return count;
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	private static class Property {
		public final String key;
		public final String value;

		public Property(String key, String value) {
			super();
			this.key = key;
			this.value = value;
		}

	}

}
