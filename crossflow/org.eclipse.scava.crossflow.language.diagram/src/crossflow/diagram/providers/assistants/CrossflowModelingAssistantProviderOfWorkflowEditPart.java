/*
 * 
 */
package crossflow.diagram.providers.assistants;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;

import crossflow.diagram.providers.CrossflowElementTypes;
import crossflow.diagram.providers.CrossflowModelingAssistantProvider;

/**
 * @generated
 */
public class CrossflowModelingAssistantProviderOfWorkflowEditPart extends CrossflowModelingAssistantProvider {

	/**
	 * @generated
	 */
	@Override
	public List<IElementType> getTypesForPopupBar(IAdaptable host) {
		List<IElementType> types = new ArrayList<IElementType>(8);
		types.add(CrossflowElementTypes.Topic_2001);
		types.add(CrossflowElementTypes.Queue_2002);
		types.add(CrossflowElementTypes.Source_2003);
		types.add(CrossflowElementTypes.Sink_2004);
		types.add(CrossflowElementTypes.Configuration_2005);
		types.add(CrossflowElementTypes.Task_2006);
		types.add(CrossflowElementTypes.Type_2007);
		types.add(CrossflowElementTypes.Field_2008);
		return types;
	}

}
