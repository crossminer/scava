package org.eclipse.scava.plugin.newcommunicationdemo;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.scava.plugin.Activator;

public class SearchArtifactMenuActionHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ArtifactSearchView artifactSearchView = new ArtifactSearchView(Activator.getDefault().getActiveShell());
		artifactSearchView.open();
		return null;
	}

}
