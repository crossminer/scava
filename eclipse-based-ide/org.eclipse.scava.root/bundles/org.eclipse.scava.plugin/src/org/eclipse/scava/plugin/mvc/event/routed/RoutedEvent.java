/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.mvc.event.routed;

import org.eclipse.scava.plugin.mvc.controller.Controller;

public class RoutedEvent implements IRoutedEvent {

	private final Controller source;

	public RoutedEvent(Controller source) {
		super();
		this.source = source;
	}

	@Override
	public Controller getSource() {
		return source;
	}

}
