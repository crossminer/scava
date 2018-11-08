/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.mvc.implementation;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.scava.plugin.mvc.IMvcComponent;
import org.eclipse.scava.plugin.mvc.event.AbstractCloseEvent;

import com.google.common.eventbus.EventBus;

public class CloseEventRaiser {
	private final Object from;

	public CloseEventRaiser(Object from) {
		this.from = from;
	}

	public void raiseCloseEvent(EventBus eventBus) {
		Class<?>[] interfaces = from.getClass().getInterfaces();
		
		for (Class<?> implementedInterface : interfaces) {
			if (IMvcComponent.class.isAssignableFrom(implementedInterface)) {
				Class<?>[] declaredClasses = implementedInterface.getDeclaredClasses();
				
				for (Class<?> declaredClass : declaredClasses) {
					if (AbstractCloseEvent.class.isAssignableFrom(declaredClass)) {
						try {
							Object event = declaredClass.getConstructor(implementedInterface)
									.newInstance(implementedInterface.cast(from));
							eventBus.post(event);
							return;
						} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
								| InvocationTargetException | NoSuchMethodException | SecurityException e) {
							e.printStackTrace();
						}
					}
				}
				
				System.err.println("Class " + from.getClass() + " does not have a nested class inherited from " + AbstractCloseEvent.class);
			}
		}
		
		System.err.println("Could not raise CloseEvent for " + from);
	}
}