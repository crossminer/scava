/*******************************************************************************
 * Copyright (c) 2017 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Konstantinos Barmpis - initial API and implementation
 ******************************************************************************/
package org.eclipse.scava.crossflow.execution.subscription;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.NotificationImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramGraphicalViewer;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.parts.DiagramDocumentEditor;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated NOT
 */
public class WorkflowGraphicalChangeSubscription {

	private static WorkflowGraphicalChangeSubscription singleton = null;

	private Set<DiagramDocumentEditor> subscribers = new HashSet<DiagramDocumentEditor>();

	private Map<EObject, ShapeNodeEditPart> elementCache = new HashMap<>();

	public static WorkflowGraphicalChangeSubscription getinstance() {

		if (singleton == null)
			singleton = new WorkflowGraphicalChangeSubscription();

		return singleton;

	}

	protected WorkflowGraphicalChangeSubscription() {
	}

	public boolean subscribeToGraphicalChanges(DiagramDocumentEditor e) {
		return subscribers.add(e);
	}

	public void elementInProgress(EObject e) {

		for (DiagramDocumentEditor d : subscribers) {
			try {
				Diagram diag = d.getDiagram();
				URI diaguri = diag.getElement().eResource().getURI();
				URI elementuri = e.eResource().getURI();

				if (diaguri.equals(elementuri)) {

					ShapeNodeEditPart v = getVisibleElementFromCache(d, e);
					// v.makepretty
					v.notifyChanged(new NotificationImpl(Notification.EVENT_TYPE_COUNT, "ignored", "running"));

				}
			} catch (Exception ex) {
				// ignore as the editor may be initializing and throw npe
			}
		}
	}

	public void elementBlocked(EObject e) {

		for (DiagramDocumentEditor d : subscribers) {
			try {
				Diagram diag = d.getDiagram();
				URI diaguri = diag.getElement().eResource().getURI();
				URI elementuri = e.eResource().getURI();

				if (diaguri.equals(elementuri)) {

					ShapeNodeEditPart v = getVisibleElementFromCache(d, e);
					// v.makepretty
					v.notifyChanged(new NotificationImpl(Notification.EVENT_TYPE_COUNT, "ignored", "busy"));

				}
			} catch (Exception ex) {
				// ignore as the editor may be initializing and throw npe
			}
		}

	}

	public void elementComplete(EObject e) {

		for (DiagramDocumentEditor d : subscribers) {
			try {
				Diagram diag = d.getDiagram();
				URI diaguri = diag.getElement().eResource().getURI();
				URI elementuri = e.eResource().getURI();

				if (diaguri.equals(elementuri)) {

					ShapeNodeEditPart v = getVisibleElementFromCache(d, e);
					// v.makepretty
					v.notifyChanged(new NotificationImpl(Notification.EVENT_TYPE_COUNT, "ignored", "empty"));

				}
			} catch (Exception ex) {
				// ignore as the editor may be initializing and throw npe
			}
		}

	}

	public void executionStarted() {
		for (DiagramDocumentEditor d : subscribers)
			d.showBusy(true);

	}

	public void executionEnded() {
		for (final DiagramDocumentEditor d : subscribers) {
			d.showBusy(false);

			Job j = new Job("saving diagram resource") {

				@Override
				protected IStatus run(IProgressMonitor monitor) {
					d.doSave(monitor);
					return new Status(IStatus.OK, "org.eclipse.scava.crossflow.diagram", "save successful");
				}
			};
			j.schedule();
		}
	}

	public void cancelSubscription(DiagramDocumentEditor wde) {
		subscribers.remove(wde);
	}

	private ShapeNodeEditPart getVisibleElementFromCache(DiagramDocumentEditor d, EObject e) {

		ShapeNodeEditPart ret = elementCache.get(e);

		if (ret == null) {

			IDiagramGraphicalViewer viewer = (IDiagramGraphicalViewer) d.getDiagramEditPart().getViewer();
			Collection<EditPart> editParts;
			Diagram diag = d.getDiagram();

			for (Iterator<?> it = diag.eAllContents(); it.hasNext();) {

				Object next = it.next();

				if (next instanceof View) {

					View n = (View) next;
					EObject viewElement = n.getElement();

					if (viewElement != null && viewElement.eResource().getURIFragment(viewElement)
							.equals(e.eResource().getURIFragment(e))) {

						Object reg = viewer.getEditPartRegistry().get(n);

						if (reg instanceof Iterable)
							editParts = (Collection<EditPart>) reg;
						else {
							editParts = new LinkedList<EditPart>();
							if (reg != null)
								editParts.add((EditPart) reg);
						}
						for (EditPart ep : editParts) {
							if (ep instanceof ShapeNodeEditPart) {
								ShapeNodeEditPart shape = (ShapeNodeEditPart) ep;
								elementCache.put(e, shape);
								return shape;
							}
						}
					}

				}
			}

		}

		return ret;

	}

}
