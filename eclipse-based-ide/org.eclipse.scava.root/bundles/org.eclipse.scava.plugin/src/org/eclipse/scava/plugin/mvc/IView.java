/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.mvc;

import org.eclipse.scava.plugin.mvc.event.AbstractCloseEvent;

public interface IView extends IMvcComponent, IDisposable, IUsesEventSystem {
	
	void init();
	
	static abstract class ViewCloseEvent<ViewType extends IView> extends AbstractCloseEvent<ViewType> {

		public ViewCloseEvent(ViewType view) {
			super(view);
		}

	}
}
