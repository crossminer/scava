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

import crossflow.diagram.edit.commands.CommitmentTaskCreateCommand;
import crossflow.diagram.edit.commands.CsvSinkCreateCommand;
import crossflow.diagram.edit.commands.CsvSourceCreateCommand;
import crossflow.diagram.edit.commands.FieldCreateCommand;
import crossflow.diagram.edit.commands.LanguageCreateCommand;
import crossflow.diagram.edit.commands.OpinionatedTaskCreateCommand;
import crossflow.diagram.edit.commands.QueueCreateCommand;
import crossflow.diagram.edit.commands.ScriptedTaskCreateCommand;
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
		if (CrossflowElementTypes.CsvSource_2001 == req.getElementType()) {
			return getGEFWrapper(new CsvSourceCreateCommand(req));
		}
		if (CrossflowElementTypes.CsvSink_2002 == req.getElementType()) {
			return getGEFWrapper(new CsvSinkCreateCommand(req));
		}
		if (CrossflowElementTypes.Topic_2003 == req.getElementType()) {
			return getGEFWrapper(new TopicCreateCommand(req));
		}
		if (CrossflowElementTypes.Queue_2004 == req.getElementType()) {
			return getGEFWrapper(new QueueCreateCommand(req));
		}
		if (CrossflowElementTypes.Source_2005 == req.getElementType()) {
			return getGEFWrapper(new SourceCreateCommand(req));
		}
		if (CrossflowElementTypes.Sink_2006 == req.getElementType()) {
			return getGEFWrapper(new SinkCreateCommand(req));
		}
		if (CrossflowElementTypes.CommitmentTask_2007 == req.getElementType()) {
			return getGEFWrapper(new CommitmentTaskCreateCommand(req));
		}
		if (CrossflowElementTypes.OpinionatedTask_2008 == req.getElementType()) {
			return getGEFWrapper(new OpinionatedTaskCreateCommand(req));
		}
		if (CrossflowElementTypes.ScriptedTask_2015 == req.getElementType()) {
			return getGEFWrapper(new ScriptedTaskCreateCommand(req));
		}
		if (CrossflowElementTypes.Task_2010 == req.getElementType()) {
			return getGEFWrapper(new TaskCreateCommand(req));
		}
		if (CrossflowElementTypes.Type_2011 == req.getElementType()) {
			return getGEFWrapper(new TypeCreateCommand(req));
		}
		if (CrossflowElementTypes.Field_2014 == req.getElementType()) {
			return getGEFWrapper(new FieldCreateCommand(req));
		}
		if (CrossflowElementTypes.Language_2013 == req.getElementType()) {
			return getGEFWrapper(new LanguageCreateCommand(req));
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
