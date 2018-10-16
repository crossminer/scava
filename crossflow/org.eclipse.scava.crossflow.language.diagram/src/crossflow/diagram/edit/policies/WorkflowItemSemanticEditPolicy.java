/*
 * 
 */
package crossflow.diagram.edit.policies;

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.commands.core.commands.DuplicateEObjectsCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DuplicateElementsRequest;

import crossflow.diagram.edit.commands.ConfigurationCreateCommand;
import crossflow.diagram.edit.commands.FieldCreateCommand;
import crossflow.diagram.edit.commands.QueueCreateCommand;
import crossflow.diagram.edit.commands.SinkCreateCommand;
import crossflow.diagram.edit.commands.SourceCreateCommand;
import crossflow.diagram.edit.commands.TaskCreateCommand;
import crossflow.diagram.edit.commands.TopicCreateCommand;
import crossflow.diagram.edit.commands.TypeCreateCommand;
import crossflow.diagram.providers.CrossflowElementTypes;

/**
 * @generated
 */
public class WorkflowItemSemanticEditPolicy extends CrossflowBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	public WorkflowItemSemanticEditPolicy() {
		super(CrossflowElementTypes.Workflow_1000);
	}

	/**
	 * @generated
	 */
	protected Command getCreateCommand(CreateElementRequest req) {
		if (CrossflowElementTypes.Topic_2001 == req.getElementType()) {
			return getGEFWrapper(new TopicCreateCommand(req));
		}
		if (CrossflowElementTypes.Queue_2002 == req.getElementType()) {
			return getGEFWrapper(new QueueCreateCommand(req));
		}
		if (CrossflowElementTypes.Source_2003 == req.getElementType()) {
			return getGEFWrapper(new SourceCreateCommand(req));
		}
		if (CrossflowElementTypes.Sink_2004 == req.getElementType()) {
			return getGEFWrapper(new SinkCreateCommand(req));
		}
		if (CrossflowElementTypes.Configuration_2005 == req.getElementType()) {
			return getGEFWrapper(new ConfigurationCreateCommand(req));
		}
		if (CrossflowElementTypes.Task_2006 == req.getElementType()) {
			return getGEFWrapper(new TaskCreateCommand(req));
		}
		if (CrossflowElementTypes.Type_2007 == req.getElementType()) {
			return getGEFWrapper(new TypeCreateCommand(req));
		}
		if (CrossflowElementTypes.Field_2008 == req.getElementType()) {
			return getGEFWrapper(new FieldCreateCommand(req));
		}
		return super.getCreateCommand(req);
	}

	/**
	 * @generated
	 */
	protected Command getDuplicateCommand(DuplicateElementsRequest req) {
		TransactionalEditingDomain editingDomain = ((IGraphicalEditPart) getHost()).getEditingDomain();
		return getGEFWrapper(new DuplicateAnythingCommand(editingDomain, req));
	}

	/**
	 * @generated
	 */
	private static class DuplicateAnythingCommand extends DuplicateEObjectsCommand {

		/**
		 * @generated
		 */
		public DuplicateAnythingCommand(TransactionalEditingDomain editingDomain, DuplicateElementsRequest req) {
			super(editingDomain, req.getLabel(), req.getElementsToBeDuplicated(), req.getAllDuplicatedElementsMap());
		}

	}

}
