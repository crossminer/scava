/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.main;

import org.eclipse.scava.plugin.mvc.controller.Controller;
import org.eclipse.scava.plugin.mvc.event.routed.RoutedEvent;

public abstract class EclipseInterfaceEvent extends RoutedEvent {

	public EclipseInterfaceEvent(Controller source) {
		super(source);
	}

	public static class ProjectSearchRequestEvent extends EclipseInterfaceEvent {

		public ProjectSearchRequestEvent(Controller source) {
			super(source);
		}

	}

	public static class LibrarySearchRequestEvent extends EclipseInterfaceEvent {

		public LibrarySearchRequestEvent(Controller source) {
			super(source);
		}

	}

	public static class CodeRecommendationRequestEvent extends EclipseInterfaceEvent {

		public CodeRecommendationRequestEvent(Controller source) {
			super(source);
		}

	}

	public static class ApiDocuemntationRequestEvent extends EclipseInterfaceEvent {

		public ApiDocuemntationRequestEvent(Controller source) {
			super(source);
		}

	}
}
