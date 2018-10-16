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

import crossflow.Configuration;
import crossflow.CrossflowPackage;
import crossflow.Field;
import crossflow.Queue;
import crossflow.Sink;
import crossflow.Source;
import crossflow.Stream;
import crossflow.Task;
import crossflow.Topic;
import crossflow.Type;
import crossflow.Workflow;
import crossflow.diagram.edit.parts.ConfigurationEditPart;
import crossflow.diagram.edit.parts.Field2EditPart;
import crossflow.diagram.edit.parts.FieldEditPart;
import crossflow.diagram.edit.parts.QueueEditPart;
import crossflow.diagram.edit.parts.SinkEditPart;
import crossflow.diagram.edit.parts.SourceEditPart;
import crossflow.diagram.edit.parts.StreamTypeEditPart;
import crossflow.diagram.edit.parts.TaskEditPart;
import crossflow.diagram.edit.parts.TaskInputEditPart;
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
		case TypeTypeFieldsCompartmentEditPart.VISUAL_ID:
			return getTypeTypeFieldsCompartment_7001SemanticChildren(view);
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
		for (Iterator<?> it = modelElement.getTasks().iterator(); it.hasNext();) {
			Task childElement = (Task) it.next();
			int visualID = CrossflowVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == SourceEditPart.VISUAL_ID) {
				result.add(new CrossflowNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == SinkEditPart.VISUAL_ID) {
				result.add(new CrossflowNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == TaskEditPart.VISUAL_ID) {
				result.add(new CrossflowNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		{
			Configuration childElement = modelElement.getConfiguration();
			int visualID = CrossflowVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == ConfigurationEditPart.VISUAL_ID) {
				result.add(new CrossflowNodeDescriptor(childElement, visualID));
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
	public static List<CrossflowLinkDescriptor> getContainedLinks(View view) {
		switch (CrossflowVisualIDRegistry.getVisualID(view)) {
		case WorkflowEditPart.VISUAL_ID:
			return getWorkflow_1000ContainedLinks(view);
		case TopicEditPart.VISUAL_ID:
			return getTopic_2001ContainedLinks(view);
		case QueueEditPart.VISUAL_ID:
			return getQueue_2002ContainedLinks(view);
		case SourceEditPart.VISUAL_ID:
			return getSource_2003ContainedLinks(view);
		case SinkEditPart.VISUAL_ID:
			return getSink_2004ContainedLinks(view);
		case ConfigurationEditPart.VISUAL_ID:
			return getConfiguration_2005ContainedLinks(view);
		case TaskEditPart.VISUAL_ID:
			return getTask_2006ContainedLinks(view);
		case TypeEditPart.VISUAL_ID:
			return getType_2007ContainedLinks(view);
		case FieldEditPart.VISUAL_ID:
			return getField_2008ContainedLinks(view);
		case Field2EditPart.VISUAL_ID:
			return getField_3001ContainedLinks(view);
		}
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<CrossflowLinkDescriptor> getIncomingLinks(View view) {
		switch (CrossflowVisualIDRegistry.getVisualID(view)) {
		case TopicEditPart.VISUAL_ID:
			return getTopic_2001IncomingLinks(view);
		case QueueEditPart.VISUAL_ID:
			return getQueue_2002IncomingLinks(view);
		case SourceEditPart.VISUAL_ID:
			return getSource_2003IncomingLinks(view);
		case SinkEditPart.VISUAL_ID:
			return getSink_2004IncomingLinks(view);
		case ConfigurationEditPart.VISUAL_ID:
			return getConfiguration_2005IncomingLinks(view);
		case TaskEditPart.VISUAL_ID:
			return getTask_2006IncomingLinks(view);
		case TypeEditPart.VISUAL_ID:
			return getType_2007IncomingLinks(view);
		case FieldEditPart.VISUAL_ID:
			return getField_2008IncomingLinks(view);
		case Field2EditPart.VISUAL_ID:
			return getField_3001IncomingLinks(view);
		}
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<CrossflowLinkDescriptor> getOutgoingLinks(View view) {
		switch (CrossflowVisualIDRegistry.getVisualID(view)) {
		case TopicEditPart.VISUAL_ID:
			return getTopic_2001OutgoingLinks(view);
		case QueueEditPart.VISUAL_ID:
			return getQueue_2002OutgoingLinks(view);
		case SourceEditPart.VISUAL_ID:
			return getSource_2003OutgoingLinks(view);
		case SinkEditPart.VISUAL_ID:
			return getSink_2004OutgoingLinks(view);
		case ConfigurationEditPart.VISUAL_ID:
			return getConfiguration_2005OutgoingLinks(view);
		case TaskEditPart.VISUAL_ID:
			return getTask_2006OutgoingLinks(view);
		case TypeEditPart.VISUAL_ID:
			return getType_2007OutgoingLinks(view);
		case FieldEditPart.VISUAL_ID:
			return getField_2008OutgoingLinks(view);
		case Field2EditPart.VISUAL_ID:
			return getField_3001OutgoingLinks(view);
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
	public static List<CrossflowLinkDescriptor> getTopic_2001ContainedLinks(View view) {
		Topic modelElement = (Topic) view.getElement();
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Stream_Type_4001(modelElement));
		return result;
	}

	/**
	* @generated
	*/
	public static List<CrossflowLinkDescriptor> getQueue_2002ContainedLinks(View view) {
		Queue modelElement = (Queue) view.getElement();
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Stream_Type_4001(modelElement));
		return result;
	}

	/**
	* @generated
	*/
	public static List<CrossflowLinkDescriptor> getSource_2003ContainedLinks(View view) {
		Source modelElement = (Source) view.getElement();
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Task_Input_4002(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Task_Output_4003(modelElement));
		return result;
	}

	/**
	* @generated
	*/
	public static List<CrossflowLinkDescriptor> getSink_2004ContainedLinks(View view) {
		Sink modelElement = (Sink) view.getElement();
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Task_Input_4002(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Task_Output_4003(modelElement));
		return result;
	}

	/**
	* @generated
	*/
	public static List<CrossflowLinkDescriptor> getConfiguration_2005ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	* @generated
	*/
	public static List<CrossflowLinkDescriptor> getTask_2006ContainedLinks(View view) {
		Task modelElement = (Task) view.getElement();
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Task_Input_4002(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Task_Output_4003(modelElement));
		return result;
	}

	/**
	* @generated
	*/
	public static List<CrossflowLinkDescriptor> getType_2007ContainedLinks(View view) {
		Type modelElement = (Type) view.getElement();
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Type_Extending_4004(modelElement));
		return result;
	}

	/**
	* @generated
	*/
	public static List<CrossflowLinkDescriptor> getField_2008ContainedLinks(View view) {
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
	public static List<CrossflowLinkDescriptor> getTopic_2001IncomingLinks(View view) {
		Topic modelElement = (Topic) view.getElement();
		Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences = EcoreUtil.CrossReferencer
				.find(view.eResource().getResourceSet().getResources());
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		result.addAll(getIncomingFeatureModelFacetLinks_Task_Input_4002(modelElement, crossReferences));
		result.addAll(getIncomingFeatureModelFacetLinks_Task_Output_4003(modelElement, crossReferences));
		return result;
	}

	/**
	* @generated
	*/
	public static List<CrossflowLinkDescriptor> getQueue_2002IncomingLinks(View view) {
		Queue modelElement = (Queue) view.getElement();
		Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences = EcoreUtil.CrossReferencer
				.find(view.eResource().getResourceSet().getResources());
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		result.addAll(getIncomingFeatureModelFacetLinks_Task_Input_4002(modelElement, crossReferences));
		result.addAll(getIncomingFeatureModelFacetLinks_Task_Output_4003(modelElement, crossReferences));
		return result;
	}

	/**
	* @generated
	*/
	public static List<CrossflowLinkDescriptor> getSource_2003IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	* @generated
	*/
	public static List<CrossflowLinkDescriptor> getSink_2004IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	* @generated
	*/
	public static List<CrossflowLinkDescriptor> getConfiguration_2005IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	* @generated
	*/
	public static List<CrossflowLinkDescriptor> getTask_2006IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	* @generated
	*/
	public static List<CrossflowLinkDescriptor> getType_2007IncomingLinks(View view) {
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
	public static List<CrossflowLinkDescriptor> getField_2008IncomingLinks(View view) {
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
	public static List<CrossflowLinkDescriptor> getTopic_2001OutgoingLinks(View view) {
		Topic modelElement = (Topic) view.getElement();
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Stream_Type_4001(modelElement));
		return result;
	}

	/**
	* @generated
	*/
	public static List<CrossflowLinkDescriptor> getQueue_2002OutgoingLinks(View view) {
		Queue modelElement = (Queue) view.getElement();
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Stream_Type_4001(modelElement));
		return result;
	}

	/**
	* @generated
	*/
	public static List<CrossflowLinkDescriptor> getSource_2003OutgoingLinks(View view) {
		Source modelElement = (Source) view.getElement();
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Task_Input_4002(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Task_Output_4003(modelElement));
		return result;
	}

	/**
	* @generated
	*/
	public static List<CrossflowLinkDescriptor> getSink_2004OutgoingLinks(View view) {
		Sink modelElement = (Sink) view.getElement();
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Task_Input_4002(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Task_Output_4003(modelElement));
		return result;
	}

	/**
	* @generated
	*/
	public static List<CrossflowLinkDescriptor> getConfiguration_2005OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	* @generated
	*/
	public static List<CrossflowLinkDescriptor> getTask_2006OutgoingLinks(View view) {
		Task modelElement = (Task) view.getElement();
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Task_Input_4002(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Task_Output_4003(modelElement));
		return result;
	}

	/**
	* @generated
	*/
	public static List<CrossflowLinkDescriptor> getType_2007OutgoingLinks(View view) {
		Type modelElement = (Type) view.getElement();
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Type_Extending_4004(modelElement));
		return result;
	}

	/**
	* @generated
	*/
	public static List<CrossflowLinkDescriptor> getField_2008OutgoingLinks(View view) {
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
	private static Collection<CrossflowLinkDescriptor> getIncomingFeatureModelFacetLinks_Task_Input_4002(Stream target,
			Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences) {
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		Collection<EStructuralFeature.Setting> settings = crossReferences.get(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() == CrossflowPackage.eINSTANCE.getTask_Input()) {
				result.add(new CrossflowLinkDescriptor(setting.getEObject(), target,
						CrossflowElementTypes.TaskInput_4002, TaskInputEditPart.VISUAL_ID));
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
	private static Collection<CrossflowLinkDescriptor> getOutgoingFeatureModelFacetLinks_Task_Input_4002(Task source) {
		LinkedList<CrossflowLinkDescriptor> result = new LinkedList<CrossflowLinkDescriptor>();
		for (Iterator<?> destinations = source.getInput().iterator(); destinations.hasNext();) {
			Stream destination = (Stream) destinations.next();
			result.add(new CrossflowLinkDescriptor(source, destination, CrossflowElementTypes.TaskInput_4002,
					TaskInputEditPart.VISUAL_ID));
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
