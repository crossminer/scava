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

import crossflow.Queue;
import crossflow.Topic;
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
		case TopicEditPart.VISUAL_ID:
			return getImage("Navigator?TopLevelNode?org.eclipse.scava.crossflow?Topic", //$NON-NLS-1$
					CrossflowElementTypes.Topic_2001);
		case QueueEditPart.VISUAL_ID:
			return getImage("Navigator?TopLevelNode?org.eclipse.scava.crossflow?Queue", //$NON-NLS-1$
					CrossflowElementTypes.Queue_2002);
		case SourceEditPart.VISUAL_ID:
			return getImage("Navigator?TopLevelNode?org.eclipse.scava.crossflow?Source", //$NON-NLS-1$
					CrossflowElementTypes.Source_2003);
		case SinkEditPart.VISUAL_ID:
			return getImage("Navigator?TopLevelNode?org.eclipse.scava.crossflow?Sink", CrossflowElementTypes.Sink_2004); //$NON-NLS-1$
		case ConfigurationEditPart.VISUAL_ID:
			return getImage("Navigator?TopLevelNode?org.eclipse.scava.crossflow?Configuration", //$NON-NLS-1$
					CrossflowElementTypes.Configuration_2005);
		case TaskEditPart.VISUAL_ID:
			return getImage("Navigator?TopLevelNode?org.eclipse.scava.crossflow?Task", CrossflowElementTypes.Task_2006); //$NON-NLS-1$
		case TypeEditPart.VISUAL_ID:
			return getImage("Navigator?TopLevelNode?org.eclipse.scava.crossflow?Type", CrossflowElementTypes.Type_2007); //$NON-NLS-1$
		case FieldEditPart.VISUAL_ID:
			return getImage("Navigator?TopLevelNode?org.eclipse.scava.crossflow?Field", //$NON-NLS-1$
					CrossflowElementTypes.Field_2008);
		case Field2EditPart.VISUAL_ID:
			return getImage("Navigator?Node?org.eclipse.scava.crossflow?Field", CrossflowElementTypes.Field_3001); //$NON-NLS-1$
		case StreamTypeEditPart.VISUAL_ID:
			return getImage("Navigator?Link?org.eclipse.scava.crossflow?Stream?type", //$NON-NLS-1$
					CrossflowElementTypes.StreamType_4001);
		case TaskInputEditPart.VISUAL_ID:
			return getImage("Navigator?Link?org.eclipse.scava.crossflow?Task?input", //$NON-NLS-1$
					CrossflowElementTypes.TaskInput_4002);
		case TaskOutputEditPart.VISUAL_ID:
			return getImage("Navigator?Link?org.eclipse.scava.crossflow?Task?output", //$NON-NLS-1$
					CrossflowElementTypes.TaskOutput_4003);
		case TypeExtendingEditPart.VISUAL_ID:
			return getImage("Navigator?Link?org.eclipse.scava.crossflow?Type?extending", //$NON-NLS-1$
					CrossflowElementTypes.TypeExtending_4004);
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
		case TopicEditPart.VISUAL_ID:
			return getTopic_2001Text(view);
		case QueueEditPart.VISUAL_ID:
			return getQueue_2002Text(view);
		case SourceEditPart.VISUAL_ID:
			return getSource_2003Text(view);
		case SinkEditPart.VISUAL_ID:
			return getSink_2004Text(view);
		case ConfigurationEditPart.VISUAL_ID:
			return getConfiguration_2005Text(view);
		case TaskEditPart.VISUAL_ID:
			return getTask_2006Text(view);
		case TypeEditPart.VISUAL_ID:
			return getType_2007Text(view);
		case FieldEditPart.VISUAL_ID:
			return getField_2008Text(view);
		case Field2EditPart.VISUAL_ID:
			return getField_3001Text(view);
		case StreamTypeEditPart.VISUAL_ID:
			return getStreamType_4001Text(view);
		case TaskInputEditPart.VISUAL_ID:
			return getTaskInput_4002Text(view);
		case TaskOutputEditPart.VISUAL_ID:
			return getTaskOutput_4003Text(view);
		case TypeExtendingEditPart.VISUAL_ID:
			return getTypeExtending_4004Text(view);
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
	private String getTopic_2001Text(View view) {
		Topic domainModelElement = (Topic) view.getElement();
		if (domainModelElement != null) {
			return domainModelElement.getName();
		} else {
			CrossflowDiagramEditorPlugin.getInstance().logError("No domain element for view with visualID = " + 2001); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	* @generated
	*/
	private String getQueue_2002Text(View view) {
		Queue domainModelElement = (Queue) view.getElement();
		if (domainModelElement != null) {
			return domainModelElement.getName();
		} else {
			CrossflowDiagramEditorPlugin.getInstance().logError("No domain element for view with visualID = " + 2002); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	* @generated
	*/
	private String getSource_2003Text(View view) {
		IParser parser = CrossflowParserProvider.getParser(CrossflowElementTypes.Source_2003,
				view.getElement() != null ? view.getElement() : view,
				CrossflowVisualIDRegistry.getType(SourceNameEditPart.VISUAL_ID));
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
	private String getSink_2004Text(View view) {
		IParser parser = CrossflowParserProvider.getParser(CrossflowElementTypes.Sink_2004,
				view.getElement() != null ? view.getElement() : view,
				CrossflowVisualIDRegistry.getType(SinkNameEditPart.VISUAL_ID));
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
	private String getConfiguration_2005Text(View view) {
		IParser parser = CrossflowParserProvider.getParser(CrossflowElementTypes.Configuration_2005,
				view.getElement() != null ? view.getElement() : view,
				CrossflowVisualIDRegistry.getType(ConfigurationNumberOfWorkersIsMasterEditPart.VISUAL_ID));
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
	private String getTask_2006Text(View view) {
		IParser parser = CrossflowParserProvider.getParser(CrossflowElementTypes.Task_2006,
				view.getElement() != null ? view.getElement() : view,
				CrossflowVisualIDRegistry.getType(TaskNameEditPart.VISUAL_ID));
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
	private String getType_2007Text(View view) {
		IParser parser = CrossflowParserProvider.getParser(CrossflowElementTypes.Type_2007,
				view.getElement() != null ? view.getElement() : view,
				CrossflowVisualIDRegistry.getType(TypeNameEditPart.VISUAL_ID));
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
	private String getField_2008Text(View view) {
		IParser parser = CrossflowParserProvider.getParser(CrossflowElementTypes.Field_2008,
				view.getElement() != null ? view.getElement() : view,
				CrossflowVisualIDRegistry.getType(FieldNameEditPart.VISUAL_ID));
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
	private String getField_3001Text(View view) {
		IParser parser = CrossflowParserProvider.getParser(CrossflowElementTypes.Field_3001,
				view.getElement() != null ? view.getElement() : view,
				CrossflowVisualIDRegistry.getType(FieldName2EditPart.VISUAL_ID));
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
	private String getTaskInput_4002Text(View view) {
		IParser parser = CrossflowParserProvider.getParser(CrossflowElementTypes.TaskInput_4002,
				view.getElement() != null ? view.getElement() : view, CommonParserHint.DESCRIPTION);
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			CrossflowDiagramEditorPlugin.getInstance().logError("Parser was not found for label " + 6002); //$NON-NLS-1$
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
