/*
 * 
 */
package crossflow.diagram.providers;

import crossflow.diagram.part.CrossflowDiagramEditorPlugin;

/**
 * @generated
 */
public class ElementInitializers {

	protected ElementInitializers() {
		// use #getInstance to access cached instance
	}

	/**
	 * @generated
	 */
	public static ElementInitializers getInstance() {
		ElementInitializers cached = CrossflowDiagramEditorPlugin.getInstance().getElementInitializers();
		if (cached == null) {
			CrossflowDiagramEditorPlugin.getInstance().setElementInitializers(cached = new ElementInitializers());
		}
		return cached;
	}
}
