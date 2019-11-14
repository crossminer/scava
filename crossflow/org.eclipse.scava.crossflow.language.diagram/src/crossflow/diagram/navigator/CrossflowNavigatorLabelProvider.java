/*
* 
*/
package crossflow.diagram.navigator;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gmf.runtime.common.ui.services.parser.CommonParserHint;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserOptions;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.ITreePathLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.ViewerLabel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.navigator.ICommonContentExtensionSite;
import org.eclipse.ui.navigator.ICommonLabelProvider;

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
import crossflow.diagram.edit.parts.LanguageNameEditPart;
import crossflow.diagram.edit.parts.OpinionatedTaskEditPart;
import crossflow.diagram.edit.parts.OpinionatedTaskNameEditPart;
import crossflow.diagram.edit.parts.Parameter2EditPart;
import crossflow.diagram.edit.parts.ParameterEditPart;
import crossflow.diagram.edit.parts.ParameterNameValue2EditPart;
import crossflow.diagram.edit.parts.ParameterNameValueEditPart;
import crossflow.diagram.edit.parts.QueueEditPart;
import crossflow.diagram.edit.parts.QueueNameEditPart;
import crossflow.diagram.edit.parts.ReusableComponentEditPart;
import crossflow.diagram.edit.parts.ReusableComponentNameEditPart;
import crossflow.diagram.edit.parts.ScriptedTaskEditPart;
import crossflow.diagram.edit.parts.ScriptedTaskNameEditPart;
import crossflow.diagram.edit.parts.SerialiserEditPart;
import crossflow.diagram.edit.parts.SerialiserNameEditPart;
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
import crossflow.diagram.edit.parts.WorkflowEditPart;
import crossflow.diagram.part.CrossflowDiagramEditorPlugin;
import crossflow.diagram.part.CrossflowVisualIDRegistry;
import crossflow.diagram.providers.CrossflowElementTypes;
import crossflow.diagram.providers.CrossflowParserProvider;

/**
 * @generated
 */
public class CrossflowNavigatorLabelProvider extends LabelProvider
		implements ICommonLabelProvider, ITreePathLabelProvider {

	/**
	* @generated
	*/
	static {
		CrossflowDiagramEditorPlugin.getInstance().getImageRegistry().put("Navigator?UnknownElement", //$NON-NLS-1$
				ImageDescriptor.getMissingImageDescriptor());
		CrossflowDiagramEditorPlugin.getInstance().getImageRegistry().put("Navigator?ImageNotFound", //$NON-NLS-1$
				ImageDescriptor.getMissingImageDescriptor());
	}

	/**
	* @generated
	*/
	public void updateLabel(ViewerLabel label, TreePath elementPath) {
		Object element = elementPath.getLastSegment();
		if (element instanceof CrossflowNavigatorItem && !isOwnView(((CrossflowNavigatorItem) element).getView())) {
			return;
		}
		label.setText(getText(element));
		label.setImage(getImage(element));
	}

	/**
	* @generated
	*/
	public Image getImage(Object element) {
		if (element instanceof CrossflowNavigatorGroup) {
			CrossflowNavigatorGroup group = (CrossflowNavigatorGroup) element;
			return CrossflowDiagramEditorPlugin.getInstance().getBundledImage(group.getIcon());
		}

		if (element instanceof CrossflowNavigatorItem) {
			CrossflowNavigatorItem navigatorItem = (CrossflowNavigatorItem) element;
			if (!isOwnView(navigatorItem.getView())) {
				return super.getImage(element);
			}
			return getImage(navigatorItem.getView());
		}

		// Due to plugin.xml content will be called only for "own" views
		if (element instanceof IAdaptable) {
			View view = (View) ((IAdaptable) element).getAdapter(View.class);
			if (view != null && isOwnView(view)) {
				return getImage(view);
			}
		}

		return super.getImage(element);
	}

	/**
	* @generated
	*/
	public Image getImage(View view) {
		switch (CrossflowVisualIDRegistry.getVisualID(view)) {
		case WorkflowEditPart.VISUAL_ID:
			return getImage("Navigator?Diagram?org.eclipse.scava.crossflow?Workflow", //$NON-NLS-1$
					CrossflowElementTypes.Workflow_1000);
		case CsvSourceEditPart.VISUAL_ID:
			return getImage("Navigator?TopLevelNode?org.eclipse.scava.crossflow?CsvSource", //$NON-NLS-1$
					CrossflowElementTypes.CsvSource_2001);
		case CsvSinkEditPart.VISUAL_ID:
			return getImage("Navigator?TopLevelNode?org.eclipse.scava.crossflow?CsvSink", //$NON-NLS-1$
					CrossflowElementTypes.CsvSink_2002);
		case TopicEditPart.VISUAL_ID:
			return getImage("Navigator?TopLevelNode?org.eclipse.scava.crossflow?Topic", //$NON-NLS-1$
					CrossflowElementTypes.Topic_2003);
		case QueueEditPart.VISUAL_ID:
			return getImage("Navigator?TopLevelNode?org.eclipse.scava.crossflow?Queue", //$NON-NLS-1$
					CrossflowElementTypes.Queue_2004);
		case SourceEditPart.VISUAL_ID:
			return getImage("Navigator?TopLevelNode?org.eclipse.scava.crossflow?Source", //$NON-NLS-1$
					CrossflowElementTypes.Source_2005);
		case SinkEditPart.VISUAL_ID:
			return getImage("Navigator?TopLevelNode?org.eclipse.scava.crossflow?Sink", CrossflowElementTypes.Sink_2006); //$NON-NLS-1$
		case CommitmentTaskEditPart.VISUAL_ID:
			return getImage("Navigator?TopLevelNode?org.eclipse.scava.crossflow?CommitmentTask", //$NON-NLS-1$
					CrossflowElementTypes.CommitmentTask_2007);
		case OpinionatedTaskEditPart.VISUAL_ID:
			return getImage("Navigator?TopLevelNode?org.eclipse.scava.crossflow?OpinionatedTask", //$NON-NLS-1$
					CrossflowElementTypes.OpinionatedTask_2008);
		case TaskEditPart.VISUAL_ID:
			return getImage("Navigator?TopLevelNode?org.eclipse.scava.crossflow?Task", CrossflowElementTypes.Task_2010); //$NON-NLS-1$
		case TypeEditPart.VISUAL_ID:
			return getImage("Navigator?TopLevelNode?org.eclipse.scava.crossflow?Type", CrossflowElementTypes.Type_2011); //$NON-NLS-1$
		case LanguageEditPart.VISUAL_ID:
			return getImage("Navigator?TopLevelNode?org.eclipse.scava.crossflow?Language", //$NON-NLS-1$
					CrossflowElementTypes.Language_2013);
		case FieldEditPart.VISUAL_ID:
			return getImage("Navigator?TopLevelNode?org.eclipse.scava.crossflow?Field", //$NON-NLS-1$
					CrossflowElementTypes.Field_2014);
		case ScriptedTaskEditPart.VISUAL_ID:
			return getImage("Navigator?TopLevelNode?org.eclipse.scava.crossflow?ScriptedTask", //$NON-NLS-1$
					CrossflowElementTypes.ScriptedTask_2015);
		case SerialiserEditPart.VISUAL_ID:
			return getImage("Navigator?TopLevelNode?org.eclipse.scava.crossflow?Serialiser", //$NON-NLS-1$
					CrossflowElementTypes.Serialiser_2016);
		case ReusableComponentEditPart.VISUAL_ID:
			return getImage("Navigator?TopLevelNode?org.eclipse.scava.crossflow?ReusableComponent", //$NON-NLS-1$
					CrossflowElementTypes.ReusableComponent_2017);
		case Field3EditPart.VISUAL_ID:
			return getImage("Navigator?Node?org.eclipse.scava.crossflow?Field", CrossflowElementTypes.Field_3001); //$NON-NLS-1$
		case ParameterEditPart.VISUAL_ID:
			return getImage("Navigator?Node?org.eclipse.scava.crossflow?Parameter", //$NON-NLS-1$
					CrossflowElementTypes.Parameter_3002);
		case Field2EditPart.VISUAL_ID:
			return getImage("Navigator?Node?org.eclipse.scava.crossflow?Field", CrossflowElementTypes.Field_3003); //$NON-NLS-1$
		case Parameter2EditPart.VISUAL_ID:
			return getImage("Navigator?Node?org.eclipse.scava.crossflow?Parameter", //$NON-NLS-1$
					CrossflowElementTypes.Parameter_3004);
		case StreamTypeEditPart.VISUAL_ID:
			return getImage("Navigator?Link?org.eclipse.scava.crossflow?Stream?type", //$NON-NLS-1$
					CrossflowElementTypes.StreamType_4001);
		case TaskOutputEditPart.VISUAL_ID:
			return getImage("Navigator?Link?org.eclipse.scava.crossflow?Task?output", //$NON-NLS-1$
					CrossflowElementTypes.TaskOutput_4003);
		case TypeExtendingEditPart.VISUAL_ID:
			return getImage("Navigator?Link?org.eclipse.scava.crossflow?Type?extending", //$NON-NLS-1$
					CrossflowElementTypes.TypeExtending_4004);
		case StreamInputOfEditPart.VISUAL_ID:
			return getImage("Navigator?Link?org.eclipse.scava.crossflow?Stream?inputOf", //$NON-NLS-1$
					CrossflowElementTypes.StreamInputOf_4005);
		}
		return getImage("Navigator?UnknownElement", null); //$NON-NLS-1$
	}

	/**
	* @generated
	*/
	private Image getImage(String key, IElementType elementType) {
		ImageRegistry imageRegistry = CrossflowDiagramEditorPlugin.getInstance().getImageRegistry();
		Image image = imageRegistry.get(key);
		if (image == null && elementType != null && CrossflowElementTypes.isKnownElementType(elementType)) {
			image = CrossflowElementTypes.getImage(elementType);
			imageRegistry.put(key, image);
		}

		if (image == null) {
			image = imageRegistry.get("Navigator?ImageNotFound"); //$NON-NLS-1$
			imageRegistry.put(key, image);
		}
		return image;
	}

	/**
	* @generated
	*/
	public String getText(Object element) {
		if (element instanceof CrossflowNavigatorGroup) {
			CrossflowNavigatorGroup group = (CrossflowNavigatorGroup) element;
			return group.getGroupName();
		}

		if (element instanceof CrossflowNavigatorItem) {
			CrossflowNavigatorItem navigatorItem = (CrossflowNavigatorItem) element;
			if (!isOwnView(navigatorItem.getView())) {
				return null;
			}
			return getText(navigatorItem.getView());
		}

		// Due to plugin.xml content will be called only for "own" views
		if (element instanceof IAdaptable) {
			View view = (View) ((IAdaptable) element).getAdapter(View.class);
			if (view != null && isOwnView(view)) {
				return getText(view);
			}
		}

		return super.getText(element);
	}

	/**
	* @generated
	*/
	public String getText(View view) {
		if (view.getElement() != null && view.getElement().eIsProxy()) {
			return getUnresolvedDomainElementProxyText(view);
		}
		switch (CrossflowVisualIDRegistry.getVisualID(view)) {
		case WorkflowEditPart.VISUAL_ID:
			return getWorkflow_1000Text(view);
		case CsvSourceEditPart.VISUAL_ID:
			return getCsvSource_2001Text(view);
		case CsvSinkEditPart.VISUAL_ID:
			return getCsvSink_2002Text(view);
		case TopicEditPart.VISUAL_ID:
			return getTopic_2003Text(view);
		case QueueEditPart.VISUAL_ID:
			return getQueue_2004Text(view);
		case SourceEditPart.VISUAL_ID:
			return getSource_2005Text(view);
		case SinkEditPart.VISUAL_ID:
			return getSink_2006Text(view);
		case CommitmentTaskEditPart.VISUAL_ID:
			return getCommitmentTask_2007Text(view);
		case OpinionatedTaskEditPart.VISUAL_ID:
			return getOpinionatedTask_2008Text(view);
		case TaskEditPart.VISUAL_ID:
			return getTask_2010Text(view);
		case TypeEditPart.VISUAL_ID:
			return getType_2011Text(view);
		case LanguageEditPart.VISUAL_ID:
			return getLanguage_2013Text(view);
		case FieldEditPart.VISUAL_ID:
			return getField_2014Text(view);
		case ScriptedTaskEditPart.VISUAL_ID:
			return getScriptedTask_2015Text(view);
		case SerialiserEditPart.VISUAL_ID:
			return getSerialiser_2016Text(view);
		case ReusableComponentEditPart.VISUAL_ID:
			return getReusableComponent_2017Text(view);
		case Field3EditPart.VISUAL_ID:
			return getField_3001Text(view);
		case ParameterEditPart.VISUAL_ID:
			return getParameter_3002Text(view);
		case Field2EditPart.VISUAL_ID:
			return getField_3003Text(view);
		case Parameter2EditPart.VISUAL_ID:
			return getParameter_3004Text(view);
		case StreamTypeEditPart.VISUAL_ID:
			return getStreamType_4001Text(view);
		case TaskOutputEditPart.VISUAL_ID:
			return getTaskOutput_4003Text(view);
		case TypeExtendingEditPart.VISUAL_ID:
			return getTypeExtending_4004Text(view);
		case StreamInputOfEditPart.VISUAL_ID:
			return getStreamInputOf_4005Text(view);
		}
		return getUnknownElementText(view);
	}

	/**
	* @generated
	*/
	private String getWorkflow_1000Text(View view) {
		Workflow domainModelElement = (Workflow) view.getElement();
		if (domainModelElement != null) {
			return domainModelElement.getName();
		} else {
			CrossflowDiagramEditorPlugin.getInstance().logError("No domain element for view with visualID = " + 1000); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	* @generated
	*/
	private String getCsvSource_2001Text(View view) {
		IParser parser = CrossflowParserProvider.getParser(CrossflowElementTypes.CsvSource_2001,
				view.getElement() != null ? view.getElement() : view,
				CrossflowVisualIDRegistry.getType(CsvSourceNameEditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			CrossflowDiagramEditorPlugin.getInstance().logError("Parser was not found for label " + 5001); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	* @generated
	*/
	private String getCsvSink_2002Text(View view) {
		IParser parser = CrossflowParserProvider.getParser(CrossflowElementTypes.CsvSink_2002,
				view.getElement() != null ? view.getElement() : view,
				CrossflowVisualIDRegistry.getType(CsvSinkNameEditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			CrossflowDiagramEditorPlugin.getInstance().logError("Parser was not found for label " + 5002); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	* @generated
	*/
	private String getTopic_2003Text(View view) {
		IParser parser = CrossflowParserProvider.getParser(CrossflowElementTypes.Topic_2003,
				view.getElement() != null ? view.getElement() : view,
				CrossflowVisualIDRegistry.getType(TopicNameEditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			CrossflowDiagramEditorPlugin.getInstance().logError("Parser was not found for label " + 5003); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	* @generated
	*/
	private String getQueue_2004Text(View view) {
		IParser parser = CrossflowParserProvider.getParser(CrossflowElementTypes.Queue_2004,
				view.getElement() != null ? view.getElement() : view,
				CrossflowVisualIDRegistry.getType(QueueNameEditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			CrossflowDiagramEditorPlugin.getInstance().logError("Parser was not found for label " + 5004); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	* @generated
	*/
	private String getSource_2005Text(View view) {
		IParser parser = CrossflowParserProvider.getParser(CrossflowElementTypes.Source_2005,
				view.getElement() != null ? view.getElement() : view,
				CrossflowVisualIDRegistry.getType(SourceNameEditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			CrossflowDiagramEditorPlugin.getInstance().logError("Parser was not found for label " + 5005); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	* @generated
	*/
	private String getSink_2006Text(View view) {
		IParser parser = CrossflowParserProvider.getParser(CrossflowElementTypes.Sink_2006,
				view.getElement() != null ? view.getElement() : view,
				CrossflowVisualIDRegistry.getType(SinkNameEditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			CrossflowDiagramEditorPlugin.getInstance().logError("Parser was not found for label " + 5006); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	* @generated
	*/
	private String getCommitmentTask_2007Text(View view) {
		IParser parser = CrossflowParserProvider.getParser(CrossflowElementTypes.CommitmentTask_2007,
				view.getElement() != null ? view.getElement() : view,
				CrossflowVisualIDRegistry.getType(CommitmentTaskNameEditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			CrossflowDiagramEditorPlugin.getInstance().logError("Parser was not found for label " + 5007); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	* @generated
	*/
	private String getOpinionatedTask_2008Text(View view) {
		IParser parser = CrossflowParserProvider.getParser(CrossflowElementTypes.OpinionatedTask_2008,
				view.getElement() != null ? view.getElement() : view,
				CrossflowVisualIDRegistry.getType(OpinionatedTaskNameEditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			CrossflowDiagramEditorPlugin.getInstance().logError("Parser was not found for label " + 5008); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	* @generated
	*/
	private String getTask_2010Text(View view) {
		IParser parser = CrossflowParserProvider.getParser(CrossflowElementTypes.Task_2010,
				view.getElement() != null ? view.getElement() : view,
				CrossflowVisualIDRegistry.getType(TaskNameEditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			CrossflowDiagramEditorPlugin.getInstance().logError("Parser was not found for label " + 5010); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	* @generated
	*/
	private String getType_2011Text(View view) {
		IParser parser = CrossflowParserProvider.getParser(CrossflowElementTypes.Type_2011,
				view.getElement() != null ? view.getElement() : view,
				CrossflowVisualIDRegistry.getType(TypeNameEditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			CrossflowDiagramEditorPlugin.getInstance().logError("Parser was not found for label " + 5012); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	* @generated
	*/
	private String getLanguage_2013Text(View view) {
		IParser parser = CrossflowParserProvider.getParser(CrossflowElementTypes.Language_2013,
				view.getElement() != null ? view.getElement() : view,
				CrossflowVisualIDRegistry.getType(LanguageNameEditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			CrossflowDiagramEditorPlugin.getInstance().logError("Parser was not found for label " + 5015); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	* @generated
	*/
	private String getField_2014Text(View view) {
		IParser parser = CrossflowParserProvider.getParser(CrossflowElementTypes.Field_2014,
				view.getElement() != null ? view.getElement() : view,
				CrossflowVisualIDRegistry.getType(FieldNameTypeEditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			CrossflowDiagramEditorPlugin.getInstance().logError("Parser was not found for label " + 5016); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	* @generated
	*/
	private String getScriptedTask_2015Text(View view) {
		IParser parser = CrossflowParserProvider.getParser(CrossflowElementTypes.ScriptedTask_2015,
				view.getElement() != null ? view.getElement() : view,
				CrossflowVisualIDRegistry.getType(ScriptedTaskNameEditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			CrossflowDiagramEditorPlugin.getInstance().logError("Parser was not found for label " + 5017); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	* @generated
	*/
	private String getSerialiser_2016Text(View view) {
		IParser parser = CrossflowParserProvider.getParser(CrossflowElementTypes.Serialiser_2016,
				view.getElement() != null ? view.getElement() : view,
				CrossflowVisualIDRegistry.getType(SerialiserNameEditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			CrossflowDiagramEditorPlugin.getInstance().logError("Parser was not found for label " + 5020); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	* @generated
	*/
	private String getReusableComponent_2017Text(View view) {
		IParser parser = CrossflowParserProvider.getParser(CrossflowElementTypes.ReusableComponent_2017,
				view.getElement() != null ? view.getElement() : view,
				CrossflowVisualIDRegistry.getType(ReusableComponentNameEditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			CrossflowDiagramEditorPlugin.getInstance().logError("Parser was not found for label " + 5021); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	* @generated
	*/
	private String getField_3001Text(View view) {
		IParser parser = CrossflowParserProvider.getParser(CrossflowElementTypes.Field_3001,
				view.getElement() != null ? view.getElement() : view,
				CrossflowVisualIDRegistry.getType(FieldNameType3EditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			CrossflowDiagramEditorPlugin.getInstance().logError("Parser was not found for label " + 5011); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	* @generated
	*/
	private String getParameter_3002Text(View view) {
		IParser parser = CrossflowParserProvider.getParser(CrossflowElementTypes.Parameter_3002,
				view.getElement() != null ? view.getElement() : view,
				CrossflowVisualIDRegistry.getType(ParameterNameValueEditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			CrossflowDiagramEditorPlugin.getInstance().logError("Parser was not found for label " + 5014); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	* @generated
	*/
	private String getField_3003Text(View view) {
		IParser parser = CrossflowParserProvider.getParser(CrossflowElementTypes.Field_3003,
				view.getElement() != null ? view.getElement() : view,
				CrossflowVisualIDRegistry.getType(FieldNameType2EditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			CrossflowDiagramEditorPlugin.getInstance().logError("Parser was not found for label " + 5018); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	* @generated
	*/
	private String getParameter_3004Text(View view) {
		IParser parser = CrossflowParserProvider.getParser(CrossflowElementTypes.Parameter_3004,
				view.getElement() != null ? view.getElement() : view,
				CrossflowVisualIDRegistry.getType(ParameterNameValue2EditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			CrossflowDiagramEditorPlugin.getInstance().logError("Parser was not found for label " + 5019); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	* @generated
	*/
	private String getStreamType_4001Text(View view) {
		IParser parser = CrossflowParserProvider.getParser(CrossflowElementTypes.StreamType_4001,
				view.getElement() != null ? view.getElement() : view, CommonParserHint.DESCRIPTION);
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			CrossflowDiagramEditorPlugin.getInstance().logError("Parser was not found for label " + 6001); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	* @generated
	*/
	private String getTaskOutput_4003Text(View view) {
		IParser parser = CrossflowParserProvider.getParser(CrossflowElementTypes.TaskOutput_4003,
				view.getElement() != null ? view.getElement() : view, CommonParserHint.DESCRIPTION);
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			CrossflowDiagramEditorPlugin.getInstance().logError("Parser was not found for label " + 6003); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	* @generated
	*/
	private String getTypeExtending_4004Text(View view) {
		IParser parser = CrossflowParserProvider.getParser(CrossflowElementTypes.TypeExtending_4004,
				view.getElement() != null ? view.getElement() : view, CommonParserHint.DESCRIPTION);
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			CrossflowDiagramEditorPlugin.getInstance().logError("Parser was not found for label " + 6004); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	* @generated
	*/
	private String getStreamInputOf_4005Text(View view) {
		IParser parser = CrossflowParserProvider.getParser(CrossflowElementTypes.StreamInputOf_4005,
				view.getElement() != null ? view.getElement() : view, CommonParserHint.DESCRIPTION);
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			CrossflowDiagramEditorPlugin.getInstance().logError("Parser was not found for label " + 6005); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	* @generated
	*/
	private String getUnknownElementText(View view) {
		return "<UnknownElement Visual_ID = " + view.getType() + ">"; //$NON-NLS-1$  //$NON-NLS-2$
	}

	/**
	* @generated
	*/
	private String getUnresolvedDomainElementProxyText(View view) {
		return "<Unresolved domain element Visual_ID = " + view.getType() + ">"; //$NON-NLS-1$  //$NON-NLS-2$
	}

	/**
	* @generated
	*/
	public void init(ICommonContentExtensionSite aConfig) {
	}

	/**
	* @generated
	*/
	public void restoreState(IMemento aMemento) {
	}

	/**
	* @generated
	*/
	public void saveState(IMemento aMemento) {
	}

	/**
	* @generated
	*/
	public String getDescription(Object anElement) {
		return null;
	}

	/**
	* @generated
	*/
	private boolean isOwnView(View view) {
		return WorkflowEditPart.MODEL_ID.equals(CrossflowVisualIDRegistry.getModelID(view));
	}

}
