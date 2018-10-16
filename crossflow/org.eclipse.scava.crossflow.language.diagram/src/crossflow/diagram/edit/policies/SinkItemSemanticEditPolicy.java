/*
* 
*/
package crossflow.diagram.edit.policies;

import java.util.Iterator;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.core.commands.DeleteCommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyReferenceCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyReferenceRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientReferenceRelationshipRequest;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;

import crossflow.diagram.edit.commands.TaskInputCreateCommand;
import crossflow.diagram.edit.commands.TaskInputReorientCommand;
import crossflow.diagram.edit.commands.TaskOutputCreateCommand;
import crossflow.diagram.edit.commands.TaskOutputReorientCommand;
import crossflow.diagram.edit.parts.TaskInputEditPart;
import crossflow.diagram.edit.parts.TaskOutputEditPart;
import crossflow.diagram.part.CrossflowVisualIDRegistry;
import crossflow.diagram.providers.CrossflowElementTypes;

/**
 * @generated
 */
public class SinkItemSemanticEditPolicy extends CrossflowBaseItemSemanticEditPolicy {

	/**
	* @generated
	*/
	public SinkItemSemanticEditPolicy() {
		super(CrossflowElementTypes.Sink_2004);
	}

	/**
	* @generated
	*/
	protected Command getDestroyElementCommand(DestroyElementRequest req) {
		View view = (View) getHost().getModel();
		CompositeTransactionalCommand cmd = new CompositeTransactionalCommand(getEditingDomain(), null);
		cmd.setTransactionNestingEnabled(false);
		for (Iterator<?> it = view.getSourceEdges().iterator(); it.hasNext();) {
			Edge outgoingLink = (Edge) it.next();
			if (CrossflowVisualIDRegistry.getVisualID(outgoingLink) == TaskInputEditPart.VISUAL_ID) {
				DestroyReferenceRequest r = new DestroyReferenceRequest(outgoingLink.getSource().getElement(), null,
						outgoingLink.getTarget().getElement(), false);
				cmd.add(new DestroyReferenceCommand(r));
				cmd.add(new DeleteCommand(getEditingDomain(), outgoingLink));
				continue;
			}
			if (CrossflowVisualIDRegistry.getVisualID(outgoingLink) == TaskOutputEditPart.VISUAL_ID) {
				DestroyReferenceRequest r = new DestroyReferenceRequest(outgoingLink.getSource().getElement(), null,
						outgoingLink.getTarget().getElement(), false);
				cmd.add(new DestroyReferenceCommand(r));
				cmd.add(new DeleteCommand(getEditingDomain(), outgoingLink));
				continue;
			}
		}
		EAnnotation annotation = view.getEAnnotation("Shortcut"); //$NON-NLS-1$
		if (annotation == null) {
			// there are indirectly referenced children, need extra commands: false
			addDestroyShortcutsCommand(cmd, view);
			// delete host element
			cmd.add(new DestroyElementCommand(req));
		} else {
			cmd.add(new DeleteCommand(getEditingDomain(), view));
		}
		return getGEFWrapper(cmd.reduce());
	}

	/**
	 * @generated
	 */
	protected Command getCreateRelationshipCommand(CreateRelationshipRequest req) {
		Command command = req.getTarget() == null ? getStartCreateRelationshipCommand(req)
				: getCompleteCreateRelationshipCommand(req);
		return command != null ? command : super.getCreateRelationshipCommand(req);
	}

	/**
	 * @generated
	 */
	protected Command getStartCreateRelationshipCommand(CreateRelationshipRequest req) {
		if (CrossflowElementTypes.TaskInput_4002 == req.getElementType()) {
			return getGEFWrapper(new TaskInputCreateCommand(req, req.getSource(), req.getTarget()));
		}
		if (CrossflowElementTypes.TaskOutput_4003 == req.getElementType()) {
			return getGEFWrapper(new TaskOutputCreateCommand(req, req.getSource(), req.getTarget()));
		}
		return null;
	}

	/**
	 * @generated
	 */
	protected Command getCompleteCreateRelationshipCommand(CreateRelationshipRequest req) {
		if (CrossflowElementTypes.TaskInput_4002 == req.getElementType()) {
			return null;
		}
		if (CrossflowElementTypes.TaskOutput_4003 == req.getElementType()) {
			return null;
		}
		return null;
	}

	/**
	 * Returns command to reorient EReference based link. New link target or source
	 * should be the domain model element associated with this node.
	 * 
	 * @generated
	 */
	protected Command getReorientReferenceRelationshipCommand(ReorientReferenceRelationshipRequest req) {
		switch (getVisualID(req)) {
		case TaskInputEditPart.VISUAL_ID:
			return getGEFWrapper(new TaskInputReorientCommand(req));
		case TaskOutputEditPart.VISUAL_ID:
			return getGEFWrapper(new TaskOutputReorientCommand(req));
		}
		return super.getReorientReferenceRelationshipCommand(req);
	}

}
