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
import crossflow.diagram.edit.parts.ConfigurationEditPart;
import crossflow.diagram.edit.parts.ConfigurationNumberOfWorkersIsMasterEditPart;
import crossflow.diagram.edit.parts.Field2EditPart;
import crossflow.diagram.edit.parts.FieldEditPart;
import crossflow.diagram.edit.parts.FieldName2EditPart;
import crossflow.diagram.edit.parts.FieldNameEditPart;
import crossflow.diagram.edit.parts.QueueEditPart;
import crossflow.diagram.edit.parts.SinkEditPart;
import crossflow.diagram.edit.parts.SinkNameEditPart;
import crossflow.diagram.edit.parts.SourceEditPart;
import crossflow.diagram.edit.parts.SourceNameEditPart;
import crossflow.diagram.edit.parts.StreamTypeEditPart;
import crossflow.diagram.edit.parts.TaskEditPart;
import crossflow.diagram.edit.parts.TaskInputEditPart;
import crossflow.diagram.edit.parts.TaskNameEditPart;
import crossflow.diagram.edit.parts.TaskOutputEditPart;
import crossflow.diagram.edit.parts.TopicEditPart;
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
			if (CrossflowPackage.eINSTANCE.getConfiguration().isSuperTypeOf(domainElement.eClass())) {
				return ConfigurationEditPart.VISUAL_ID;
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
			break;
		case TypeTypeFieldsCompartmentEditPart.VISUAL_ID:
			if (CrossflowPackage.eINSTANCE.getField().isSuperTypeOf(domainElement.eClass())) {
				return Field2EditPart.VISUAL_ID;
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
			if (ConfigurationEditPart.VISUAL_ID == nodeVisualID) {
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
		case ConfigurationEditPart.VISUAL_ID:
			if (ConfigurationNumberOfWorkersIsMasterEditPart.VISUAL_ID == nodeVisualID) {
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
			if (FieldNameEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case Field2EditPart.VISUAL_ID:
			if (FieldName2EditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case TypeTypeFieldsCompartmentEditPart.VISUAL_ID:
			if (Field2EditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case StreamTypeEditPart.VISUAL_ID:
			if (WrappingLabelEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case TaskInputEditPart.VISUAL_ID:
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
		case TypeTypeFieldsCompartmentEditPart.VISUAL_ID:
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
		case TopicEditPart.VISUAL_ID:
		case QueueEditPart.VISUAL_ID:
		case SourceEditPart.VISUAL_ID:
		case SinkEditPart.VISUAL_ID:
		case ConfigurationEditPart.VISUAL_ID:
		case TaskEditPart.VISUAL_ID:
		case FieldEditPart.VISUAL_ID:
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
