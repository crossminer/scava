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
		List<IElementType> types = new ArrayList<IElementType>(15);
		types.add(CrossflowElementTypes.CsvSource_2001);
		types.add(CrossflowElementTypes.CsvSink_2002);
		types.add(CrossflowElementTypes.Topic_2003);
		types.add(CrossflowElementTypes.Queue_2004);
		types.add(CrossflowElementTypes.Source_2005);
		types.add(CrossflowElementTypes.Sink_2006);
		types.add(CrossflowElementTypes.CommitmentTask_2007);
		types.add(CrossflowElementTypes.OpinionatedTask_2008);
		types.add(CrossflowElementTypes.ScriptedTask_2015);
		types.add(CrossflowElementTypes.ReusableComponent_2017);
		types.add(CrossflowElementTypes.Task_2010);
		types.add(CrossflowElementTypes.Type_2011);
		types.add(CrossflowElementTypes.Field_2014);
		types.add(CrossflowElementTypes.Language_2013);
		types.add(CrossflowElementTypes.Serializer_2018);
		return types;
	}

}
