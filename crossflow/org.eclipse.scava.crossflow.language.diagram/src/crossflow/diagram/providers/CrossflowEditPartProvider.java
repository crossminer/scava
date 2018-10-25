/*
 * 
 */
package crossflow.diagram.providers;

import org.eclipse.gmf.tooling.runtime.providers.DefaultEditPartProvider;

import crossflow.diagram.edit.parts.CrossflowEditPartFactory;
import crossflow.diagram.edit.parts.WorkflowEditPart;
import crossflow.diagram.part.CrossflowVisualIDRegistry;

/**
 * @generated
 */
public class CrossflowEditPartProvider extends DefaultEditPartProvider {

	/**
	 * @generated
	 */
	public CrossflowEditPartProvider() {
		super(new CrossflowEditPartFactory(), CrossflowVisualIDRegistry.TYPED_INSTANCE, WorkflowEditPart.MODEL_ID);
	}

}
