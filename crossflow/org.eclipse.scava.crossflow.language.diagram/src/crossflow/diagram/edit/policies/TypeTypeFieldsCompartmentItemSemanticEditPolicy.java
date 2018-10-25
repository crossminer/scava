/*
* 
*/
package crossflow.diagram.edit.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;

import crossflow.diagram.edit.commands.Field2CreateCommand;
import crossflow.diagram.providers.CrossflowElementTypes;

/**
 * @generated
 */
public class TypeTypeFieldsCompartmentItemSemanticEditPolicy extends CrossflowBaseItemSemanticEditPolicy {

	/**
	* @generated
	*/
	public TypeTypeFieldsCompartmentItemSemanticEditPolicy() {
		super(CrossflowElementTypes.Type_2007);
	}

	/**
	* @generated
	*/
	protected Command getCreateCommand(CreateElementRequest req) {
		if (CrossflowElementTypes.Field_3001 == req.getElementType()) {
			return getGEFWrapper(new Field2CreateCommand(req));
		}
		return super.getCreateCommand(req);
	}

}
