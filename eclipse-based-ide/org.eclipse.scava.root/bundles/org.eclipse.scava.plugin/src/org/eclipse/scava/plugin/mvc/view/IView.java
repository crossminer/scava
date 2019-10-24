/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.mvc.view;

import org.eclipse.scava.plugin.mvc.IHasEventListenerManager;
import org.eclipse.scava.plugin.mvc.IInitializable;
import org.eclipse.scava.plugin.mvc.IMvcComponent;
import org.eclipse.swt.widgets.Composite;

public interface IView<ViewEventListenerType extends IViewEventListener>
		extends IMvcComponent, IInitializable, IHasEventListenerManager<ViewEventListenerType> {
	Composite getComposite();
}
