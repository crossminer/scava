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
package org.eclipse.scava.plugin.usermonitoring.event.events.classpath;

import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IElementChangedListener;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.scava.plugin.Activator;

public class ClasspathChangeListener implements IElementChangedListener {

	@Override
	public void elementChanged(ElementChangedEvent event) {

		visit(event.getDelta());

	}

	private void visit(IJavaElementDelta delta) {
		
		IJavaElement el = delta.getElement();
		
		
		
		switch (el.getElementType()) {
		case IJavaElement.JAVA_MODEL:
			visitChildren(delta);
			break;
		case IJavaElement.JAVA_PROJECT:
			if (isClasspathChanged(delta.getFlags())) {

				IJavaElementDelta[] affectedChildren = delta.getAffectedChildren();

				for (IJavaElementDelta iJavaElementDelta : affectedChildren) {

					if (iJavaElementDelta.getKind() == IJavaElementDelta.CHANGED || iJavaElementDelta.getKind() == IJavaElementDelta.ADDED) {

						int flags = iJavaElementDelta.getFlags();
						if (isRemove(flags)) {
							
							IJavaProject project = (IJavaProject)el;
							Activator.getDefault().getEventBus()
									.post(new ClasspathChangeEvent(iJavaElementDelta.getElement().getElementName(), ClasspathChangeEventType.DELETED, project));

						}
						IJavaProject project = (IJavaProject)el;
						if (isAdd(flags)) {
							Activator.getDefault().getEventBus()
									.post(new ClasspathChangeEvent(iJavaElementDelta.getElement().getElementName(), ClasspathChangeEventType.ADDED, project));
						}

					}

				}
			}
			break;
		default:
			break;
		}
	}

	private boolean isClasspathChanged(int flags) {
		return 0 != (flags & (IJavaElementDelta.F_CLASSPATH_CHANGED | IJavaElementDelta.F_RESOLVED_CLASSPATH_CHANGED));

	}

	private boolean isRemove(int flags) {
		return 0 != (flags & IJavaElementDelta.F_REMOVED_FROM_CLASSPATH);

	}

	private boolean isAdd(int flags) {
		return 0 != (flags & (IJavaElementDelta.F_ADDED_TO_CLASSPATH));

	}

	public void visitChildren(IJavaElementDelta delta) {
		for (IJavaElementDelta c : delta.getAffectedChildren()) {
			visit(c);
		}
	}

}
