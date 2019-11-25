/*
 * 
 */
package crossflow.diagram.edit.parts;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.tooling.runtime.directedit.locator.CellEditorLocatorAccess;

import crossflow.diagram.part.CrossflowVisualIDRegistry;

/**
 * @generated
 */
public class CrossflowEditPartFactory implements EditPartFactory {

	/**
	* @generated
	*/
	public EditPart createEditPart(EditPart context, Object model) {
		if (model instanceof View) {
			View view = (View) model;
			switch (CrossflowVisualIDRegistry.getVisualID(view)) {

			case WorkflowEditPart.VISUAL_ID:
				return new WorkflowEditPart(view);

			case CsvSourceEditPart.VISUAL_ID:
				return new CsvSourceEditPart(view);

			case CsvSourceNameEditPart.VISUAL_ID:
				return new CsvSourceNameEditPart(view);

			case CsvSinkEditPart.VISUAL_ID:
				return new CsvSinkEditPart(view);

			case CsvSinkNameEditPart.VISUAL_ID:
				return new CsvSinkNameEditPart(view);

			case TopicEditPart.VISUAL_ID:
				return new TopicEditPart(view);

			case TopicNameEditPart.VISUAL_ID:
				return new TopicNameEditPart(view);

			case QueueEditPart.VISUAL_ID:
				return new QueueEditPart(view);

			case QueueNameEditPart.VISUAL_ID:
				return new QueueNameEditPart(view);

			case SourceEditPart.VISUAL_ID:
				return new SourceEditPart(view);

			case SourceNameEditPart.VISUAL_ID:
				return new SourceNameEditPart(view);

			case SinkEditPart.VISUAL_ID:
				return new SinkEditPart(view);

			case SinkNameEditPart.VISUAL_ID:
				return new SinkNameEditPart(view);

			case CommitmentTaskEditPart.VISUAL_ID:
				return new CommitmentTaskEditPart(view);

			case CommitmentTaskNameEditPart.VISUAL_ID:
				return new CommitmentTaskNameEditPart(view);

			case OpinionatedTaskEditPart.VISUAL_ID:
				return new OpinionatedTaskEditPart(view);

			case OpinionatedTaskNameEditPart.VISUAL_ID:
				return new OpinionatedTaskNameEditPart(view);

			case ScriptedTaskEditPart.VISUAL_ID:
				return new ScriptedTaskEditPart(view);

			case ScriptedTaskNameEditPart.VISUAL_ID:
				return new ScriptedTaskNameEditPart(view);

			case ReusableComponentEditPart.VISUAL_ID:
				return new ReusableComponentEditPart(view);

			case ReusableComponentNameEditPart.VISUAL_ID:
				return new ReusableComponentNameEditPart(view);

			case DataFieldEditPart.VISUAL_ID:
				return new DataFieldEditPart(view);

			case DataFieldNameTypeEditPart.VISUAL_ID:
				return new DataFieldNameTypeEditPart(view);

			case EnumFieldEditPart.VISUAL_ID:
				return new EnumFieldEditPart(view);

			case EnumFieldNameEditPart.VISUAL_ID:
				return new EnumFieldNameEditPart(view);

			case TaskEditPart.VISUAL_ID:
				return new TaskEditPart(view);

			case TaskNameEditPart.VISUAL_ID:
				return new TaskNameEditPart(view);

			case TypeEditPart.VISUAL_ID:
				return new TypeEditPart(view);

			case TypeNameEditPart.VISUAL_ID:
				return new TypeNameEditPart(view);

			case LanguageEditPart.VISUAL_ID:
				return new LanguageEditPart(view);

			case LanguageNameEditPart.VISUAL_ID:
				return new LanguageNameEditPart(view);

			case SerializerEditPart.VISUAL_ID:
				return new SerializerEditPart(view);

			case SerializerNameEditPart.VISUAL_ID:
				return new SerializerNameEditPart(view);

			case DataField2EditPart.VISUAL_ID:
				return new DataField2EditPart(view);

			case DataFieldNameType2EditPart.VISUAL_ID:
				return new DataFieldNameType2EditPart(view);

			case EnumField2EditPart.VISUAL_ID:
				return new EnumField2EditPart(view);

			case EnumFieldName2EditPart.VISUAL_ID:
				return new EnumFieldName2EditPart(view);

			case DataField3EditPart.VISUAL_ID:
				return new DataField3EditPart(view);

			case DataFieldNameType3EditPart.VISUAL_ID:
				return new DataFieldNameType3EditPart(view);

			case EnumField3EditPart.VISUAL_ID:
				return new EnumField3EditPart(view);

			case EnumFieldName3EditPart.VISUAL_ID:
				return new EnumFieldName3EditPart(view);

			case ParameterEditPart.VISUAL_ID:
				return new ParameterEditPart(view);

			case ParameterNameValueEditPart.VISUAL_ID:
				return new ParameterNameValueEditPart(view);

			case Parameter2EditPart.VISUAL_ID:
				return new Parameter2EditPart(view);

			case ParameterNameValue2EditPart.VISUAL_ID:
				return new ParameterNameValue2EditPart(view);

			case ScriptedTaskScriptedTaskOutputVariablesCompartmentEditPart.VISUAL_ID:
				return new ScriptedTaskScriptedTaskOutputVariablesCompartmentEditPart(view);

			case TypeTypeFieldsCompartmentEditPart.VISUAL_ID:
				return new TypeTypeFieldsCompartmentEditPart(view);

			case LanguageLanguageParametersCompartmentEditPart.VISUAL_ID:
				return new LanguageLanguageParametersCompartmentEditPart(view);

			case SerializerSerializerParametersCompartmentEditPart.VISUAL_ID:
				return new SerializerSerializerParametersCompartmentEditPart(view);

			case StreamTypeEditPart.VISUAL_ID:
				return new StreamTypeEditPart(view);

			case WrappingLabelEditPart.VISUAL_ID:
				return new WrappingLabelEditPart(view);

			case StreamInputOfEditPart.VISUAL_ID:
				return new StreamInputOfEditPart(view);

			case WrappingLabel2EditPart.VISUAL_ID:
				return new WrappingLabel2EditPart(view);

			case TaskOutputEditPart.VISUAL_ID:
				return new TaskOutputEditPart(view);

			case WrappingLabel3EditPart.VISUAL_ID:
				return new WrappingLabel3EditPart(view);

			case TypeExtendingEditPart.VISUAL_ID:
				return new TypeExtendingEditPart(view);

			case WrappingLabel4EditPart.VISUAL_ID:
				return new WrappingLabel4EditPart(view);

			}
		}
		return createUnrecognizedEditPart(context, model);
	}

	/**
	* @generated
	*/
	private EditPart createUnrecognizedEditPart(EditPart context, Object model) {
		// Handle creation of unrecognized child node EditParts here
		return null;
	}

	/**
	* @generated
	*/
	public static CellEditorLocator getTextCellEditorLocator(ITextAwareEditPart source) {
		return CellEditorLocatorAccess.INSTANCE.getTextCellEditorLocator(source);
	}

}
