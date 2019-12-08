/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.main.page;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.scava.plugin.mvc.controller.Controller;
import org.eclipse.scava.plugin.mvc.event.routed.RoutedEvent;

public class OpenFileInEditorRequestEvent extends RoutedEvent {
	private final IFile file;
	private final int selectionStart;
	private final int selectionEnd;
	private final boolean selection;

	public OpenFileInEditorRequestEvent(Controller source, ASTNode node) {
		super(source);

		ASTNode root = node.getRoot();
		IResource resource = ((CompilationUnit) root).getJavaElement().getResource();
		file = (IFile) resource;
		selectionStart = node.getStartPosition();
		selectionEnd = node.getLength();
		selection = true;
	}

	public OpenFileInEditorRequestEvent(Controller source, IFile file) {
		super(source);

		this.file = file;
		selectionStart = 0;
		selectionEnd = 0;
		selection = false;
	}

	public OpenFileInEditorRequestEvent(Controller source, IFile file, int selectionStart, int selectionEnd) {
		super(source);
		this.file = file;
		this.selectionStart = selectionStart;
		this.selectionEnd = selectionEnd;
		selection = true;
	}

	public IFile getFile() {
		return file;
	}

	public int getSelectionStart() {
		return selectionStart;
	}

	public int getSelectionEnd() {
		return selectionEnd;
	}

	public boolean isSelection() {
		return selection;
	}

}
