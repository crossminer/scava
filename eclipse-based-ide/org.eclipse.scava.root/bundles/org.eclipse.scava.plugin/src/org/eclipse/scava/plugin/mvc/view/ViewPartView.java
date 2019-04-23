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

import org.eclipse.scava.plugin.mvc.event.direct.EventManager;
import org.eclipse.scava.plugin.mvc.event.direct.IEventListenerManager;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

public abstract class ViewPartView<ViewEventListenerType extends IViewEventListener> extends ViewPart
		implements IView<ViewEventListenerType> {
	protected final EventManager<ViewEventListenerType> eventManager = new EventManager<>();
	private boolean disposed;
	private Composite composite;

	protected ViewPartView() {

	}

	/**
	 * Create contents of the view part.
	 * 
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		composite = parent;
	}

	@Override
	public void init() {

	}

	@Override
	public IEventListenerManager<ViewEventListenerType> getEventListenerManager() {
		return eventManager;
	}

	@Override
	public void dispose() {
		if (!disposed) {
			disposed = true;
			eventManager.invoke(l -> l.requestViewClose());
		} else {
			super.dispose();
			eventManager.dispose();
		}
	}

	@SuppressWarnings("unchecked")
	public static <ViewType> ViewType open(String viewID) throws PartInitException {
		return (ViewType) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(viewID);
	}

	@SuppressWarnings("unchecked")
	public static <ViewType> ViewType open(String viewID, String secondaryId) throws PartInitException {
		return (ViewType) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(viewID,
				secondaryId, ViewOpenMode.Active.getModeId());
	}

	@SuppressWarnings("unchecked")
	public static <ViewType> ViewType open(String viewID, String secondaryId, ViewOpenMode mode)
			throws PartInitException {
		return (ViewType) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(viewID,
				secondaryId, mode.getModeId());
	}

	@Override
	public Composite getComposite() {
		return composite;
	}

	public enum ViewOpenMode {
		Active(IWorkbenchPage.VIEW_ACTIVATE), Visible(IWorkbenchPage.VIEW_VISIBLE), Create(IWorkbenchPage.VIEW_CREATE);

		private ViewOpenMode(int modeId) {
			this.modeId = modeId;
		}

		private int modeId;

		public int getModeId() {
			return modeId;
		}
	}
}
