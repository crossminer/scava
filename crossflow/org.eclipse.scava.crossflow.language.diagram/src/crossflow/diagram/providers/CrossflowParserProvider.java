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
import crossflow.diagram.edit.parts.CommitmentTaskNameEditPart;
import crossflow.diagram.edit.parts.CsvSinkNameEditPart;
import crossflow.diagram.edit.parts.CsvSourceNameEditPart;
import crossflow.diagram.edit.parts.DataFieldNameType2EditPart;
import crossflow.diagram.edit.parts.DataFieldNameType3EditPart;
import crossflow.diagram.edit.parts.DataFieldNameTypeEditPart;
import crossflow.diagram.edit.parts.EnumFieldName2EditPart;
import crossflow.diagram.edit.parts.EnumFieldName3EditPart;
import crossflow.diagram.edit.parts.EnumFieldNameEditPart;
import crossflow.diagram.edit.parts.LanguageNameEditPart;
import crossflow.diagram.edit.parts.OpinionatedTaskNameEditPart;
import crossflow.diagram.edit.parts.ParameterNameValue2EditPart;
import crossflow.diagram.edit.parts.ParameterNameValueEditPart;
import crossflow.diagram.edit.parts.QueueNameEditPart;
import crossflow.diagram.edit.parts.ReusableComponentNameEditPart;
import crossflow.diagram.edit.parts.ScriptedTaskNameEditPart;
import crossflow.diagram.edit.parts.SerializerNameEditPart;
import crossflow.diagram.edit.parts.SinkNameEditPart;
import crossflow.diagram.edit.parts.SourceNameEditPart;
import crossflow.diagram.edit.parts.TaskNameEditPart;
import crossflow.diagram.edit.parts.TopicNameEditPart;
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
	private IParser csvSourceName_5001Parser;

	/**
	* @generated
	*/
	private IParser getCsvSourceName_5001Parser() {
		if (csvSourceName_5001Parser == null) {
			EAttribute[] features = new EAttribute[] { CrossflowPackage.eINSTANCE.getTask_Name() };
			MessageFormatParser parser = new MessageFormatParser(features);
			csvSourceName_5001Parser = parser;
		}
		return csvSourceName_5001Parser;
	}

	/**
	* @generated
	*/
	private IParser csvSinkName_5002Parser;

	/**
	* @generated
	*/
	private IParser getCsvSinkName_5002Parser() {
		if (csvSinkName_5002Parser == null) {
			EAttribute[] features = new EAttribute[] { CrossflowPackage.eINSTANCE.getTask_Name() };
			MessageFormatParser parser = new MessageFormatParser(features);
			csvSinkName_5002Parser = parser;
		}
		return csvSinkName_5002Parser;
	}

	/**
	* @generated
	*/
	private IParser topicName_5003Parser;

	/**
	* @generated
	*/
	private IParser getTopicName_5003Parser() {
		if (topicName_5003Parser == null) {
			EAttribute[] features = new EAttribute[] { CrossflowPackage.eINSTANCE.getStream_Name() };
			MessageFormatParser parser = new MessageFormatParser(features);
			topicName_5003Parser = parser;
		}
		return topicName_5003Parser;
	}

	/**
	* @generated
	*/
	private IParser queueName_5004Parser;

	/**
	* @generated
	*/
	private IParser getQueueName_5004Parser() {
		if (queueName_5004Parser == null) {
			EAttribute[] features = new EAttribute[] { CrossflowPackage.eINSTANCE.getStream_Name() };
			MessageFormatParser parser = new MessageFormatParser(features);
			queueName_5004Parser = parser;
		}
		return queueName_5004Parser;
	}

	/**
	* @generated
	*/
	private IParser sourceName_5005Parser;

	/**
	* @generated
	*/
	private IParser getSourceName_5005Parser() {
		if (sourceName_5005Parser == null) {
			EAttribute[] features = new EAttribute[] { CrossflowPackage.eINSTANCE.getTask_Name() };
			MessageFormatParser parser = new MessageFormatParser(features);
			sourceName_5005Parser = parser;
		}
		return sourceName_5005Parser;
	}

	/**
	* @generated
	*/
	private IParser sinkName_5006Parser;

	/**
	* @generated
	*/
	private IParser getSinkName_5006Parser() {
		if (sinkName_5006Parser == null) {
			EAttribute[] features = new EAttribute[] { CrossflowPackage.eINSTANCE.getTask_Name() };
			MessageFormatParser parser = new MessageFormatParser(features);
			sinkName_5006Parser = parser;
		}
		return sinkName_5006Parser;
	}

	/**
	* @generated
	*/
	private IParser commitmentTaskName_5007Parser;

	/**
	* @generated
	*/
	private IParser getCommitmentTaskName_5007Parser() {
		if (commitmentTaskName_5007Parser == null) {
			EAttribute[] features = new EAttribute[] { CrossflowPackage.eINSTANCE.getTask_Name() };
			MessageFormatParser parser = new MessageFormatParser(features);
			commitmentTaskName_5007Parser = parser;
		}
		return commitmentTaskName_5007Parser;
	}

	/**
	* @generated
	*/
	private IParser opinionatedTaskName_5008Parser;

	/**
	* @generated
	*/
	private IParser getOpinionatedTaskName_5008Parser() {
		if (opinionatedTaskName_5008Parser == null) {
			EAttribute[] features = new EAttribute[] { CrossflowPackage.eINSTANCE.getTask_Name() };
			MessageFormatParser parser = new MessageFormatParser(features);
			opinionatedTaskName_5008Parser = parser;
		}
		return opinionatedTaskName_5008Parser;
	}

	/**
	* @generated
	*/
	private IParser scriptedTaskName_5017Parser;

	/**
	* @generated
	*/
	private IParser getScriptedTaskName_5017Parser() {
		if (scriptedTaskName_5017Parser == null) {
			EAttribute[] features = new EAttribute[] { CrossflowPackage.eINSTANCE.getTask_Name() };
			MessageFormatParser parser = new MessageFormatParser(features);
			scriptedTaskName_5017Parser = parser;
		}
		return scriptedTaskName_5017Parser;
	}

	/**
	* @generated
	*/
	private IParser reusableComponentName_5021Parser;

	/**
	* @generated
	*/
	private IParser getReusableComponentName_5021Parser() {
		if (reusableComponentName_5021Parser == null) {
			EAttribute[] features = new EAttribute[] { CrossflowPackage.eINSTANCE.getTask_Name() };
			MessageFormatParser parser = new MessageFormatParser(features);
			reusableComponentName_5021Parser = parser;
		}
		return reusableComponentName_5021Parser;
	}

	/**
	* @generated
	*/
	private IParser dataFieldNameType_5026Parser;

	/**
	* @generated
	*/
	private IParser getDataFieldNameType_5026Parser() {
		if (dataFieldNameType_5026Parser == null) {
			EAttribute[] features = new EAttribute[] { CrossflowPackage.eINSTANCE.getField_Name(),
					CrossflowPackage.eINSTANCE.getDataField_Type() };
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}:{1}"); //$NON-NLS-1$
			parser.setEditorPattern("{0}:{1}"); //$NON-NLS-1$
			parser.setEditPattern("{0}:{1}"); //$NON-NLS-1$
			dataFieldNameType_5026Parser = parser;
		}
		return dataFieldNameType_5026Parser;
	}

	/**
	* @generated
	*/
	private IParser enumFieldName_5027Parser;

	/**
	* @generated
	*/
	private IParser getEnumFieldName_5027Parser() {
		if (enumFieldName_5027Parser == null) {
			EAttribute[] features = new EAttribute[] { CrossflowPackage.eINSTANCE.getField_Name() };
			MessageFormatParser parser = new MessageFormatParser(features);
			enumFieldName_5027Parser = parser;
		}
		return enumFieldName_5027Parser;
	}

	/**
	* @generated
	*/
	private IParser taskName_5010Parser;

	/**
	* @generated
	*/
	private IParser getTaskName_5010Parser() {
		if (taskName_5010Parser == null) {
			EAttribute[] features = new EAttribute[] { CrossflowPackage.eINSTANCE.getTask_Name() };
			MessageFormatParser parser = new MessageFormatParser(features);
			taskName_5010Parser = parser;
		}
		return taskName_5010Parser;
	}

	/**
	* @generated
	*/
	private IParser typeName_5012Parser;

	/**
	* @generated
	*/
	private IParser getTypeName_5012Parser() {
		if (typeName_5012Parser == null) {
			EAttribute[] features = new EAttribute[] { CrossflowPackage.eINSTANCE.getType_Name() };
			MessageFormatParser parser = new MessageFormatParser(features);
			typeName_5012Parser = parser;
		}
		return typeName_5012Parser;
	}

	/**
	* @generated
	*/
	private IParser languageName_5015Parser;

	/**
	* @generated
	*/
	private IParser getLanguageName_5015Parser() {
		if (languageName_5015Parser == null) {
			EAttribute[] features = new EAttribute[] { CrossflowPackage.eINSTANCE.getLanguage_Name() };
			MessageFormatParser parser = new MessageFormatParser(features);
			languageName_5015Parser = parser;
		}
		return languageName_5015Parser;
	}

	/**
	* @generated
	*/
	private IParser serializerName_5023Parser;

	/**
	* @generated
	*/
	private IParser getSerializerName_5023Parser() {
		if (serializerName_5023Parser == null) {
			EAttribute[] features = new EAttribute[] { CrossflowPackage.eINSTANCE.getSerializer_Name() };
			MessageFormatParser parser = new MessageFormatParser(features);
			serializerName_5023Parser = parser;
		}
		return serializerName_5023Parser;
	}

	/**
	* @generated
	*/
	private IParser dataFieldNameType_5024Parser;

	/**
	* @generated
	*/
	private IParser getDataFieldNameType_5024Parser() {
		if (dataFieldNameType_5024Parser == null) {
			EAttribute[] features = new EAttribute[] { CrossflowPackage.eINSTANCE.getField_Name(),
					CrossflowPackage.eINSTANCE.getDataField_Type() };
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}:{1}"); //$NON-NLS-1$
			parser.setEditorPattern("{0}:{1}"); //$NON-NLS-1$
			parser.setEditPattern("{0}:{1}"); //$NON-NLS-1$
			dataFieldNameType_5024Parser = parser;
		}
		return dataFieldNameType_5024Parser;
	}

	/**
	* @generated
	*/
	private IParser enumFieldName_5025Parser;

	/**
	* @generated
	*/
	private IParser getEnumFieldName_5025Parser() {
		if (enumFieldName_5025Parser == null) {
			EAttribute[] features = new EAttribute[] { CrossflowPackage.eINSTANCE.getField_Name() };
			MessageFormatParser parser = new MessageFormatParser(features);
			enumFieldName_5025Parser = parser;
		}
		return enumFieldName_5025Parser;
	}

	/**
	* @generated
	*/
	private IParser dataFieldNameType_5028Parser;

	/**
	* @generated
	*/
	private IParser getDataFieldNameType_5028Parser() {
		if (dataFieldNameType_5028Parser == null) {
			EAttribute[] features = new EAttribute[] { CrossflowPackage.eINSTANCE.getField_Name(),
					CrossflowPackage.eINSTANCE.getDataField_Type() };
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}:{1}"); //$NON-NLS-1$
			parser.setEditorPattern("{0}:{1}"); //$NON-NLS-1$
			parser.setEditPattern("{0}:{1}"); //$NON-NLS-1$
			dataFieldNameType_5028Parser = parser;
		}
		return dataFieldNameType_5028Parser;
	}

	/**
	* @generated
	*/
	private IParser enumFieldName_5029Parser;

	/**
	* @generated
	*/
	private IParser getEnumFieldName_5029Parser() {
		if (enumFieldName_5029Parser == null) {
			EAttribute[] features = new EAttribute[] { CrossflowPackage.eINSTANCE.getField_Name() };
			MessageFormatParser parser = new MessageFormatParser(features);
			enumFieldName_5029Parser = parser;
		}
		return enumFieldName_5029Parser;
	}

	/**
	* @generated
	*/
	private IParser parameterNameValue_5014Parser;

	/**
	* @generated
	*/
	private IParser getParameterNameValue_5014Parser() {
		if (parameterNameValue_5014Parser == null) {
			EAttribute[] features = new EAttribute[] { CrossflowPackage.eINSTANCE.getParameter_Name(),
					CrossflowPackage.eINSTANCE.getParameter_Value() };
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}:{1}"); //$NON-NLS-1$
			parser.setEditorPattern("{0}:{1}"); //$NON-NLS-1$
			parser.setEditPattern("{0}:{1}"); //$NON-NLS-1$
			parameterNameValue_5014Parser = parser;
		}
		return parameterNameValue_5014Parser;
	}

	/**
	* @generated
	*/
	private IParser parameterNameValue_5022Parser;

	/**
	* @generated
	*/
	private IParser getParameterNameValue_5022Parser() {
		if (parameterNameValue_5022Parser == null) {
			EAttribute[] features = new EAttribute[] { CrossflowPackage.eINSTANCE.getParameter_Name(),
					CrossflowPackage.eINSTANCE.getParameter_Value() };
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}:{1}"); //$NON-NLS-1$
			parser.setEditorPattern("{0}:{1}"); //$NON-NLS-1$
			parser.setEditPattern("{0}:{1}"); //$NON-NLS-1$
			parameterNameValue_5022Parser = parser;
		}
		return parameterNameValue_5022Parser;
	}

	/**
	* @generated
	*/
	protected IParser getParser(int visualID) {
		switch (visualID) {
		case CsvSourceNameEditPart.VISUAL_ID:
			return getCsvSourceName_5001Parser();
		case CsvSinkNameEditPart.VISUAL_ID:
			return getCsvSinkName_5002Parser();
		case TopicNameEditPart.VISUAL_ID:
			return getTopicName_5003Parser();
		case QueueNameEditPart.VISUAL_ID:
			return getQueueName_5004Parser();
		case SourceNameEditPart.VISUAL_ID:
			return getSourceName_5005Parser();
		case SinkNameEditPart.VISUAL_ID:
			return getSinkName_5006Parser();
		case CommitmentTaskNameEditPart.VISUAL_ID:
			return getCommitmentTaskName_5007Parser();
		case OpinionatedTaskNameEditPart.VISUAL_ID:
			return getOpinionatedTaskName_5008Parser();
		case ScriptedTaskNameEditPart.VISUAL_ID:
			return getScriptedTaskName_5017Parser();
		case ReusableComponentNameEditPart.VISUAL_ID:
			return getReusableComponentName_5021Parser();
		case DataFieldNameTypeEditPart.VISUAL_ID:
			return getDataFieldNameType_5026Parser();
		case EnumFieldNameEditPart.VISUAL_ID:
			return getEnumFieldName_5027Parser();
		case TaskNameEditPart.VISUAL_ID:
			return getTaskName_5010Parser();
		case TypeNameEditPart.VISUAL_ID:
			return getTypeName_5012Parser();
		case LanguageNameEditPart.VISUAL_ID:
			return getLanguageName_5015Parser();
		case SerializerNameEditPart.VISUAL_ID:
			return getSerializerName_5023Parser();
		case DataFieldNameType2EditPart.VISUAL_ID:
			return getDataFieldNameType_5024Parser();
		case EnumFieldName2EditPart.VISUAL_ID:
			return getEnumFieldName_5025Parser();
		case DataFieldNameType3EditPart.VISUAL_ID:
			return getDataFieldNameType_5028Parser();
		case EnumFieldName3EditPart.VISUAL_ID:
			return getEnumFieldName_5029Parser();
		case ParameterNameValueEditPart.VISUAL_ID:
			return getParameterNameValue_5014Parser();
		case ParameterNameValue2EditPart.VISUAL_ID:
			return getParameterNameValue_5022Parser();
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
