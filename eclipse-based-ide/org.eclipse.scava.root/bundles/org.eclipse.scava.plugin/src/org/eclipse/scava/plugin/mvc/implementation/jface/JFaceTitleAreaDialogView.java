/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.mvc.implementation.jface;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.scava.plugin.mvc.IView;
import org.eclipse.scava.plugin.mvc.implementation.CloseEventRaiser;
import org.eclipse.swt.widgets.Shell;

import com.google.common.eventbus.EventBus;

public abstract class JFaceTitleAreaDialogView extends TitleAreaDialog implements IView {
	private EventBus eventBus;
	private boolean disposed;

	public JFaceTitleAreaDialogView(Shell parentShell) {
		super(parentShell);
	}

	@Override
	public void init() {
		open();
	}

	@Override
	public void setEventBus(EventBus eventBus) {
		this.eventBus = eventBus;
	}

	protected EventBus getEventBus() {
		return eventBus;
	}

	@Override
	public void dispose() {
		disposed = true;
		close();
	}

	@Override
	public boolean close() {
		if (disposed) {
			return super.close();
		} else {
			new CloseEventRaiser(this).raiseCloseEvent(getEventBus());
			return false;
		}
	}

}
