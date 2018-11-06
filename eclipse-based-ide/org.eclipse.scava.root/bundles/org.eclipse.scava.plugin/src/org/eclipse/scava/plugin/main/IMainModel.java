/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.main;

import org.eclipse.core.resources.IProject;
import org.eclipse.scava.plugin.mvc.IModel;
import org.eclipse.swt.widgets.Shell;

import com.google.common.eventbus.EventBus;

public interface IMainModel extends IModel {
	Shell getShell();
	IProject getActiveProject();
	EventBus getEventBus();
}
