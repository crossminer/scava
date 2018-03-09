/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.ui.communicationdisplay;

import java.util.Iterator;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.scava.plugin.logger.LoggerMessageKind;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.services.IDisposable;

public class CommunicationDisplayDisplay extends Dialog implements IDisposable {
	
	private StyledText styledText;
	
	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public CommunicationDisplayDisplay(Shell parentShell) {
		super(parentShell);
		setShellStyle(SWT.DIALOG_TRIM | SWT.RESIZE);
		setBlockOnOpen(false);
		open();
	}
	
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Communication display");
	}
	
	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		styledText = new StyledText(container, SWT.V_SCROLL | SWT.BORDER | SWT.WRAP);
		styledText.setEditable(false);
		
		return container;
	}
	
	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
	}
	
	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 300);
	}
	
	private static final Color FROM_CLIENT_COLOR = new Color(null, 255, 0, 0);
	private static final Color FROM_SERVER_COLOR = new Color(null, 0, 0, 255);
	private static final Color JSON_COLOR = new Color(null, 0, 255, 0);
	private static final Color LIBRARY_COLOR = new Color(null, 255, 0, 255);
	
	private StyleRange getStyleRange(LoggerMessageKind kind) {
		StyleRange styleRange = new StyleRange();
		
		switch (kind) {
			case FROM_CLIENT:
				styleRange.foreground = FROM_CLIENT_COLOR;
				break;
			case FROM_SERVER:
				styleRange.foreground = FROM_SERVER_COLOR;
				break;
			case JSON:
				styleRange.foreground = JSON_COLOR;
				break;
			case LIBRARY:
				styleRange.foreground = LIBRARY_COLOR;
				break;
			default:
				break;
		}
		
		return styleRange;
	}
	
	public void refreshView(Iterator<CommunicationMessage> messages) {
		int topIndex = styledText.getTopIndex();
		styledText.setStyleRange(null);
		styledText.setText("");
		
		while (messages.hasNext()) {
			CommunicationMessage message = messages.next();
			
			StyleRange styleRange = getStyleRange(message.getKind());
			
			styleRange.start = styledText.getCharCount();
			styleRange.length = message.getMessage().length();
			
			styledText.append(message.getMessage() + "\r\n");
			styledText.setStyleRange(styleRange);
		}
		
		styledText.setTopIndex(topIndex);
	}
	
	@Override
	public boolean close() {
		return false;
	}
	
	@Override
	public void dispose() {
		super.close();
	}
}
