/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.treelements;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.highlight.DetectionHighlightData;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.highlight.DetectionHighlightDefinition;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.swt.widgets.Display;

public class DetectionOccurenceTreeElement extends TreeElement {
	private final IMarker marker;
	private int lineNumber;
	private ASTNode astNode;
	private static final Styler styler;

	static {
		styler = new Styler() {
			private final Font font = FontDescriptor.createFrom(new FontData()).setHeight(9).setStyle(SWT.NONE)
					.createFont(Display.getCurrent());

			@Override
			public void applyStyles(TextStyle textStyle) {
				textStyle.foreground = Display.getDefault().getSystemColor(SWT.COLOR_DARK_GRAY);
				textStyle.font = font;
			}

		};
	}

	public DetectionOccurenceTreeElement(IMarker marker) {
		super();
		this.marker = marker;

		try {
			DetectionHighlightData detectionHighlightData = DetectionHighlightDefinition
					.getDetectionHighlightData(marker);
			astNode = detectionHighlightData.getAstNode();
			CompilationUnit compilationUnit = (CompilationUnit) astNode.getRoot();
			int startPos = astNode.getStartPosition();
			lineNumber = compilationUnit.getLineNumber(startPos);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	public IMarker getMarker() {
		return marker;
	}

	@Override
	public int compareTo(TreeElement o) {
		if (o instanceof DetectionOccurenceTreeElement) {
			DetectionOccurenceTreeElement other = (DetectionOccurenceTreeElement) o;
			int result;
			if ((result = Integer.compare(lineNumber, other.lineNumber)) == 0) {
				return astNode.toString().compareTo(other.toString());
			} else {
				return result;
			}
		}

		return super.compareTo(o);
	}

	@Override
	public StyledString getStyledString() {
		StyledString styledString = new StyledString();
		styledString.append(lineNumber + ": ", styler);
		styledString.append(astNode.toString());
		return styledString;
	}

	@Override
	protected void setParent(TreeElement element) {
		super.setParent(element);

		if (element == null) {
			try {
				marker.delete();
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
	}
}
