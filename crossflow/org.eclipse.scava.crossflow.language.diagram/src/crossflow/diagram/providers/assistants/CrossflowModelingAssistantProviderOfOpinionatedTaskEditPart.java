/*
 * 
 */
package crossflow.diagram.providers.assistants;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;

import crossflow.diagram.edit.parts.OpinionatedTaskEditPart;
import crossflow.diagram.edit.parts.QueueEditPart;
import crossflow.diagram.edit.parts.TopicEditPart;
import crossflow.diagram.providers.CrossflowElementTypes;
import crossflow.diagram.providers.CrossflowModelingAssistantProvider;

/**
 * @generated
 */
public class CrossflowModelingAssistantProviderOfOpinionatedTaskEditPart extends CrossflowModelingAssistantProvider {

	/**
	* @generated
	*/
	@Override

	public List<IElementType> getRelTypesOnSource(IAdaptable source) {
		IGraphicalEditPart sourceEditPart = (IGraphicalEditPart) source.getAdapter(IGraphicalEditPart.class);
		return doGetRelTypesOnSource((OpinionatedTaskEditPart) sourceEditPart);
	}

	/**
	* @generated
	*/
	public List<IElementType> doGetRelTypesOnSource(OpinionatedTaskEditPart source) {
		List<IElementType> types = new ArrayList<IElementType>(1);
		types.add(CrossflowElementTypes.TaskOutput_4003);
		return types;
	}

	/**
	* @generated
	*/
	@Override

	public List<IElementType> getRelTypesOnSourceAndTarget(IAdaptable source, IAdaptable target) {
		IGraphicalEditPart sourceEditPart = (IGraphicalEditPart) source.getAdapter(IGraphicalEditPart.class);
		IGraphicalEditPart targetEditPart = (IGraphicalEditPart) target.getAdapter(IGraphicalEditPart.class);
		return doGetRelTypesOnSourceAndTarget((OpinionatedTaskEditPart) sourceEditPart, targetEditPart);
	}

	/**
	* @generated
	*/
	public List<IElementType> doGetRelTypesOnSourceAndTarget(OpinionatedTaskEditPart source,
			IGraphicalEditPart targetEditPart) {
		List<IElementType> types = new LinkedList<IElementType>();
		if (targetEditPart instanceof TopicEditPart) {
			types.add(CrossflowElementTypes.TaskOutput_4003);
		}
		if (targetEditPart instanceof QueueEditPart) {
			types.add(CrossflowElementTypes.TaskOutput_4003);
		}
		return types;
	}

	/**
	* @generated
	*/
	@Override

	public List<IElementType> getTypesForTarget(IAdaptable source, IElementType relationshipType) {
		IGraphicalEditPart sourceEditPart = (IGraphicalEditPart) source.getAdapter(IGraphicalEditPart.class);
		return doGetTypesForTarget((OpinionatedTaskEditPart) sourceEditPart, relationshipType);
	}

	/**
	* @generated
	*/
	public List<IElementType> doGetTypesForTarget(OpinionatedTaskEditPart source, IElementType relationshipType) {
		List<IElementType> types = new ArrayList<IElementType>();
		if (relationshipType == CrossflowElementTypes.TaskOutput_4003) {
			types.add(CrossflowElementTypes.Topic_2003);
			types.add(CrossflowElementTypes.Queue_2004);
		}
		return types;
	}

	/**
	* @generated
	*/
	@Override

	public List<IElementType> getRelTypesOnTarget(IAdaptable target) {
		IGraphicalEditPart targetEditPart = (IGraphicalEditPart) target.getAdapter(IGraphicalEditPart.class);
		return doGetRelTypesOnTarget((OpinionatedTaskEditPart) targetEditPart);
	}

	/**
	* @generated
	*/
	public List<IElementType> doGetRelTypesOnTarget(OpinionatedTaskEditPart target) {
		List<IElementType> types = new ArrayList<IElementType>(1);
		types.add(CrossflowElementTypes.StreamInputOf_4005);
		return types;
	}

	/**
	* @generated
	*/
	@Override

	public List<IElementType> getTypesForSource(IAdaptable target, IElementType relationshipType) {
		IGraphicalEditPart targetEditPart = (IGraphicalEditPart) target.getAdapter(IGraphicalEditPart.class);
		return doGetTypesForSource((OpinionatedTaskEditPart) targetEditPart, relationshipType);
	}

	/**
	* @generated
	*/
	public List<IElementType> doGetTypesForSource(OpinionatedTaskEditPart target, IElementType relationshipType) {
		List<IElementType> types = new ArrayList<IElementType>();
		if (relationshipType == CrossflowElementTypes.StreamInputOf_4005) {
			types.add(CrossflowElementTypes.Topic_2003);
			types.add(CrossflowElementTypes.Queue_2004);
		}
		return types;
	}

}
