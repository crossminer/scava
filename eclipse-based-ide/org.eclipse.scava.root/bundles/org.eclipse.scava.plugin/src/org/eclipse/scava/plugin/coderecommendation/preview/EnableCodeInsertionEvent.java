/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.coderecommendation.preview;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.scava.plugin.mvc.controller.Controller;
import org.eclipse.scava.plugin.mvc.event.routed.RoutedEvent;

public class EnableCodeInsertionEvent extends RoutedEvent {
	private final IDocument document;
	private final ITextSelection selection;

	public EnableCodeInsertionEvent(Controller source, IDocument document, ITextSelection selection) {
		super(source);
		this.document = document;
		this.selection = selection;
	}

	public IDocument getDocument() {
		return document;
	}

	public ITextSelection getSelection() {
		return selection;
	}

}
