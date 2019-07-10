/*
* 
*/
package crossflow.diagram.part;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.tooling.runtime.structure.DiagramStructure;

import crossflow.CrossflowPackage;
import crossflow.Workflow;
import crossflow.diagram.edit.parts.CommitmentTaskEditPart;
import crossflow.diagram.edit.parts.CommitmentTaskNameEditPart;
import crossflow.diagram.edit.parts.CsvSinkEditPart;
import crossflow.diagram.edit.parts.CsvSinkNameEditPart;
import crossflow.diagram.edit.parts.CsvSourceEditPart;
import crossflow.diagram.edit.parts.CsvSourceNameEditPart;
import crossflow.diagram.edit.parts.Field2EditPart;
import crossflow.diagram.edit.parts.Field3EditPart;
import crossflow.diagram.edit.parts.FieldEditPart;
import crossflow.diagram.edit.parts.FieldNameType2EditPart;
import crossflow.diagram.edit.parts.FieldNameType3EditPart;
import crossflow.diagram.edit.parts.FieldNameTypeEditPart;
import crossflow.diagram.edit.parts.LanguageEditPart;
import crossflow.diagram.edit.parts.LanguageLanguageParametersCompartmentEditPart;
import crossflow.diagram.edit.parts.LanguageNameEditPart;
import crossflow.diagram.edit.parts.OpinionatedTaskEditPart;
import crossflow.diagram.edit.parts.OpinionatedTaskNameEditPart;
import crossflow.diagram.edit.parts.ParameterEditPart;
import crossflow.diagram.edit.parts.ParameterNameValueEditPart;
import crossflow.diagram.edit.parts.QueueEditPart;
import crossflow.diagram.edit.parts.QueueNameEditPart;
import crossflow.diagram.edit.parts.ScriptedTaskEditPart;
import crossflow.diagram.edit.parts.ScriptedTaskNameEditPart;
import crossflow.diagram.edit.parts.ScriptedTaskScriptedTaskOutputVariablesCompartmentEditPart;
import crossflow.diagram.edit.parts.SinkEditPart;
import crossflow.diagram.edit.parts.SinkNameEditPart;
import crossflow.diagram.edit.parts.SourceEditPart;
import crossflow.diagram.edit.parts.SourceNameEditPart;
import crossflow.diagram.edit.parts.StreamInputOfEditPart;
import crossflow.diagram.edit.parts.StreamTypeEditPart;
import crossflow.diagram.edit.parts.TaskEditPart;
import crossflow.diagram.edit.parts.TaskNameEditPart;
import crossflow.diagram.edit.parts.TaskOutputEditPart;
import crossflow.diagram.edit.parts.TopicEditPart;
import crossflow.diagram.edit.parts.TopicNameEditPart;
import crossflow.diagram.edit.parts.TypeEditPart;
import crossflow.diagram.edit.parts.TypeExtendingEditPart;
import crossflow.diagram.edit.parts.TypeNameEditPart;
import crossflow.diagram.edit.parts.TypeTypeFieldsCompartmentEditPart;
import crossflow.diagram.edit.parts.WorkflowEditPart;
import crossflow.diagram.edit.parts.WrappingLabel2EditPart;
import crossflow.diagram.edit.parts.WrappingLabel3EditPart;
import crossflow.diagram.edit.parts.WrappingLabel4EditPart;
import crossflow.diagram.edit.parts.WrappingLabelEditPart;

/**
 * This registry is used to determine which type of visual object should be
 * created for the corresponding Diagram, Node, ChildNode or Link represented
 * by a domain model object.
 * 
 * @generated
 */
public class CrossflowVisualIDRegistry {

	/**
	* @generated
	*/
	private static final String DEBUG_KEY = "org.eclipse.scava.crossflow.language.diagram/debug/visualID"; //$NON-NLS-1$

	/**
	* @generated
	*/
	public static int getVisualID(View view) {
		if (view instanceof Diagram) {
			if (WorkflowEditPart.MODEL_ID.equals(view.getType())) {
				return WorkflowEditPart.VISUAL_ID;
			} else {
				return -1;
			}
		}
		return crossflow.diagram.part.CrossflowVisualIDRegistry.getVisualID(view.getType());
	}

	/**
	* @generated
	*/
	public static String getModelID(View view) {
		View diagram = view.getDiagram();
		while (view != diagram) {
			EAnnotation annotation = view.getEAnnotation("Shortcut"); //$NON-NLS-1$
			if (annotation != null) {
				return (String) annotation.getDetails().get("modelID"); //$NON-NLS-1$
			}
			view = (View) view.eContainer();
		}
		return diagram != null ? diagram.getType() : null;
	}

	/**
	* @generated
	*/
	public static int getVisualID(String type) {
		try {
			return Integer.parseInt(type);
		} catch (NumberFormatException e) {
			if (Boolean.TRUE.toString().equalsIgnoreCase(Platform.getDebugOption(DEBUG_KEY))) {
				CrossflowDiagramEditorPlugin.getInstance()
						.logError("Unable to parse view type as a visualID number: " + type);
			}
		}
		return -1;
	}

	/**
	* @generated
	*/
	public static String getType(int visualID) {
		return Integer.toString(visualID);
	}

	/**
	* @generated
	*/
	public static int getDiagramVisualID(EObject domainElement) {
		if (domainElement == null) {
			return -1;
		}
		if (CrossflowPackage.eINSTANCE.getWorkflow().isSuperTypeOf(domainElement.eClass())
				&& isDiagram((Workflow) domainElement)) {
			return WorkflowEditPart.VISUAL_ID;
		}
		return -1;
	}

	/**
	* @generated
	*/
	public static int getNodeVisualID(View containerView, EObject domainElement) {
		if (domainElement == null) {
			return -1;
		}
		String containerModelID = crossflow.diagram.part.CrossflowVisualIDRegistry.getModelID(containerView);
		if (!WorkflowEditPart.MODEL_ID.equals(containerModelID) && !"crossflow".equals(containerModelID)) { //$NON-NLS-1$
			return -1;
		}
		int containerVisualID;
		if (WorkflowEditPart.MODEL_ID.equals(containerModelID)) {
			containerVisualID = crossflow.diagram.part.CrossflowVisualIDRegistry.getVisualID(containerView);
		} else {
			if (containerView instanceof Diagram) {
				containerVisualID = WorkflowEditPart.VISUAL_ID;
			} else {
				return -1;
			}
		}
		switch (containerVisualID) {
		case WorkflowEditPart.VISUAL_ID:
			if (CrossflowPackage.eINSTANCE.getCsvSource().isSuperTypeOf(domainElement.eClass())) {
				return CsvSourceEditPart.VISUAL_ID;
			}
			if (CrossflowPackage.eINSTANCE.getCsvSink().isSuperTypeOf(domainElement.eClass())) {
				return CsvSinkEditPart.VISUAL_ID;
			}
			if (CrossflowPackage.eINSTANCE.getTopic().isSuperTypeOf(domainElement.eClass())) {
				return TopicEditPart.VISUAL_ID;
			}
			if (CrossflowPackage.eINSTANCE.getQueue().isSuperTypeOf(domainElement.eClass())) {
				return QueueEditPart.VISUAL_ID;
			}
			if (CrossflowPackage.eINSTANCE.getSource().isSuperTypeOf(domainElement.eClass())) {
				return SourceEditPart.VISUAL_ID;
			}
			if (CrossflowPackage.eINSTANCE.getSink().isSuperTypeOf(domainElement.eClass())) {
				return SinkEditPart.VISUAL_ID;
			}
			if (CrossflowPackage.eINSTANCE.getCommitmentTask().isSuperTypeOf(domainElement.eClass())) {
				return CommitmentTaskEditPart.VISUAL_ID;
			}
			if (CrossflowPackage.eINSTANCE.getOpinionatedTask().isSuperTypeOf(domainElement.eClass())) {
				return OpinionatedTaskEditPart.VISUAL_ID;
			}
			if (CrossflowPackage.eINSTANCE.getScriptedTask().isSuperTypeOf(domainElement.eClass())) {
				return ScriptedTaskEditPart.VISUAL_ID;
			}
			if (CrossflowPackage.eINSTANCE.getTask().isSuperTypeOf(domainElement.eClass())) {
				return TaskEditPart.VISUAL_ID;
			}
			if (CrossflowPackage.eINSTANCE.getType().isSuperTypeOf(domainElement.eClass())) {
				return TypeEditPart.VISUAL_ID;
			}
			if (CrossflowPackage.eINSTANCE.getField().isSuperTypeOf(domainElement.eClass())) {
				return FieldEditPart.VISUAL_ID;
			}
			if (CrossflowPackage.eINSTANCE.getLanguage().isSuperTypeOf(domainElement.eClass())) {
				return LanguageEditPart.VISUAL_ID;
			}
			break;
		case ScriptedTaskScriptedTaskOutputVariablesCompartmentEditPart.VISUAL_ID:
			if (CrossflowPackage.eINSTANCE.getField().isSuperTypeOf(domainElement.eClass())) {
				return Field2EditPart.VISUAL_ID;
			}
			break;
		case TypeTypeFieldsCompartmentEditPart.VISUAL_ID:
			if (CrossflowPackage.eINSTANCE.getField().isSuperTypeOf(domainElement.eClass())) {
				return Field3EditPart.VISUAL_ID;
			}
			break;
		case LanguageLanguageParametersCompartmentEditPart.VISUAL_ID:
			if (CrossflowPackage.eINSTANCE.getParameter().isSuperTypeOf(domainElement.eClass())) {
				return ParameterEditPart.VISUAL_ID;
			}
			break;
		}
		return -1;
	}

	/**
	* @generated
	*/
	public static boolean canCreateNode(View containerView, int nodeVisualID) {
		String containerModelID = crossflow.diagram.part.CrossflowVisualIDRegistry.getModelID(containerView);
		if (!WorkflowEditPart.MODEL_ID.equals(containerModelID) && !"crossflow".equals(containerModelID)) { //$NON-NLS-1$
			return false;
		}
		int containerVisualID;
		if (WorkflowEditPart.MODEL_ID.equals(containerModelID)) {
			containerVisualID = crossflow.diagram.part.CrossflowVisualIDRegistry.getVisualID(containerView);
		} else {
			if (containerView instanceof Diagram) {
				containerVisualID = WorkflowEditPart.VISUAL_ID;
			} else {
				return false;
			}
		}
		switch (containerVisualID) {
		case WorkflowEditPart.VISUAL_ID:
			if (CsvSourceEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (CsvSinkEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (TopicEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (QueueEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (SourceEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (SinkEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (CommitmentTaskEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (OpinionatedTaskEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (ScriptedTaskEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (TaskEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (TypeEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (FieldEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (LanguageEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case CsvSourceEditPart.VISUAL_ID:
			if (CsvSourceNameEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case CsvSinkEditPart.VISUAL_ID:
			if (CsvSinkNameEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case TopicEditPart.VISUAL_ID:
			if (TopicNameEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case QueueEditPart.VISUAL_ID:
			if (QueueNameEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case SourceEditPart.VISUAL_ID:
			if (SourceNameEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case SinkEditPart.VISUAL_ID:
			if (SinkNameEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case CommitmentTaskEditPart.VISUAL_ID:
			if (CommitmentTaskNameEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case OpinionatedTaskEditPart.VISUAL_ID:
			if (OpinionatedTaskNameEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case ScriptedTaskEditPart.VISUAL_ID:
			if (ScriptedTaskNameEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (ScriptedTaskScriptedTaskOutputVariablesCompartmentEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case TaskEditPart.VISUAL_ID:
			if (TaskNameEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case TypeEditPart.VISUAL_ID:
			if (TypeNameEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (TypeTypeFieldsCompartmentEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case FieldEditPart.VISUAL_ID:
			if (FieldNameTypeEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case LanguageEditPart.VISUAL_ID:
			if (LanguageNameEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (LanguageLanguageParametersCompartmentEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case Field2EditPart.VISUAL_ID:
			if (FieldNameType2EditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case Field3EditPart.VISUAL_ID:
			if (FieldNameType3EditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case ParameterEditPart.VISUAL_ID:
			if (ParameterNameValueEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case ScriptedTaskScriptedTaskOutputVariablesCompartmentEditPart.VISUAL_ID:
			if (Field2EditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case TypeTypeFieldsCompartmentEditPart.VISUAL_ID:
			if (Field3EditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case LanguageLanguageParametersCompartmentEditPart.VISUAL_ID:
			if (ParameterEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case StreamTypeEditPart.VISUAL_ID:
			if (WrappingLabelEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case StreamInputOfEditPart.VISUAL_ID:
			if (WrappingLabel2EditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case TaskOutputEditPart.VISUAL_ID:
			if (WrappingLabel3EditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case TypeExtendingEditPart.VISUAL_ID:
			if (WrappingLabel4EditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		}
		return false;
	}

	/**
	* @generated
	*/
	public static int getLinkWithClassVisualID(EObject domainElement) {
		if (domainElement == null) {
			return -1;
		}
		return -1;
	}

	/**
	* User can change implementation of this method to handle some specific
	* situations not covered by default logic.
	* 
	* @generated
	*/
	private static boolean isDiagram(Workflow element) {
		return true;
	}

	/**
	* @generated
	*/
	public static boolean checkNodeVisualID(View containerView, EObject domainElement, int candidate) {
		if (candidate == -1) {
			//unrecognized id is always bad
			return false;
		}
		int basic = getNodeVisualID(containerView, domainElement);
		return basic == candidate;
	}

	/**
	* @generated
	*/
	public static boolean isCompartmentVisualID(int visualID) {
		switch (visualID) {
		case ScriptedTaskScriptedTaskOutputVariablesCompartmentEditPart.VISUAL_ID:
		case TypeTypeFieldsCompartmentEditPart.VISUAL_ID:
		case LanguageLanguageParametersCompartmentEditPart.VISUAL_ID:
			return true;
		default:
			break;
		}
		return false;
	}

	/**
	* @generated
	*/
	public static boolean isSemanticLeafVisualID(int visualID) {
		switch (visualID) {
		case WorkflowEditPart.VISUAL_ID:
			return false;
		case CsvSourceEditPart.VISUAL_ID:
		case CsvSinkEditPart.VISUAL_ID:
		case TopicEditPart.VISUAL_ID:
		case QueueEditPart.VISUAL_ID:
		case SourceEditPart.VISUAL_ID:
		case SinkEditPart.VISUAL_ID:
		case CommitmentTaskEditPart.VISUAL_ID:
		case OpinionatedTaskEditPart.VISUAL_ID:
		case TaskEditPart.VISUAL_ID:
		case FieldEditPart.VISUAL_ID:
		case Field3EditPart.VISUAL_ID:
		case ParameterEditPart.VISUAL_ID:
		case Field2EditPart.VISUAL_ID:
			return true;
		default:
			break;
		}
		return false;
	}

	/**
	* @generated
	*/
	public static final DiagramStructure TYPED_INSTANCE = new DiagramStructure() {
		/**
		* @generated
		*/
		@Override

		public int getVisualID(View view) {
			return crossflow.diagram.part.CrossflowVisualIDRegistry.getVisualID(view);
		}

		/**
		* @generated
		*/
		@Override

		public String getModelID(View view) {
			return crossflow.diagram.part.CrossflowVisualIDRegistry.getModelID(view);
		}

		/**
		* @generated
		*/
		@Override

		public int getNodeVisualID(View containerView, EObject domainElement) {
			return crossflow.diagram.part.CrossflowVisualIDRegistry.getNodeVisualID(containerView, domainElement);
		}

		/**
		* @generated
		*/
		@Override

		public boolean checkNodeVisualID(View containerView, EObject domainElement, int candidate) {
			return crossflow.diagram.part.CrossflowVisualIDRegistry.checkNodeVisualID(containerView, domainElement,
					candidate);
		}

		/**
		* @generated
		*/
		@Override

		public boolean isCompartmentVisualID(int visualID) {
			return crossflow.diagram.part.CrossflowVisualIDRegistry.isCompartmentVisualID(visualID);
		}

		/**
		* @generated
		*/
		@Override

		public boolean isSemanticLeafVisualID(int visualID) {
			return crossflow.diagram.part.CrossflowVisualIDRegistry.isSemanticLeafVisualID(visualID);
		}
	};

}
