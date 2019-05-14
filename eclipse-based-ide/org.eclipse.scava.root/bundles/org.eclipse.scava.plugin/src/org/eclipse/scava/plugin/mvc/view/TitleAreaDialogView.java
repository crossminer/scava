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

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.scava.plugin.mvc.event.direct.EventManager;
import org.eclipse.scava.plugin.mvc.event.direct.IEventListenerManager;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public abstract class TitleAreaDialogView<ViewEventListenerType extends IViewEventListener> extends TitleAreaDialog
		implements IView<ViewEventListenerType> {
	protected final EventManager<ViewEventListenerType> eventManager = new EventManager<>();
	private boolean disposed;
	private Composite composite;

	@Override
	public void init() {
		open();
	}

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public TitleAreaDialogView(Shell parentShell) {
		super(parentShell);
	}

	@Override
	public IEventListenerManager<ViewEventListenerType> getEventListenerManager() {
		return eventManager;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		composite = (Composite) super.createDialogArea(parent);
		return composite;
	}

	@Override
	public void dispose() {
		eventManager.dispose();

		disposed = true;

		close();
	}

	@Override
	public boolean close() {
		if (disposed || eventManager.isEmpty()) {
			return super.close();
		} else {
			eventManager.invoke(l -> l.requestViewClose());
			return false;
		}
	}

	@Override
	public Composite getComposite() {
		return composite;
	}

}
