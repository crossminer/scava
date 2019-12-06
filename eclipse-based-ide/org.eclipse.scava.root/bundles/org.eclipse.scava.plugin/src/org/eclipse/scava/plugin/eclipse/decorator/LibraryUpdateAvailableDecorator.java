package org.eclipse.scava.plugin.eclipse.decorator;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.swt.widgets.Display;

public class LibraryUpdateAvailableDecorator extends LabelProvider implements ILightweightLabelDecorator {

	private final Set<IProject> updatesAvailableFor = new HashSet<>();

	@Override
	public void decorate(Object element, IDecoration decoration) {

		boolean areUpdatesAvailable = updatesAvailableFor.contains(element);

		if (areUpdatesAvailable) {
			decoration.addSuffix(" [new lib version available]");
		}
	}

	public void setState(IProject project, boolean areUpdatesAvailable) {
		if (areUpdatesAvailable) {
			updatesAvailableFor.add(project);
		} else {
			updatesAvailableFor.remove(project);
		}

		Display.getDefault().asyncExec(() -> {
			fireLabelProviderChanged(new LabelProviderChangedEvent(this, project));
		});
	}

	public void clear() {
		Display.getDefault().asyncExec(() -> {
			Set<IProject> copy = new HashSet<IProject>(updatesAvailableFor);
			updatesAvailableFor.clear();
			copy.forEach(p -> fireLabelProviderChanged(new LabelProviderChangedEvent(this, p)));
		});
	}

}
