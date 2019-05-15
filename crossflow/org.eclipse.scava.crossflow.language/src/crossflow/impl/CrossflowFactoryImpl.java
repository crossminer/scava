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
			case CrossflowPackage.TYPE: return createType();
			case CrossflowPackage.FIELD: return createField();
			case CrossflowPackage.LANGUAGE: return createLanguage();
			case CrossflowPackage.PARAMETER: return createParameter();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Workflow createWorkflow() {
		WorkflowImpl workflow = new WorkflowImpl();
		return workflow;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Topic createTopic() {
		TopicImpl topic = new TopicImpl();
		return topic;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Queue createQueue() {
		QueueImpl queue = new QueueImpl();
		return queue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Task createTask() {
		TaskImpl task = new TaskImpl();
		return task;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Source createSource() {
		SourceImpl source = new SourceImpl();
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CsvSource createCsvSource() {
		CsvSourceImpl csvSource = new CsvSourceImpl();
		return csvSource;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Sink createSink() {
		SinkImpl sink = new SinkImpl();
		return sink;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CsvSink createCsvSink() {
		CsvSinkImpl csvSink = new CsvSinkImpl();
		return csvSink;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CommitmentTask createCommitmentTask() {
		CommitmentTaskImpl commitmentTask = new CommitmentTaskImpl();
		return commitmentTask;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OpinionatedTask createOpinionatedTask() {
		OpinionatedTaskImpl opinionatedTask = new OpinionatedTaskImpl();
		return opinionatedTask;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Type createType() {
		TypeImpl type = new TypeImpl();
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Field createField() {
		FieldImpl field = new FieldImpl();
		return field;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Language createLanguage() {
		LanguageImpl language = new LanguageImpl();
		return language;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Parameter createParameter() {
		ParameterImpl parameter = new ParameterImpl();
		return parameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
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
