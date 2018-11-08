/*
 * 
 */
package crossflow.diagram.navigator;

import org.eclipse.jface.viewers.ViewerSorter;

import crossflow.diagram.part.CrossflowVisualIDRegistry;

/**
 * @generated
 */
public class CrossflowNavigatorSorter extends ViewerSorter {

	/**
	 * @generated
	 */
	private static final int GROUP_CATEGORY = 7003;

	/**
	 * @generated
	 */
	private static final int SHORTCUTS_CATEGORY = 7002;

	/**
	 * @generated
	 */
	public int category(Object element) {
		if (element instanceof CrossflowNavigatorItem) {
			CrossflowNavigatorItem item = (CrossflowNavigatorItem) element;
			if (item.getView().getEAnnotation("Shortcut") != null) { //$NON-NLS-1$
				return SHORTCUTS_CATEGORY;
			}
			return CrossflowVisualIDRegistry.getVisualID(item.getView());
		}
		return GROUP_CATEGORY;
	}

}
