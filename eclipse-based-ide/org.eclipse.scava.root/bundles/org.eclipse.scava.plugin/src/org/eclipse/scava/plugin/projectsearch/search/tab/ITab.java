/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.projectsearch.search.tab;

import org.eclipse.scava.plugin.mvc.view.IView;
import org.eclipse.scava.plugin.mvc.view.IViewEventListener;
import org.eclipse.swt.custom.CTabItem;

public interface ITab<ViewEventListenerType extends IViewEventListener> extends IView<ViewEventListenerType>{

	void setTabReference(CTabItem tab);

}