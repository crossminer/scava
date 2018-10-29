package org.eclipse.scava.plugin.eclipse.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.scava.plugin.Activator;
import org.eclipse.scava.plugin.main.IMainController;

public class ApiDocumentationHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Activator.getDefault().getMainController().getEventBus().post(new IMainController.RequestApiDocumentation());
		return null;
	}

}
