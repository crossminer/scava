/*
* 
*/
package crossflow.diagram.edit.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;

import crossflow.diagram.edit.commands.DataField2CreateCommand;
import crossflow.diagram.edit.commands.EnumField2CreateCommand;
import crossflow.diagram.providers.CrossflowElementTypes;

/**
 * @generated
 */
public class ScriptedTaskScriptedTaskOutputVariablesCompartmentItemSemanticEditPolicy
		extends CrossflowBaseItemSemanticEditPolicy {

	/**
	* @generated
	*/
	public ScriptedTaskScriptedTaskOutputVariablesCompartmentItemSemanticEditPolicy() {
		super(CrossflowElementTypes.ScriptedTask_2015);
	}

	/**
	* @generated
	*/
	protected Command getCreateCommand(CreateElementRequest req) {
		if (CrossflowElementTypes.DataField_3006 == req.getElementType()) {
			return getGEFWrapper(new DataField2CreateCommand(req));
		}
		if (CrossflowElementTypes.EnumField_3007 == req.getElementType()) {
			return getGEFWrapper(new EnumField2CreateCommand(req));
		}
		return super.getCreateCommand(req);
	}

}
