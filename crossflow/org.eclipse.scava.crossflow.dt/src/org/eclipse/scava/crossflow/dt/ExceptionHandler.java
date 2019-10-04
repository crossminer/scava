package org.eclipse.scava.crossflow.dt;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

class ExceptionHandler {

	static void handle(Exception e, Shell shell) {
		String trace = e.getClass().getName() + "\n";
		for (StackTraceElement t : e.getStackTrace())
			trace = trace + t + "\n";
		MessageDialog.openError(shell, "Error", "Message: " + e.getMessage() + "\n" + trace);
	}

}
