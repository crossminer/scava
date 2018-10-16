/*
 * 
 */
package crossflow.diagram.providers;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.GetParserOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserProvider;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserService;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.ui.services.parser.ParserHintAdapter;
import org.eclipse.gmf.runtime.notation.View;

import crossflow.CrossflowPackage;
import crossflow.diagram.edit.parts.ConfigurationNumberOfWorkersIsMasterEditPart;
import crossflow.diagram.edit.parts.FieldName2EditPart;
import crossflow.diagram.edit.parts.FieldNameEditPart;
import crossflow.diagram.edit.parts.SinkNameEditPart;
import crossflow.diagram.edit.parts.SourceNameEditPart;
import crossflow.diagram.edit.parts.TaskNameEditPart;
import crossflow.diagram.edit.parts.TypeNameEditPart;
import crossflow.diagram.parsers.MessageFormatParser;
import crossflow.diagram.part.CrossflowVisualIDRegistry;

/**
 * @generated
 */
public class CrossflowParserProvider extends AbstractProvider implements IParserProvider {

	/**
	* @generated
	*/
	private IParser sourceName_5001Parser;

	/**
	* @generated
	*/
	private IParser getSourceName_5001Parser() {
		if (sourceName_5001Parser == null) {
			EAttribute[] features = new EAttribute[] { CrossflowPackage.eINSTANCE.getTask_Name() };
			MessageFormatParser parser = new MessageFormatParser(features);
			sourceName_5001Parser = parser;
		}
		return sourceName_5001Parser;
	}

	/**
	* @generated
	*/
	private IParser sinkName_5002Parser;

	/**
	* @generated
	*/
	private IParser getSinkName_5002Parser() {
		if (sinkName_5002Parser == null) {
			EAttribute[] features = new EAttribute[] { CrossflowPackage.eINSTANCE.getTask_Name() };
			MessageFormatParser parser = new MessageFormatParser(features);
			sinkName_5002Parser = parser;
		}
		return sinkName_5002Parser;
	}

	/**
	* @generated
	*/
	private IParser configurationNumberOfWorkersIsMasterAlsoWorker_5003Parser;

	/**
	* @generated
	*/
	private IParser getConfigurationNumberOfWorkersIsMasterAlsoWorker_5003Parser() {
		if (configurationNumberOfWorkersIsMasterAlsoWorker_5003Parser == null) {
			EAttribute[] features = new EAttribute[] { CrossflowPackage.eINSTANCE.getConfiguration_NumberOfWorkers(),
					CrossflowPackage.eINSTANCE.getConfiguration_IsMasterAlsoWorker() };
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("Config: NoW:{0}, iMaW:{1}"); //$NON-NLS-1$
			parser.setEditorPattern("Config: NoW:{0}, iMaW:{1}"); //$NON-NLS-1$
			parser.setEditPattern("Config: NoW:{0}, iMaW:{1}"); //$NON-NLS-1$
			configurationNumberOfWorkersIsMasterAlsoWorker_5003Parser = parser;
		}
		return configurationNumberOfWorkersIsMasterAlsoWorker_5003Parser;
	}

	/**
	* @generated
	*/
	private IParser taskName_5004Parser;

	/**
	* @generated
	*/
	private IParser getTaskName_5004Parser() {
		if (taskName_5004Parser == null) {
			EAttribute[] features = new EAttribute[] { CrossflowPackage.eINSTANCE.getTask_Name() };
			MessageFormatParser parser = new MessageFormatParser(features);
			taskName_5004Parser = parser;
		}
		return taskName_5004Parser;
	}

	/**
	* @generated
	*/
	private IParser typeName_5006Parser;

	/**
	* @generated
	*/
	private IParser getTypeName_5006Parser() {
		if (typeName_5006Parser == null) {
			EAttribute[] features = new EAttribute[] { CrossflowPackage.eINSTANCE.getType_Name() };
			MessageFormatParser parser = new MessageFormatParser(features);
			typeName_5006Parser = parser;
		}
		return typeName_5006Parser;
	}

	/**
	* @generated
	*/
	private IParser fieldName_5007Parser;

	/**
	* @generated
	*/
	private IParser getFieldName_5007Parser() {
		if (fieldName_5007Parser == null) {
			EAttribute[] features = new EAttribute[] { CrossflowPackage.eINSTANCE.getField_Name() };
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}"); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			fieldName_5007Parser = parser;
		}
		return fieldName_5007Parser;
	}

	/**
	* @generated
	*/
	private IParser fieldName_5005Parser;

	/**
	* @generated
	*/
	private IParser getFieldName_5005Parser() {
		if (fieldName_5005Parser == null) {
			EAttribute[] features = new EAttribute[] { CrossflowPackage.eINSTANCE.getField_Name() };
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}"); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			fieldName_5005Parser = parser;
		}
		return fieldName_5005Parser;
	}

	/**
	 * @generated
	 */
	protected IParser getParser(int visualID) {
		switch (visualID) {
		case SourceNameEditPart.VISUAL_ID:
			return getSourceName_5001Parser();
		case SinkNameEditPart.VISUAL_ID:
			return getSinkName_5002Parser();
		case ConfigurationNumberOfWorkersIsMasterEditPart.VISUAL_ID:
			return getConfigurationNumberOfWorkersIsMasterAlsoWorker_5003Parser();
		case TaskNameEditPart.VISUAL_ID:
			return getTaskName_5004Parser();
		case TypeNameEditPart.VISUAL_ID:
			return getTypeName_5006Parser();
		case FieldNameEditPart.VISUAL_ID:
			return getFieldName_5007Parser();
		case FieldName2EditPart.VISUAL_ID:
			return getFieldName_5005Parser();
		}
		return null;
	}

	/**
	 * Utility method that consults ParserService
	 * @generated
	 */
	public static IParser getParser(IElementType type, EObject object, String parserHint) {
		return ParserService.getInstance().getParser(new HintAdapter(type, object, parserHint));
	}

	/**
	 * @generated
	 */
	public IParser getParser(IAdaptable hint) {
		String vid = (String) hint.getAdapter(String.class);
		if (vid != null) {
			return getParser(CrossflowVisualIDRegistry.getVisualID(vid));
		}
		View view = (View) hint.getAdapter(View.class);
		if (view != null) {
			return getParser(CrossflowVisualIDRegistry.getVisualID(view));
		}
		return null;
	}

	/**
	 * @generated
	 */
	public boolean provides(IOperation operation) {
		if (operation instanceof GetParserOperation) {
			IAdaptable hint = ((GetParserOperation) operation).getHint();
			if (CrossflowElementTypes.getElement(hint) == null) {
				return false;
			}
			return getParser(hint) != null;
		}
		return false;
	}

	/**
	 * @generated
	 */
	private static class HintAdapter extends ParserHintAdapter {

		/**
		 * @generated
		 */
		private final IElementType elementType;

		/**
		 * @generated
		 */
		public HintAdapter(IElementType type, EObject object, String parserHint) {
			super(object, parserHint);
			assert type != null;
			elementType = type;
		}

		/**
		 * @generated
		 */
		public Object getAdapter(Class adapter) {
			if (IElementType.class.equals(adapter)) {
				return elementType;
			}
			return super.getAdapter(adapter);
		}
	}

}
