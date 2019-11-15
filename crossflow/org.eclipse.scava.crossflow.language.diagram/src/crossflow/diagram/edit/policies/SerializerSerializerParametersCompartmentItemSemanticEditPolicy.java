/*
* 
*/
package crossflow.diagram.edit.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;

import crossflow.diagram.edit.commands.Parameter2CreateCommand;
import crossflow.diagram.providers.CrossflowElementTypes;

/**
 * @generated
 */
public class SerializerSerializerParametersCompartmentItemSemanticEditPolicy
		extends CrossflowBaseItemSemanticEditPolicy {

	/**
	* @generated
	*/
	public SerializerSerializerParametersCompartmentItemSemanticEditPolicy() {
		super(CrossflowElementTypes.Serializer_2018);
	}

	/**
	* @generated
	*/
	protected Command getCreateCommand(CreateElementRequest req) {
		if (CrossflowElementTypes.Parameter_3005 == req.getElementType()) {
			return getGEFWrapper(new Parameter2CreateCommand(req));
		}
		return super.getCreateCommand(req);
	}

}
