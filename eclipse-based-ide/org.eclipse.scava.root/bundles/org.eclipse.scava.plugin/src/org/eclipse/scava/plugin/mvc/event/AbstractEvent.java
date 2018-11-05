package org.eclipse.scava.plugin.mvc.event;

import org.eclipse.scava.plugin.mvc.IMvcComponent;

public abstract class AbstractEvent<SenderComponentType extends IMvcComponent> {
	private final SenderComponentType sender;

	public AbstractEvent(SenderComponentType sender) {
		this.sender = sender;
	}

	public SenderComponentType getSender() {
		return sender;
	}

}
