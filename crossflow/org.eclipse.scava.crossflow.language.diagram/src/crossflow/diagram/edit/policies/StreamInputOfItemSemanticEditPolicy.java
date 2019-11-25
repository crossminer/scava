/*
* 
*/
package crossflow.diagram.edit.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyReferenceCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyReferenceRequest;

import crossflow.diagram.providers.CrossflowElementTypes;

/**
 * @generated
 */
public class StreamInputOfItemSemanticEditPolicy extends CrossflowBaseItemSemanticEditPolicy {

	/**
	* @generated
	*/
	public StreamInputOfItemSemanticEditPolicy() {
		super(CrossflowElementTypes.StreamInputOf_4005);
	}

	/**
	* @generated
	*/
	protected Command getDestroyReferenceCommand(DestroyReferenceRequest req) {
		return getGEFWrapper(new DestroyReferenceCommand(req));
	}

}
