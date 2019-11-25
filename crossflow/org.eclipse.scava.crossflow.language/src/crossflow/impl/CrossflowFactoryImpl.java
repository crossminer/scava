/**
 */
package crossflow.impl;

import crossflow.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class CrossflowFactoryImpl extends EFactoryImpl implements CrossflowFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CrossflowFactory init() {
		try {
			CrossflowFactory theCrossflowFactory = (CrossflowFactory)EPackage.Registry.INSTANCE.getEFactory(CrossflowPackage.eNS_URI);
			if (theCrossflowFactory != null) {
				return theCrossflowFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new CrossflowFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CrossflowFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case CrossflowPackage.WORKFLOW: return createWorkflow();
			case CrossflowPackage.TOPIC: return createTopic();
			case CrossflowPackage.QUEUE: return createQueue();
			case CrossflowPackage.TASK: return createTask();
			case CrossflowPackage.SOURCE: return createSource();
			case CrossflowPackage.CSV_SOURCE: return createCsvSource();
			case CrossflowPackage.SINK: return createSink();
			case CrossflowPackage.CSV_SINK: return createCsvSink();
			case CrossflowPackage.COMMITMENT_TASK: return createCommitmentTask();
			case CrossflowPackage.OPINIONATED_TASK: return createOpinionatedTask();
			case CrossflowPackage.SCRIPTED_TASK: return createScriptedTask();
			case CrossflowPackage.REUSABLE_COMPONENT: return createReusableComponent();
			case CrossflowPackage.TYPE: return createType();
			case CrossflowPackage.DATA_FIELD: return createDataField();
			case CrossflowPackage.ENUM_FIELD: return createEnumField();
			case CrossflowPackage.LANGUAGE: return createLanguage();
			case CrossflowPackage.PARAMETER: return createParameter();
			case CrossflowPackage.SERIALIZER: return createSerializer();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Workflow createWorkflow() {
		WorkflowImpl workflow = new WorkflowImpl();
		return workflow;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Topic createTopic() {
		TopicImpl topic = new TopicImpl();
		return topic;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Queue createQueue() {
		QueueImpl queue = new QueueImpl();
		return queue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Task createTask() {
		TaskImpl task = new TaskImpl();
		return task;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Source createSource() {
		SourceImpl source = new SourceImpl();
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CsvSource createCsvSource() {
		CsvSourceImpl csvSource = new CsvSourceImpl();
		return csvSource;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Sink createSink() {
		SinkImpl sink = new SinkImpl();
		return sink;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CsvSink createCsvSink() {
		CsvSinkImpl csvSink = new CsvSinkImpl();
		return csvSink;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CommitmentTask createCommitmentTask() {
		CommitmentTaskImpl commitmentTask = new CommitmentTaskImpl();
		return commitmentTask;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public OpinionatedTask createOpinionatedTask() {
		OpinionatedTaskImpl opinionatedTask = new OpinionatedTaskImpl();
		return opinionatedTask;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ScriptedTask createScriptedTask() {
		ScriptedTaskImpl scriptedTask = new ScriptedTaskImpl();
		return scriptedTask;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ReusableComponent createReusableComponent() {
		ReusableComponentImpl reusableComponent = new ReusableComponentImpl();
		return reusableComponent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Type createType() {
		TypeImpl type = new TypeImpl();
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DataField createDataField() {
		DataFieldImpl dataField = new DataFieldImpl();
		return dataField;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EnumField createEnumField() {
		EnumFieldImpl enumField = new EnumFieldImpl();
		return enumField;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Language createLanguage() {
		LanguageImpl language = new LanguageImpl();
		return language;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Parameter createParameter() {
		ParameterImpl parameter = new ParameterImpl();
		return parameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Serializer createSerializer() {
		SerializerImpl serializer = new SerializerImpl();
		return serializer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CrossflowPackage getCrossflowPackage() {
		return (CrossflowPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static CrossflowPackage getPackage() {
		return CrossflowPackage.eINSTANCE;
	}

} //CrossflowFactoryImpl
