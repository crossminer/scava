/*
 * 
 */
package crossflow.diagram.providers;

import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.tooling.runtime.providers.DiagramElementTypeImages;
import org.eclipse.gmf.tooling.runtime.providers.DiagramElementTypes;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

import crossflow.CrossflowPackage;
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
import crossflow.diagram.edit.parts.WorkflowEditPart;
import crossflow.diagram.part.CrossflowDiagramEditorPlugin;

/**
 * @generated
 */
public class CrossflowElementTypes {

	/**
	 * @generated
	 */
	private CrossflowElementTypes() {
	}

	/**
	 * @generated
	 */
	private static Map<IElementType, ENamedElement> elements;

	/**
	 * @generated
	 */
	private static DiagramElementTypeImages elementTypeImages = new DiagramElementTypeImages(
			CrossflowDiagramEditorPlugin.getInstance().getItemProvidersAdapterFactory());

	/**
	 * @generated
	 */
	private static Set<IElementType> KNOWN_ELEMENT_TYPES;

	/**
	 * @generated
	 */
	public static final IElementType Workflow_1000 = getElementType(
			"org.eclipse.scava.crossflow.language.diagram.Workflow_1000"); //$NON-NLS-1$
	/**
	* @generated
	*/
	public static final IElementType Topic_2001 = getElementType(
			"org.eclipse.scava.crossflow.language.diagram.Topic_2001"); //$NON-NLS-1$

	/**
	* @generated
	*/
	public static final IElementType Queue_2002 = getElementType(
			"org.eclipse.scava.crossflow.language.diagram.Queue_2002"); //$NON-NLS-1$

	/**
	* @generated
	*/
	public static final IElementType Source_2003 = getElementType(
			"org.eclipse.scava.crossflow.language.diagram.Source_2003"); //$NON-NLS-1$

	/**
	* @generated
	*/
	public static final IElementType Sink_2004 = getElementType(
			"org.eclipse.scava.crossflow.language.diagram.Sink_2004"); //$NON-NLS-1$

	/**
	* @generated
	*/
	public static final IElementType Configuration_2005 = getElementType(
			"org.eclipse.scava.crossflow.language.diagram.Configuration_2005"); //$NON-NLS-1$

	/**
	* @generated
	*/
	public static final IElementType Task_2006 = getElementType(
			"org.eclipse.scava.crossflow.language.diagram.Task_2006"); //$NON-NLS-1$

	/**
	* @generated
	*/
	public static final IElementType Type_2007 = getElementType(
			"org.eclipse.scava.crossflow.language.diagram.Type_2007"); //$NON-NLS-1$

	/**
	* @generated
	*/
	public static final IElementType Field_2008 = getElementType(
			"org.eclipse.scava.crossflow.language.diagram.Field_2008"); //$NON-NLS-1$

	/**
	* @generated
	*/
	public static final IElementType Field_3001 = getElementType(
			"org.eclipse.scava.crossflow.language.diagram.Field_3001"); //$NON-NLS-1$

	/**
	* @generated
	*/
	public static final IElementType StreamType_4001 = getElementType(
			"org.eclipse.scava.crossflow.language.diagram.StreamType_4001"); //$NON-NLS-1$

	/**
	* @generated
	*/
	public static final IElementType TaskInput_4002 = getElementType(
			"org.eclipse.scava.crossflow.language.diagram.TaskInput_4002"); //$NON-NLS-1$

	/**
	* @generated
	*/
	public static final IElementType TaskOutput_4003 = getElementType(
			"org.eclipse.scava.crossflow.language.diagram.TaskOutput_4003"); //$NON-NLS-1$

	/**
	* @generated
	*/
	public static final IElementType TypeExtending_4004 = getElementType(
			"org.eclipse.scava.crossflow.language.diagram.TypeExtending_4004"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static ImageDescriptor getImageDescriptor(ENamedElement element) {
		return elementTypeImages.getImageDescriptor(element);
	}

	/**
	 * @generated
	 */
	public static Image getImage(ENamedElement element) {
		return elementTypeImages.getImage(element);
	}

	/**
	 * @generated
	 */
	public static ImageDescriptor getImageDescriptor(IAdaptable hint) {
		return getImageDescriptor(getElement(hint));
	}

	/**
	 * @generated
	 */
	public static Image getImage(IAdaptable hint) {
		return getImage(getElement(hint));
	}

	/**
	 * Returns 'type' of the ecore object associated with the hint.
	 * 
	 * @generated
	 */
	public static ENamedElement getElement(IAdaptable hint) {
		Object type = hint.getAdapter(IElementType.class);
		if (elements == null) {
			elements = new IdentityHashMap<IElementType, ENamedElement>();

			elements.put(Workflow_1000, CrossflowPackage.eINSTANCE.getWorkflow());

			elements.put(Topic_2001, CrossflowPackage.eINSTANCE.getTopic());

			elements.put(Queue_2002, CrossflowPackage.eINSTANCE.getQueue());

			elements.put(Source_2003, CrossflowPackage.eINSTANCE.getSource());

			elements.put(Sink_2004, CrossflowPackage.eINSTANCE.getSink());

			elements.put(Configuration_2005, CrossflowPackage.eINSTANCE.getConfiguration());

			elements.put(Task_2006, CrossflowPackage.eINSTANCE.getTask());

			elements.put(Type_2007, CrossflowPackage.eINSTANCE.getType());

			elements.put(Field_2008, CrossflowPackage.eINSTANCE.getField());

			elements.put(Field_3001, CrossflowPackage.eINSTANCE.getField());

			elements.put(StreamType_4001, CrossflowPackage.eINSTANCE.getStream_Type());

			elements.put(TaskInput_4002, CrossflowPackage.eINSTANCE.getTask_Input());

			elements.put(TaskOutput_4003, CrossflowPackage.eINSTANCE.getTask_Output());

			elements.put(TypeExtending_4004, CrossflowPackage.eINSTANCE.getType_Extending());
		}
		return (ENamedElement) elements.get(type);
	}

	/**
	 * @generated
	 */
	private static IElementType getElementType(String id) {
		return ElementTypeRegistry.getInstance().getType(id);
	}

	/**
	 * @generated
	 */
	public static boolean isKnownElementType(IElementType elementType) {
		if (KNOWN_ELEMENT_TYPES == null) {
			KNOWN_ELEMENT_TYPES = new HashSet<IElementType>();
			KNOWN_ELEMENT_TYPES.add(Workflow_1000);
			KNOWN_ELEMENT_TYPES.add(Topic_2001);
			KNOWN_ELEMENT_TYPES.add(Queue_2002);
			KNOWN_ELEMENT_TYPES.add(Source_2003);
			KNOWN_ELEMENT_TYPES.add(Sink_2004);
			KNOWN_ELEMENT_TYPES.add(Configuration_2005);
			KNOWN_ELEMENT_TYPES.add(Task_2006);
			KNOWN_ELEMENT_TYPES.add(Type_2007);
			KNOWN_ELEMENT_TYPES.add(Field_2008);
			KNOWN_ELEMENT_TYPES.add(Field_3001);
			KNOWN_ELEMENT_TYPES.add(StreamType_4001);
			KNOWN_ELEMENT_TYPES.add(TaskInput_4002);
			KNOWN_ELEMENT_TYPES.add(TaskOutput_4003);
			KNOWN_ELEMENT_TYPES.add(TypeExtending_4004);
		}
		return KNOWN_ELEMENT_TYPES.contains(elementType);
	}

	/**
	 * @generated
	 */
	public static IElementType getElementType(int visualID) {
		switch (visualID) {
		case WorkflowEditPart.VISUAL_ID:
			return Workflow_1000;
		case TopicEditPart.VISUAL_ID:
			return Topic_2001;
		case QueueEditPart.VISUAL_ID:
			return Queue_2002;
		case SourceEditPart.VISUAL_ID:
			return Source_2003;
		case SinkEditPart.VISUAL_ID:
			return Sink_2004;
		case ConfigurationEditPart.VISUAL_ID:
			return Configuration_2005;
		case TaskEditPart.VISUAL_ID:
			return Task_2006;
		case TypeEditPart.VISUAL_ID:
			return Type_2007;
		case FieldEditPart.VISUAL_ID:
			return Field_2008;
		case Field2EditPart.VISUAL_ID:
			return Field_3001;
		case StreamTypeEditPart.VISUAL_ID:
			return StreamType_4001;
		case TaskInputEditPart.VISUAL_ID:
			return TaskInput_4002;
		case TaskOutputEditPart.VISUAL_ID:
			return TaskOutput_4003;
		case TypeExtendingEditPart.VISUAL_ID:
			return TypeExtending_4004;
		}
		return null;
	}

	/**
	 * @generated
	 */
	public static final DiagramElementTypes TYPED_INSTANCE = new DiagramElementTypes(elementTypeImages) {

		/**
		* @generated
		*/
		@Override

		public boolean isKnownElementType(IElementType elementType) {
			return crossflow.diagram.providers.CrossflowElementTypes.isKnownElementType(elementType);
		}

		/**
		* @generated
		*/
		@Override

		public IElementType getElementTypeForVisualId(int visualID) {
			return crossflow.diagram.providers.CrossflowElementTypes.getElementType(visualID);
		}

		/**
		* @generated
		*/
		@Override

		public ENamedElement getDefiningNamedElement(IAdaptable elementTypeAdapter) {
			return crossflow.diagram.providers.CrossflowElementTypes.getElement(elementTypeAdapter);
		}
	};

}
