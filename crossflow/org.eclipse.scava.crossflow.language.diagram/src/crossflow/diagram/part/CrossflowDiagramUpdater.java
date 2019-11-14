/*
* 
*/
package crossflow.diagram.part;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.tooling.runtime.update.DiagramUpdater;

import crossflow.CommitmentTask;
import crossflow.CrossflowPackage;
import crossflow.CsvSink;
import crossflow.CsvSource;
import crossflow.Field;
import crossflow.Language;
import crossflow.OpinionatedTask;
import crossflow.Parameter;
import crossflow.Queue;
import crossflow.ReusableComponent;
import crossflow.ScriptedTask;
import crossflow.Serialiser;
import crossflow.Sink;
import crossflow.Source;
import crossflow.Stream;
import crossflow.Task;
import crossflow.Topic;
import crossflow.Type;
import crossflow.Workflow;
import crossflow.diagram.edit.parts.CommitmentTaskEditPart;
import crossflow.diagram.edit.parts.CsvSinkEditPart;
import crossflow.diagram.edit.parts.CsvSourceEditPart;
import crossflow.diagram.edit.parts.Field2EditPart;
import crossflow.diagram.edit.parts.Field3EditPart;
import crossflow.diagram.edit.parts.FieldEditPart;
import crossflow.diagram.edit.parts.LanguageEditPart;
import crossflow.diagram.edit.parts.LanguageLanguageParametersCompartmentEditPart;
import crossflow.diagram.edit.parts.OpinionatedTaskEditPart;
import crossflow.diagram.edit.parts.Parameter2EditPart;
import crossflow.diagram.edit.parts.ParameterEditPart;
import crossflow.diagram.edit.parts.QueueEditPart;
import crossflow.diagram.edit.parts.ReusableComponentEditPart;
import crossflow.diagram.edit.parts.ScriptedTaskEditPart;
import crossflow.diagram.edit.parts.ScriptedTaskScriptedTaskOutputVariablesCompartmentEditPart;
import crossflow.diagram.edit.parts.SerialiserEditPart;
import crossflow.diagram.edit.parts.SerialiserSerialiserParametersCompartmentEditPart;
import crossflow.diagram.edit.parts.SinkEditPart;
import crossflow.diagram.edit.parts.SourceEditPart;
import crossflow.diagram.edit.parts.StreamInputOfEditPart;
import crossflow.diagram.edit.parts.StreamTypeEditPart;
import crossflow.diagram.edit.parts.TaskEditPart;
import crossflow.diagram.edit.parts.TaskOutputEditPart;
import crossflow.diagram.edit.parts.TopicEditPart;
import crossflow.diagram.edit.parts.TypeEditPart;
import crossflow.diagram.edit.parts.TypeExtendingEditPart;
import crossflow.diagram.edit.parts.TypeTypeFieldsCompartmentEditPart;
import crossflow.diagram.edit.parts.WorkflowEditPart;
import crossflow.diagram.providers.CrossflowElementTypes;

/**
 * @generated
 */
public class CrossflowDiagramUpdater {

	/**
	* @generated
	*/
	public static boolean isShortcutOrphaned(View view) {
		return !view.isSetElement() || view.getElement() == null || view.getElement().eIsProxy();
	}

	/**
	* @generated
	*/
	public static List<CrossflowNodeDescriptor> getSemanticChildren(View view) {
		switch (CrossflowVisualIDRegistry.getVisualID(view)) {
		case WorkflowEditPart.VISUAL_ID:
			return getWorkflow_1000SemanticChildren(view);
		case ScriptedTaskScriptedTaskOutputVariablesCompartmentEditPart.VISUAL_ID:
			return getScriptedTaskScriptedTaskOutputVariablesCompartment_7003SemanticChildren(view);
		case TypeTypeFieldsCompartmentEditPart.VISUAL_ID:
			return getTypeTypeFieldsCompartment_7001SemanticChildren(view);
		case LanguageLanguageParametersCompartmentEditPart.VISUAL_ID:
			return getLanguageLanguageParametersCompartment_7002SemanticChildren(view);
		case SerialiserSerialiserParametersCompartmentEditPart.VISUAL_ID:
			return getSerialiserSerialiserParametersCompartment_7004SemanticChildren(view);
		}
		return Collections.emptyList();
	}

	/**
	* @generated
	*/
	public static List<CrossflowNodeDescriptor> getWorkflow_1000SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		Workflow modelElement = (Workflow) view.getElement();
		LinkedList<CrossflowNodeDescriptor> result = new LinkedList<CrossflowNodeDescriptor>();
		for (Iterator<?> it = modelElement.getTasks().iterator(); it.hasNext();) {
			Task childElement = (Task) it.next();
			int visualID = CrossflowVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == CsvSourceEditPart.VISUAL_ID) {
				result.add(new CrossflowNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == CsvSinkEditPart.VISUAL_ID) {
				result.add(new CrossflowNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == SourceEditPart.VISUAL_ID) {
				result.add(new CrossflowNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == SinkEditPart.VISUAL_ID) {
				result.add(new CrossflowNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == CommitmentTaskEditPart.VISUAL_ID) {
				result.add(new CrossflowNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == OpinionatedTaskEditPart.VISUAL_ID) {
				result.add(new CrossflowNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == ScriptedTaskEditPart.VISUAL_ID) {
				result.add(new CrossflowNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == ReusableComponentEditPart.VISUAL_ID) {
				result.add(new CrossflowNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == TaskEditPart.VISUAL_ID) {
				result.add(new CrossflowNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getStreams().iterator(); it.hasNext();) {
			Stream childElement = (Stream) it.next();
			int visualID = CrossflowVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == TopicEditPart.VISUAL_ID) {
				result.add(new CrossflowNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == QueueEditPart.VISUAL_ID) {
				result.add(new CrossflowNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getTypes().iterator(); it.hasNext();) {
			Type childElement = (Type) it.next();
			int visualID = CrossflowVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == TypeEditPart.VISUAL_ID) {
				result.add(new CrossflowNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getParameters().iterator(); it.hasNext();) {
			Field childElement = (Field) it.next();
			int visualID = CrossflowVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == FieldEditPart.VISUAL_ID) {
				result.add(new CrossflowNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getLanguages().iterator(); it.hasNext();) {
			Language childElement = (Language) it.next();
			int visualID = CrossflowVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == LanguageEditPart.VISUAL_ID) {
				result.add(new CrossflowNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		{
			Serialiser childElement = modelElement.getSerialiser();
			int visualID = CrossflowVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == SerialiserEditPart.VISUAL_ID) {
				result.add(new CrossflowNodeDescriptor(childElement, visualID));
			}
		}
		return result;
	}

	/**
	* @generated
	*/
	public static List<CrossflowNodeDescriptor> getScriptedTaskScriptedTaskOutputVariablesCompartment_7003SemanticChildren(
			View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		ScriptedTask modelElement = (ScriptedTask) containerView.getElement();
		LinkedList<CrossflowNodeDescriptor> result = new LinkedList<CrossflowNodeDescriptor>();
		for (Iterator<?> it = modelElement.getOutputVariables().iterator(); it.hasNext();) {
			Field childElement = (Field) it.next();
			int visualID = CrossflowVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == Field2EditPart.VISUAL_ID) {
				result.add(new CrossflowNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	* @generated
	*/
	public static List<CrossflowNodeDescriptor> getTypeTypeFieldsCompartment_7001SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Type modelElement = (Type) containerView.getElement();
		LinkedList<CrossflowNodeDescriptor> result = new LinkedList<CrossflowNodeDescriptor>();
		for (Iterator<?> it = modelElement.getFields().iterator(); it.hasNext();) {
			Field childElement = (Field) it.next();
			int visualID = CrossflowVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == Field3EditPart.VISUAL_ID) {
				result.add(new CrossflowNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	* @generated
	*/
	public static List<CrossflowNodeDescriptor> getLanguageLanguageParametersCompartment_7002SemanticChildren(
			View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Language modelElement = (Language) containerView.getElement();
		LinkedList<CrossflowNodeDescriptor> result = new LinkedList<CrossflowNodeDescriptor>();
		for (Iterator<?> it = modelElement.getParameters().iterator(); it.hasNext();) {
			Parameter childElement = (Parameter) it.next();
			int visualID = CrossflowVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == ParameterEditPart.VISUAL_ID) {
				result.add(new CrossflowNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	* @generated
	*/
	public static List<CrossflowNodeDescriptor> getSerialiserSerialiserParametersCompartment_7004SemanticChildren(
			View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Serialiser modelElement = (Serialiser) containerView.getElement();
		LinkedList<CrossflowNodeDescriptor> result = new LinkedList<CrossflowNodeDescriptor>();
		for (Iterator<?> it = modelElement.getParameters().iterator(); it.hasNext();) {
			Parameter childElement = (Parameter) it.next();
			int visualID = CrossflowVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == Parameter2EditPart.VISUAL_ID) {
				result.add(new CrossflowNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	* @generated
	*/
	public static List<CrossflowLinkDescriptor> getContainedLinks(View view) {
		switch (CrossflowVisualIDRegistry.getVisualID(view)) {
		case WorkflowEditPart.VISUAL_ID:
			return getWorkflow_1000ContainedLinks(view);
		case CsvSourceEditPart.VISUAL_ID:
			return getCsvSource_2001ContainedLinks(view);
		case CsvSinkEditPart.VISUAL_ID:
			return getCsvSink_2002ContainedLinks(view);
		case TopicEditPart.VISUAL_ID:
			return getTopic_2003ContainedLinks(view);
		case QueueEditPart.VISUAL_ID:
			return getQueue_2004ContainedLinks(view);
		case SourceEditPart.VISUAL_ID:
			return getSource_2005ContainedLinks(view);
		case SinkEditPart.VISUAL_ID:
			return getSink_2006ContainedLinks(view);
		case CommitmentTaskEditPart.VISUAL_ID:
			return getCommitmentTask_2007ContainedLinks(view);
		case OpinionatedTaskEditPart.VISUAL_ID:
			return getOpinionatedTask_2008ContainedLinks(view);
		case ScriptedTaskEditPart.VISUAL_ID:
			return getScriptedTask_2015ContainedLinks(view);
		case ReusableComponentEditPart.VISUAL_ID:
			return getReusableComponent_2017ContainedLinks(view);
		case TaskEditPart.VISUAL_ID:
			return getTask_2010ContainedLinks(view);
		case TypeEditPart.VISUAL_ID:
			return getType_2011ContainedLinks(view);
		case FieldEditPart.VISUAL_ID:
			return getField_2014ContainedLinks(view);
		case LanguageEditPart.VISUAL_ID:
			return getLanguage_2013ContainedLinks(view);
		case SerialiserEditPart.VISUAL_ID:
			return getSerialiser_2016ContainedLinks(view);
		case Field2EditPart.VISUAL_ID:
			return getField_3003ContainedLinks(view);
		case Field3EditPart.VISUAL_ID:
			return getField_3001ContainedLinks(view);
		case ParameterEditPart.VISUAL_ID:
			return getParameter_3002ContainedLinks(view);
		case Parameter2EditPart.VISUAL_ID:
			return getParameter_3004ContainedLinks(view);
		}
		return Collections.emptyList();
	}

	/**
	* @generated
	*/
	public static List<CrossflowLinkDescriptor> getIncomingLinks(View view) {
		switch (CrossflowVisualIDRegistry.getVisualID(view)) {
		case CsvSourceEditPart.VISUAL_ID:
			return getCsvSource_2001IncomingLinks(view);
		case CsvSinkEditPart.VISUAL_ID:
			return getCsvSink_2002IncomingLinks(view);
		case TopicEditPart.VISUAL_ID:
			return getTopic_2003IncomingLinks(view);
		case QueueEditPart.VISUAL_ID:
			return getQueue_2004IncomingLinks(view);
		case SourceEditPart.VISUAL_ID:
			return getSource_2005IncomingLinks(view);
		case SinkEditPart.VISUAL_ID:
			return getSink_2006IncomingLinks(view);
		case CommitmentTaskEditPart.VISUAL_ID:
			return getCommitmentTask_2007IncomingLinks(view);
		case OpinionatedTaskEditPart.VISUAL_ID:
			return getOpinionatedTask_2008IncomingLinks(view);
		case ScriptedTaskEditPart.VISUAL_ID:
			return getScriptedTask_2015IncomingLinks(view);
		case ReusableComponentEditPart.VISUAL_ID:
			return getReusableComponent_2017IncomingLinks(view);
		case TaskEditPart.VISUAL_ID:
			return getTask_2010IncomingLinks(view);
		case TypeEditPart.VISUAL_ID:
			return getType_2011IncomingLinks(view);
		case FieldEditPart.VISUAL_ID:
			return getField_2014IncomingLinks(view);
		case LanguageEditPart.VISUAL_ID:
			return getLanguage_2013IncomingLinks(view);
		case SerialiserEditPart.VISUAL_ID:
			return getSerialiser_2016IncomingLinks(view);
		case Field2EditPart.VISUAL_ID:
			return getField_3003IncomingLinks(view);
		case Field3EditPart.VISUAL_ID:
			return getField_3001IncomingLinks(view);
		case ParameterEditPart.VISUAL_ID:
			return getParameter_3002IncomingLinks(view);
		case Parameter2EditPart.VISUAL_ID:
			return getParameter_3004IncomingLinks(view);
		}
		return Collections.emptyList();
	}

	/**
	* @generated
	*/
	public static List<CrossflowLinkDescriptor> getOutgoingLinks(View view) {
		switch (CrossflowVisualIDRegistry.getVisualID(view)) {
		case CsvSourceEditPart.VISUAL_ID:
			return getCsvSource_2001OutgoingLinks(view);
		case CsvSinkEditPart.VISUAL_ID:
			return getCsvSink_2002OutgoingLinks(view);
		case TopicEditPart.VISUAL_ID:
			return getTopic_2003OutgoingLinks(view);
		case QueueEditPart.VISUAL_ID:
			return getQueue_2004OutgoingLinks(view);
		case SourceEditPart.VISUAL_ID:
			return getSource_2005OutgoingLinks(view);
		case SinkEditPart.VISUAL_ID:
			return getSink_2006OutgoingLinks(view);
		case CommitmentTaskEditPart.VISUAL_ID:
			return getCommitmentTask_2007OutgoingLinks(view);
		case OpinionatedTaskEditPart.VISUAL_ID:
			return getOpinionatedTask_2008OutgoingLinks(view);
		case ScriptedTaskEditPart.VISUAL_ID:
			return getScriptedTask_2015OutgoingLinks(view);
		case ReusableComponentEditPart.VISUAL_ID:
			return getReusableComponent_2017OutgoingLinks(view);
		case TaskEditPart.VISUAL_ID:
			return getTask_2010OutgoingLinks(view);
		case TypeEditPart.VISUAL_ID:
			return getType_2011OutgoingLinks(view);
		case FieldEditPart.VISUAL_ID:
			return getField_2014OutgoingLinks(view);
		case LanguageEditPart.VISUAL_ID:
			return getLanguage_2013OutgoingLinks(view);
		case SerialiserEditPart.VISUAL_ID:
			return getSerialiser_2016OutgoingLinks(view);
		case Field2EditPart.VISUAL_ID:
			return getField_3003OutgoingLinks(view);
		case Field3EditPart.VISUAL_ID:
			return getField_3001OutgoingLinks(view);
		case ParameterEditPart.VISUAL_ID:
			return getParameter_3002OutgoingLinks(view);
		case Parameter2EditPart.VISUAL_ID:
			return getParameter_3004OutgoingLinks(view);
		}
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<CrossflowLinkDescriptor> getWorkflow_1000ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<CrossflowLinkDescriptor> getCsvSource_2001ContainedLinks(View view) {
		CsvSource modelElement = (CsvSource) view.getElement();
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Task_Output_4003(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<CrossflowLinkDescriptor> getCsvSink_2002ContainedLinks(View view) {
		CsvSink modelElement = (CsvSink) view.getElement();
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Task_Output_4003(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<CrossflowLinkDescriptor> getTopic_2003ContainedLinks(View view) {
		Topic modelElement = (Topic) view.getElement();
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Stream_Type_4001(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Stream_InputOf_4005(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<CrossflowLinkDescriptor> getQueue_2004ContainedLinks(View view) {
		Queue modelElement = (Queue) view.getElement();
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Stream_Type_4001(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Stream_InputOf_4005(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<CrossflowLinkDescriptor> getSource_2005ContainedLinks(View view) {
		Source modelElement = (Source) view.getElement();
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Task_Output_4003(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<CrossflowLinkDescriptor> getSink_2006ContainedLinks(View view) {
		Sink modelElement = (Sink) view.getElement();
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Task_Output_4003(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<CrossflowLinkDescriptor> getCommitmentTask_2007ContainedLinks(View view) {
		CommitmentTask modelElement = (CommitmentTask) view.getElement();
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Task_Output_4003(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<CrossflowLinkDescriptor> getOpinionatedTask_2008ContainedLinks(View view) {
		OpinionatedTask modelElement = (OpinionatedTask) view.getElement();
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Task_Output_4003(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<CrossflowLinkDescriptor> getScriptedTask_2015ContainedLinks(View view) {
		ScriptedTask modelElement = (ScriptedTask) view.getElement();
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Task_Output_4003(modelElement));
		return result;
	}

	/**
	* @generated
	*/
	public static List<CrossflowLinkDescriptor> getReusableComponent_2017ContainedLinks(View view) {
		ReusableComponent modelElement = (ReusableComponent) view.getElement();
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Task_Output_4003(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<CrossflowLinkDescriptor> getTask_2010ContainedLinks(View view) {
		Task modelElement = (Task) view.getElement();
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Task_Output_4003(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<CrossflowLinkDescriptor> getType_2011ContainedLinks(View view) {
		Type modelElement = (Type) view.getElement();
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Type_Extending_4004(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<CrossflowLinkDescriptor> getField_2014ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<CrossflowLinkDescriptor> getLanguage_2013ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	* @generated
	*/
	public static List<CrossflowLinkDescriptor> getSerialiser_2016ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	* @generated
	*/
	public static List<CrossflowLinkDescriptor> getField_3003ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<CrossflowLinkDescriptor> getField_3001ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<CrossflowLinkDescriptor> getParameter_3002ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	* @generated
	*/
	public static List<CrossflowLinkDescriptor> getParameter_3004ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<CrossflowLinkDescriptor> getCsvSource_2001IncomingLinks(View view) {
		CsvSource modelElement = (CsvSource) view.getElement();
		Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences = EcoreUtil.CrossReferencer
				.find(view.eResource().getResourceSet().getResources());
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		result.addAll(getIncomingFeatureModelFacetLinks_Stream_InputOf_4005(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<CrossflowLinkDescriptor> getCsvSink_2002IncomingLinks(View view) {
		CsvSink modelElement = (CsvSink) view.getElement();
		Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences = EcoreUtil.CrossReferencer
				.find(view.eResource().getResourceSet().getResources());
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		result.addAll(getIncomingFeatureModelFacetLinks_Stream_InputOf_4005(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<CrossflowLinkDescriptor> getTopic_2003IncomingLinks(View view) {
		Topic modelElement = (Topic) view.getElement();
		Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences = EcoreUtil.CrossReferencer
				.find(view.eResource().getResourceSet().getResources());
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		result.addAll(getIncomingFeatureModelFacetLinks_Task_Output_4003(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<CrossflowLinkDescriptor> getQueue_2004IncomingLinks(View view) {
		Queue modelElement = (Queue) view.getElement();
		Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences = EcoreUtil.CrossReferencer
				.find(view.eResource().getResourceSet().getResources());
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		result.addAll(getIncomingFeatureModelFacetLinks_Task_Output_4003(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<CrossflowLinkDescriptor> getSource_2005IncomingLinks(View view) {
		Source modelElement = (Source) view.getElement();
		Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences = EcoreUtil.CrossReferencer
				.find(view.eResource().getResourceSet().getResources());
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		result.addAll(getIncomingFeatureModelFacetLinks_Stream_InputOf_4005(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<CrossflowLinkDescriptor> getSink_2006IncomingLinks(View view) {
		Sink modelElement = (Sink) view.getElement();
		Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences = EcoreUtil.CrossReferencer
				.find(view.eResource().getResourceSet().getResources());
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		result.addAll(getIncomingFeatureModelFacetLinks_Stream_InputOf_4005(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<CrossflowLinkDescriptor> getCommitmentTask_2007IncomingLinks(View view) {
		CommitmentTask modelElement = (CommitmentTask) view.getElement();
		Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences = EcoreUtil.CrossReferencer
				.find(view.eResource().getResourceSet().getResources());
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		result.addAll(getIncomingFeatureModelFacetLinks_Stream_InputOf_4005(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<CrossflowLinkDescriptor> getOpinionatedTask_2008IncomingLinks(View view) {
		OpinionatedTask modelElement = (OpinionatedTask) view.getElement();
		Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences = EcoreUtil.CrossReferencer
				.find(view.eResource().getResourceSet().getResources());
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		result.addAll(getIncomingFeatureModelFacetLinks_Stream_InputOf_4005(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<CrossflowLinkDescriptor> getScriptedTask_2015IncomingLinks(View view) {
		ScriptedTask modelElement = (ScriptedTask) view.getElement();
		Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences = EcoreUtil.CrossReferencer
				.find(view.eResource().getResourceSet().getResources());
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		result.addAll(getIncomingFeatureModelFacetLinks_Stream_InputOf_4005(modelElement, crossReferences));
		return result;
	}

	/**
	* @generated
	*/
	public static List<CrossflowLinkDescriptor> getReusableComponent_2017IncomingLinks(View view) {
		ReusableComponent modelElement = (ReusableComponent) view.getElement();
		Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences = EcoreUtil.CrossReferencer
				.find(view.eResource().getResourceSet().getResources());
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		result.addAll(getIncomingFeatureModelFacetLinks_Stream_InputOf_4005(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<CrossflowLinkDescriptor> getTask_2010IncomingLinks(View view) {
		Task modelElement = (Task) view.getElement();
		Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences = EcoreUtil.CrossReferencer
				.find(view.eResource().getResourceSet().getResources());
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		result.addAll(getIncomingFeatureModelFacetLinks_Stream_InputOf_4005(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<CrossflowLinkDescriptor> getType_2011IncomingLinks(View view) {
		Type modelElement = (Type) view.getElement();
		Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences = EcoreUtil.CrossReferencer
				.find(view.eResource().getResourceSet().getResources());
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		result.addAll(getIncomingFeatureModelFacetLinks_Stream_Type_4001(modelElement, crossReferences));
		result.addAll(getIncomingFeatureModelFacetLinks_Type_Extending_4004(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<CrossflowLinkDescriptor> getField_2014IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<CrossflowLinkDescriptor> getLanguage_2013IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	* @generated
	*/
	public static List<CrossflowLinkDescriptor> getSerialiser_2016IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	* @generated
	*/
	public static List<CrossflowLinkDescriptor> getField_3003IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<CrossflowLinkDescriptor> getField_3001IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<CrossflowLinkDescriptor> getParameter_3002IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	* @generated
	*/
	public static List<CrossflowLinkDescriptor> getParameter_3004IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<CrossflowLinkDescriptor> getCsvSource_2001OutgoingLinks(View view) {
		CsvSource modelElement = (CsvSource) view.getElement();
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Task_Output_4003(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<CrossflowLinkDescriptor> getCsvSink_2002OutgoingLinks(View view) {
		CsvSink modelElement = (CsvSink) view.getElement();
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Task_Output_4003(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<CrossflowLinkDescriptor> getTopic_2003OutgoingLinks(View view) {
		Topic modelElement = (Topic) view.getElement();
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Stream_Type_4001(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Stream_InputOf_4005(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<CrossflowLinkDescriptor> getQueue_2004OutgoingLinks(View view) {
		Queue modelElement = (Queue) view.getElement();
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Stream_Type_4001(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Stream_InputOf_4005(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<CrossflowLinkDescriptor> getSource_2005OutgoingLinks(View view) {
		Source modelElement = (Source) view.getElement();
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Task_Output_4003(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<CrossflowLinkDescriptor> getSink_2006OutgoingLinks(View view) {
		Sink modelElement = (Sink) view.getElement();
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Task_Output_4003(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<CrossflowLinkDescriptor> getCommitmentTask_2007OutgoingLinks(View view) {
		CommitmentTask modelElement = (CommitmentTask) view.getElement();
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Task_Output_4003(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<CrossflowLinkDescriptor> getOpinionatedTask_2008OutgoingLinks(View view) {
		OpinionatedTask modelElement = (OpinionatedTask) view.getElement();
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Task_Output_4003(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<CrossflowLinkDescriptor> getScriptedTask_2015OutgoingLinks(View view) {
		ScriptedTask modelElement = (ScriptedTask) view.getElement();
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Task_Output_4003(modelElement));
		return result;
	}

	/**
	* @generated
	*/
	public static List<CrossflowLinkDescriptor> getReusableComponent_2017OutgoingLinks(View view) {
		ReusableComponent modelElement = (ReusableComponent) view.getElement();
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Task_Output_4003(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<CrossflowLinkDescriptor> getTask_2010OutgoingLinks(View view) {
		Task modelElement = (Task) view.getElement();
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Task_Output_4003(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<CrossflowLinkDescriptor> getType_2011OutgoingLinks(View view) {
		Type modelElement = (Type) view.getElement();
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Type_Extending_4004(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<CrossflowLinkDescriptor> getField_2014OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<CrossflowLinkDescriptor> getLanguage_2013OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	* @generated
	*/
	public static List<CrossflowLinkDescriptor> getSerialiser_2016OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	* @generated
	*/
	public static List<CrossflowLinkDescriptor> getField_3003OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<CrossflowLinkDescriptor> getField_3001OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<CrossflowLinkDescriptor> getParameter_3002OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	* @generated
	*/
	public static List<CrossflowLinkDescriptor> getParameter_3004OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	private static Collection<CrossflowLinkDescriptor> getIncomingFeatureModelFacetLinks_Stream_Type_4001(Type target,
			Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences) {
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		Collection<EStructuralFeature.Setting> settings = crossReferences.get(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() == CrossflowPackage.eINSTANCE.getStream_Type()) {
				result.add(new CrossflowLinkDescriptor(setting.getEObject(), target,
						CrossflowElementTypes.StreamType_4001, StreamTypeEditPart.VISUAL_ID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection<CrossflowLinkDescriptor> getIncomingFeatureModelFacetLinks_Stream_InputOf_4005(
			Task target, Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences) {
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		Collection<EStructuralFeature.Setting> settings = crossReferences.get(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() == CrossflowPackage.eINSTANCE.getStream_InputOf()) {
				result.add(new CrossflowLinkDescriptor(setting.getEObject(), target,
						CrossflowElementTypes.StreamInputOf_4005, StreamInputOfEditPart.VISUAL_ID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection<CrossflowLinkDescriptor> getIncomingFeatureModelFacetLinks_Task_Output_4003(Stream target,
			Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences) {
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		Collection<EStructuralFeature.Setting> settings = crossReferences.get(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() == CrossflowPackage.eINSTANCE.getTask_Output()) {
				result.add(new CrossflowLinkDescriptor(setting.getEObject(), target,
						CrossflowElementTypes.TaskOutput_4003, TaskOutputEditPart.VISUAL_ID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection<CrossflowLinkDescriptor> getIncomingFeatureModelFacetLinks_Type_Extending_4004(
			Type target, Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences) {
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		Collection<EStructuralFeature.Setting> settings = crossReferences.get(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() == CrossflowPackage.eINSTANCE.getType_Extending()) {
				result.add(new CrossflowLinkDescriptor(setting.getEObject(), target,
						CrossflowElementTypes.TypeExtending_4004, TypeExtendingEditPart.VISUAL_ID));
			}
		}
		return result;
	}

	/**
	* @generated
	*/
	private static Collection<CrossflowLinkDescriptor> getOutgoingFeatureModelFacetLinks_Stream_Type_4001(
			Stream source) {
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		Type destination = source.getType();
		if (destination == null) {
			return result;
		}
		result.add(new CrossflowLinkDescriptor(source, destination, CrossflowElementTypes.StreamType_4001,
				StreamTypeEditPart.VISUAL_ID));
		return result;
	}

	/**
	* @generated
	*/
	private static Collection<CrossflowLinkDescriptor> getOutgoingFeatureModelFacetLinks_Stream_InputOf_4005(
			Stream source) {
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		for (Iterator<?> destinations = source.getInputOf().iterator(); destinations.hasNext();) {
			Task destination = (Task) destinations.next();
			result.add(new CrossflowLinkDescriptor(source, destination, CrossflowElementTypes.StreamInputOf_4005,
					StreamInputOfEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	* @generated
	*/
	private static Collection<CrossflowLinkDescriptor> getOutgoingFeatureModelFacetLinks_Task_Output_4003(Task source) {
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		for (Iterator<?> destinations = source.getOutput().iterator(); destinations.hasNext();) {
			Stream destination = (Stream) destinations.next();
			result.add(new CrossflowLinkDescriptor(source, destination, CrossflowElementTypes.TaskOutput_4003,
					TaskOutputEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	* @generated
	*/
	private static Collection<CrossflowLinkDescriptor> getOutgoingFeatureModelFacetLinks_Type_Extending_4004(
			Type source) {
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		for (Iterator<?> destinations = source.getExtending().iterator(); destinations.hasNext();) {
			Type destination = (Type) destinations.next();
			result.add(new CrossflowLinkDescriptor(source, destination, CrossflowElementTypes.TypeExtending_4004,
					TypeExtendingEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	* @generated
	*/
	public static final DiagramUpdater TYPED_INSTANCE = new DiagramUpdater() {
		/**
		* @generated
		*/
		@Override

		public List<CrossflowNodeDescriptor> getSemanticChildren(View view) {
			return CrossflowDiagramUpdater.getSemanticChildren(view);
		}

		/**
		* @generated
		*/
		@Override

		public List<CrossflowLinkDescriptor> getContainedLinks(View view) {
			return CrossflowDiagramUpdater.getContainedLinks(view);
		}

		/**
		* @generated
		*/
		@Override

		public List<CrossflowLinkDescriptor> getIncomingLinks(View view) {
			return CrossflowDiagramUpdater.getIncomingLinks(view);
		}

		/**
		* @generated
		*/
		@Override

		public List<CrossflowLinkDescriptor> getOutgoingLinks(View view) {
			return CrossflowDiagramUpdater.getOutgoingLinks(view);
		}
	};

}
