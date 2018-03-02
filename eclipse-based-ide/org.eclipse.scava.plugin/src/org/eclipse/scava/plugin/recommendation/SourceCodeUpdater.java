/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.recommendation;

import java.io.FileNotFoundException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.scava.commons.recommendation.source.SourceReplaceRecommendation;
import org.eclipse.scava.plugin.logger.Logger;
import org.eclipse.scava.plugin.logger.LoggerMessageKind;
import org.eclipse.scava.plugin.utils.Utils;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.editors.text.TextFileDocumentProvider;
import org.eclipse.ui.texteditor.IDocumentProvider;

public class SourceCodeUpdater {
	public static void apply(SourceReplaceRecommendation recommendation, IProject project) throws SourceCodeUpdaterException
	{
		try {
			IFile file = Utils.openFileInProject(project, recommendation.getTargetFile());
			applyReplaceInFile(recommendation, file);
		} catch (CoreException e) {
			throw new SourceCodeUpdaterException("CoreException in SourceCodeUpdater: " + e.getMessage(), e);
		} catch (FileNotFoundException e) {
			throw new SourceCodeUpdaterException(e);
		} catch (BadLocationException e) {
			throw new SourceCodeUpdaterException("Bad text location in recommendation " + recommendation + " :" + e.getMessage(), e);
		}
	}

	private static void applyReplaceInFile(SourceReplaceRecommendation recommendation, IFile file) throws CoreException, PartInitException, BadLocationException {
		IDocument document = getDocumentForFile(file);
		
		int offset = recommendation.getStartChar();
		int length = recommendation.getEndChar() - offset + 1;
		
		Logger.log(" Replace: " + recommendation.getStartChar() + " to " + recommendation.getEndChar() + " -> " + recommendation.getNewCode() + "  in: " + recommendation.getTargetFile(), LoggerMessageKind.FROM_CLIENT);
		
		document.replace(offset, length, recommendation.getNewCode());
	}

	private static IDocument getDocumentForFile(IFile file) throws CoreException {
		IDocumentProvider documentProvider = new TextFileDocumentProvider();
		documentProvider.connect(file);
		IDocument document = documentProvider.getDocument(file);
		return document;
	}
}
