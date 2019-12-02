/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
*
* Contributors:
*    Zsolt János Szamosvölgyi
*    Endre Tamás Váradi
*    Gergõ Balogh
**********************************************************************/
package org.eclipse.scava.plugin.usermonitoring.event.events.resourceElement;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.scava.plugin.Activator;
import org.eclipse.scava.plugin.usermonitoring.event.IEventListener;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.texteditor.IElementStateListener;

public class ResourceElementEventListener implements IElementStateListener, IEventListener {

	IEditorInput input;
	ICompilationUnit compilationUnit;

	public ResourceElementEventListener(IEditorInput input, ICompilationUnit compilationUnit) {
		super();
		this.input = input;
		this.compilationUnit = compilationUnit;
	}

	@Override
	public void elementDirtyStateChanged(Object element, boolean isDirty) {
		if (!isDirty && input.equals(element)) {
			
			Activator.getDefault().getEventBus().post(new ResourceElementEvent(compilationUnit, ResourceElementStateType.SAVED));
		}

	}

	@Override
	public void elementDeleted(Object element) {
		if (input.equals(element)) {
			
			Activator.getDefault().getEventBus().post(new ResourceElementEvent(compilationUnit, ResourceElementStateType.DELETED));
		}
	}

	@Override
	public void elementContentAboutToBeReplaced(Object element) {

	}

	@Override
	public void elementContentReplaced(Object element) {

	}

	@Override
	public void elementMoved(Object originalElement, Object movedElement) {

	}

}
