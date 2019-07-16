/**
 */
package crossflow;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see crossflow.CrossflowFactory
 * @model kind="package"
 * @generated
 */
public interface CrossflowPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "crossflow";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "org.eclipse.scava.crossflow";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "cf";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CrossflowPackage eINSTANCE = crossflow.impl.CrossflowPackageImpl.init();

	/**
	 * The meta object id for the '{@link crossflow.impl.WorkflowImpl <em>Workflow</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see crossflow.impl.WorkflowImpl
	 * @see crossflow.impl.CrossflowPackageImpl#getWorkflow()
	 * @generated
	 */
	int WORKFLOW = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKFLOW__NAME = 0;

	/**
	 * The feature id for the '<em><b>Streams</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKFLOW__STREAMS = 1;

	/**
	 * The feature id for the '<em><b>Tasks</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKFLOW__TASKS = 2;

	/**
	 * The feature id for the '<em><b>Types</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKFLOW__TYPES = 3;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKFLOW__PARAMETERS = 4;

	/**
	 * The feature id for the '<em><b>Languages</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKFLOW__LANGUAGES = 5;

	/**
	 * The number of structural features of the '<em>Workflow</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKFLOW_FEATURE_COUNT = 6;

	/**
	 * The meta object id for the '{@link crossflow.impl.StreamImpl <em>Stream</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see crossflow.impl.StreamImpl
	 * @see crossflow.impl.CrossflowPackageImpl#getStream()
	 * @generated
	 */
	int STREAM = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM__NAME = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM__TYPE = 1;

	/**
	 * The feature id for the '<em><b>Input Of</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM__INPUT_OF = 2;

	/**
	 * The feature id for the '<em><b>Output Of</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM__OUTPUT_OF = 3;

	/**
	 * The number of structural features of the '<em>Stream</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link crossflow.impl.TopicImpl <em>Topic</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see crossflow.impl.TopicImpl
	 * @see crossflow.impl.CrossflowPackageImpl#getTopic()
	 * @generated
	 */
	int TOPIC = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TOPIC__NAME = STREAM__NAME;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TOPIC__TYPE = STREAM__TYPE;

	/**
	 * The feature id for the '<em><b>Input Of</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TOPIC__INPUT_OF = STREAM__INPUT_OF;

	/**
	 * The feature id for the '<em><b>Output Of</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TOPIC__OUTPUT_OF = STREAM__OUTPUT_OF;

	/**
	 * The number of structural features of the '<em>Topic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TOPIC_FEATURE_COUNT = STREAM_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link crossflow.impl.QueueImpl <em>Queue</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see crossflow.impl.QueueImpl
	 * @see crossflow.impl.CrossflowPackageImpl#getQueue()
	 * @generated
	 */
	int QUEUE = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUE__NAME = STREAM__NAME;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUE__TYPE = STREAM__TYPE;

	/**
	 * The feature id for the '<em><b>Input Of</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUE__INPUT_OF = STREAM__INPUT_OF;

	/**
	 * The feature id for the '<em><b>Output Of</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUE__OUTPUT_OF = STREAM__OUTPUT_OF;

	/**
	 * The number of structural features of the '<em>Queue</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUE_FEATURE_COUNT = STREAM_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link crossflow.impl.TaskImpl <em>Task</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see crossflow.impl.TaskImpl
	 * @see crossflow.impl.CrossflowPackageImpl#getTask()
	 * @generated
	 */
	int TASK = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK__NAME = 0;

	/**
	 * The feature id for the '<em><b>Input</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK__INPUT = 1;

	/**
	 * The feature id for the '<em><b>Output</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK__OUTPUT = 2;

	/**
	 * The feature id for the '<em><b>Master Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK__MASTER_ONLY = 3;

	/**
	 * The feature id for the '<em><b>Parallel</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK__PARALLEL = 4;

	/**
	 * The feature id for the '<em><b>Cached</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK__CACHED = 5;

	/**
	 * The feature id for the '<em><b>Multiple Outputs</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK__MULTIPLE_OUTPUTS = 6;

	/**
	 * The feature id for the '<em><b>Impl</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK__IMPL = 7;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK__PARAMETERS = 8;

	/**
	 * The feature id for the '<em><b>Languages</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK__LANGUAGES = 9;

	/**
	 * The feature id for the '<em><b>Configurations</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK__CONFIGURATIONS = 10;

	/**
	 * The number of structural features of the '<em>Task</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_FEATURE_COUNT = 11;

	/**
	 * The meta object id for the '{@link crossflow.impl.SourceImpl <em>Source</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see crossflow.impl.SourceImpl
	 * @see crossflow.impl.CrossflowPackageImpl#getSource()
	 * @generated
	 */
	int SOURCE = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE__NAME = TASK__NAME;

	/**
	 * The feature id for the '<em><b>Input</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE__INPUT = TASK__INPUT;

	/**
	 * The feature id for the '<em><b>Output</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE__OUTPUT = TASK__OUTPUT;

	/**
	 * The feature id for the '<em><b>Master Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE__MASTER_ONLY = TASK__MASTER_ONLY;

	/**
	 * The feature id for the '<em><b>Parallel</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE__PARALLEL = TASK__PARALLEL;

	/**
	 * The feature id for the '<em><b>Cached</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE__CACHED = TASK__CACHED;

	/**
	 * The feature id for the '<em><b>Multiple Outputs</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE__MULTIPLE_OUTPUTS = TASK__MULTIPLE_OUTPUTS;

	/**
	 * The feature id for the '<em><b>Impl</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE__IMPL = TASK__IMPL;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE__PARAMETERS = TASK__PARAMETERS;

	/**
	 * The feature id for the '<em><b>Languages</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE__LANGUAGES = TASK__LANGUAGES;

	/**
	 * The feature id for the '<em><b>Configurations</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE__CONFIGURATIONS = TASK__CONFIGURATIONS;

	/**
	 * The number of structural features of the '<em>Source</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_FEATURE_COUNT = TASK_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link crossflow.impl.CsvSourceImpl <em>Csv Source</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see crossflow.impl.CsvSourceImpl
	 * @see crossflow.impl.CrossflowPackageImpl#getCsvSource()
	 * @generated
	 */
	int CSV_SOURCE = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CSV_SOURCE__NAME = SOURCE__NAME;

	/**
	 * The feature id for the '<em><b>Input</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CSV_SOURCE__INPUT = SOURCE__INPUT;

	/**
	 * The feature id for the '<em><b>Output</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CSV_SOURCE__OUTPUT = SOURCE__OUTPUT;

	/**
	 * The feature id for the '<em><b>Master Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CSV_SOURCE__MASTER_ONLY = SOURCE__MASTER_ONLY;

	/**
	 * The feature id for the '<em><b>Parallel</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CSV_SOURCE__PARALLEL = SOURCE__PARALLEL;

	/**
	 * The feature id for the '<em><b>Cached</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CSV_SOURCE__CACHED = SOURCE__CACHED;

	/**
	 * The feature id for the '<em><b>Multiple Outputs</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CSV_SOURCE__MULTIPLE_OUTPUTS = SOURCE__MULTIPLE_OUTPUTS;

	/**
	 * The feature id for the '<em><b>Impl</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CSV_SOURCE__IMPL = SOURCE__IMPL;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CSV_SOURCE__PARAMETERS = SOURCE__PARAMETERS;

	/**
	 * The feature id for the '<em><b>Languages</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CSV_SOURCE__LANGUAGES = SOURCE__LANGUAGES;

	/**
	 * The feature id for the '<em><b>Configurations</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CSV_SOURCE__CONFIGURATIONS = SOURCE__CONFIGURATIONS;

	/**
	 * The feature id for the '<em><b>File Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CSV_SOURCE__FILE_NAME = SOURCE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Csv Source</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CSV_SOURCE_FEATURE_COUNT = SOURCE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link crossflow.impl.SinkImpl <em>Sink</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see crossflow.impl.SinkImpl
	 * @see crossflow.impl.CrossflowPackageImpl#getSink()
	 * @generated
	 */
	int SINK = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINK__NAME = TASK__NAME;

	/**
	 * The feature id for the '<em><b>Input</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINK__INPUT = TASK__INPUT;

	/**
	 * The feature id for the '<em><b>Output</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINK__OUTPUT = TASK__OUTPUT;

	/**
	 * The feature id for the '<em><b>Master Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINK__MASTER_ONLY = TASK__MASTER_ONLY;

	/**
	 * The feature id for the '<em><b>Parallel</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINK__PARALLEL = TASK__PARALLEL;

	/**
	 * The feature id for the '<em><b>Cached</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINK__CACHED = TASK__CACHED;

	/**
	 * The feature id for the '<em><b>Multiple Outputs</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINK__MULTIPLE_OUTPUTS = TASK__MULTIPLE_OUTPUTS;

	/**
	 * The feature id for the '<em><b>Impl</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINK__IMPL = TASK__IMPL;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINK__PARAMETERS = TASK__PARAMETERS;

	/**
	 * The feature id for the '<em><b>Languages</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINK__LANGUAGES = TASK__LANGUAGES;

	/**
	 * The feature id for the '<em><b>Configurations</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINK__CONFIGURATIONS = TASK__CONFIGURATIONS;

	/**
	 * The number of structural features of the '<em>Sink</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINK_FEATURE_COUNT = TASK_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link crossflow.impl.CsvSinkImpl <em>Csv Sink</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see crossflow.impl.CsvSinkImpl
	 * @see crossflow.impl.CrossflowPackageImpl#getCsvSink()
	 * @generated
	 */
	int CSV_SINK = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CSV_SINK__NAME = SINK__NAME;

	/**
	 * The feature id for the '<em><b>Input</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CSV_SINK__INPUT = SINK__INPUT;

	/**
	 * The feature id for the '<em><b>Output</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CSV_SINK__OUTPUT = SINK__OUTPUT;

	/**
	 * The feature id for the '<em><b>Master Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CSV_SINK__MASTER_ONLY = SINK__MASTER_ONLY;

	/**
	 * The feature id for the '<em><b>Parallel</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CSV_SINK__PARALLEL = SINK__PARALLEL;

	/**
	 * The feature id for the '<em><b>Cached</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CSV_SINK__CACHED = SINK__CACHED;

	/**
	 * The feature id for the '<em><b>Multiple Outputs</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CSV_SINK__MULTIPLE_OUTPUTS = SINK__MULTIPLE_OUTPUTS;

	/**
	 * The feature id for the '<em><b>Impl</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CSV_SINK__IMPL = SINK__IMPL;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CSV_SINK__PARAMETERS = SINK__PARAMETERS;

	/**
	 * The feature id for the '<em><b>Languages</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CSV_SINK__LANGUAGES = SINK__LANGUAGES;

	/**
	 * The feature id for the '<em><b>Configurations</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CSV_SINK__CONFIGURATIONS = SINK__CONFIGURATIONS;

	/**
	 * The feature id for the '<em><b>File Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CSV_SINK__FILE_NAME = SINK_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Csv Sink</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CSV_SINK_FEATURE_COUNT = SINK_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link crossflow.impl.CommitmentTaskImpl <em>Commitment Task</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see crossflow.impl.CommitmentTaskImpl
	 * @see crossflow.impl.CrossflowPackageImpl#getCommitmentTask()
	 * @generated
	 */
	int COMMITMENT_TASK = 9;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMITMENT_TASK__NAME = TASK__NAME;

	/**
	 * The feature id for the '<em><b>Input</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMITMENT_TASK__INPUT = TASK__INPUT;

	/**
	 * The feature id for the '<em><b>Output</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMITMENT_TASK__OUTPUT = TASK__OUTPUT;

	/**
	 * The feature id for the '<em><b>Master Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMITMENT_TASK__MASTER_ONLY = TASK__MASTER_ONLY;

	/**
	 * The feature id for the '<em><b>Parallel</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMITMENT_TASK__PARALLEL = TASK__PARALLEL;

	/**
	 * The feature id for the '<em><b>Cached</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMITMENT_TASK__CACHED = TASK__CACHED;

	/**
	 * The feature id for the '<em><b>Multiple Outputs</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMITMENT_TASK__MULTIPLE_OUTPUTS = TASK__MULTIPLE_OUTPUTS;

	/**
	 * The feature id for the '<em><b>Impl</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMITMENT_TASK__IMPL = TASK__IMPL;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMITMENT_TASK__PARAMETERS = TASK__PARAMETERS;

	/**
	 * The feature id for the '<em><b>Languages</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMITMENT_TASK__LANGUAGES = TASK__LANGUAGES;

	/**
	 * The feature id for the '<em><b>Configurations</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMITMENT_TASK__CONFIGURATIONS = TASK__CONFIGURATIONS;

	/**
	 * The feature id for the '<em><b>Commit After</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMITMENT_TASK__COMMIT_AFTER = TASK_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Commitment Task</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMITMENT_TASK_FEATURE_COUNT = TASK_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link crossflow.impl.OpinionatedTaskImpl <em>Opinionated Task</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see crossflow.impl.OpinionatedTaskImpl
	 * @see crossflow.impl.CrossflowPackageImpl#getOpinionatedTask()
	 * @generated
	 */
	int OPINIONATED_TASK = 10;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPINIONATED_TASK__NAME = TASK__NAME;

	/**
	 * The feature id for the '<em><b>Input</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPINIONATED_TASK__INPUT = TASK__INPUT;

	/**
	 * The feature id for the '<em><b>Output</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPINIONATED_TASK__OUTPUT = TASK__OUTPUT;

	/**
	 * The feature id for the '<em><b>Master Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPINIONATED_TASK__MASTER_ONLY = TASK__MASTER_ONLY;

	/**
	 * The feature id for the '<em><b>Parallel</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPINIONATED_TASK__PARALLEL = TASK__PARALLEL;

	/**
	 * The feature id for the '<em><b>Cached</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPINIONATED_TASK__CACHED = TASK__CACHED;

	/**
	 * The feature id for the '<em><b>Multiple Outputs</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPINIONATED_TASK__MULTIPLE_OUTPUTS = TASK__MULTIPLE_OUTPUTS;

	/**
	 * The feature id for the '<em><b>Impl</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPINIONATED_TASK__IMPL = TASK__IMPL;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPINIONATED_TASK__PARAMETERS = TASK__PARAMETERS;

	/**
	 * The feature id for the '<em><b>Languages</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPINIONATED_TASK__LANGUAGES = TASK__LANGUAGES;

	/**
	 * The feature id for the '<em><b>Configurations</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPINIONATED_TASK__CONFIGURATIONS = TASK__CONFIGURATIONS;

	/**
	 * The number of structural features of the '<em>Opinionated Task</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPINIONATED_TASK_FEATURE_COUNT = TASK_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link crossflow.impl.ScriptedTaskImpl <em>Scripted Task</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see crossflow.impl.ScriptedTaskImpl
	 * @see crossflow.impl.CrossflowPackageImpl#getScriptedTask()
	 * @generated
	 */
	int SCRIPTED_TASK = 11;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCRIPTED_TASK__NAME = TASK__NAME;

	/**
	 * The feature id for the '<em><b>Input</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCRIPTED_TASK__INPUT = TASK__INPUT;

	/**
	 * The feature id for the '<em><b>Output</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCRIPTED_TASK__OUTPUT = TASK__OUTPUT;

	/**
	 * The feature id for the '<em><b>Master Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCRIPTED_TASK__MASTER_ONLY = TASK__MASTER_ONLY;

	/**
	 * The feature id for the '<em><b>Parallel</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCRIPTED_TASK__PARALLEL = TASK__PARALLEL;

	/**
	 * The feature id for the '<em><b>Cached</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCRIPTED_TASK__CACHED = TASK__CACHED;

	/**
	 * The feature id for the '<em><b>Multiple Outputs</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCRIPTED_TASK__MULTIPLE_OUTPUTS = TASK__MULTIPLE_OUTPUTS;

	/**
	 * The feature id for the '<em><b>Impl</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCRIPTED_TASK__IMPL = TASK__IMPL;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCRIPTED_TASK__PARAMETERS = TASK__PARAMETERS;

	/**
	 * The feature id for the '<em><b>Languages</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCRIPTED_TASK__LANGUAGES = TASK__LANGUAGES;

	/**
	 * The feature id for the '<em><b>Configurations</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCRIPTED_TASK__CONFIGURATIONS = TASK__CONFIGURATIONS;

	/**
	 * The feature id for the '<em><b>Scripting Language</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCRIPTED_TASK__SCRIPTING_LANGUAGE = TASK_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Script</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCRIPTED_TASK__SCRIPT = TASK_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Output Variables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCRIPTED_TASK__OUTPUT_VARIABLES = TASK_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Scripted Task</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCRIPTED_TASK_FEATURE_COUNT = TASK_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link crossflow.impl.TypeImpl <em>Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see crossflow.impl.TypeImpl
	 * @see crossflow.impl.CrossflowPackageImpl#getType()
	 * @generated
	 */
	int TYPE = 12;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Impl</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE__IMPL = 1;

	/**
	 * The feature id for the '<em><b>Is Many</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE__IS_MANY = 2;

	/**
	 * The feature id for the '<em><b>Extending</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE__EXTENDING = 3;

	/**
	 * The feature id for the '<em><b>Fields</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE__FIELDS = 4;

	/**
	 * The number of structural features of the '<em>Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link crossflow.impl.FieldImpl <em>Field</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see crossflow.impl.FieldImpl
	 * @see crossflow.impl.CrossflowPackageImpl#getField()
	 * @generated
	 */
	int FIELD = 13;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD__NAME = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD__TYPE = 1;

	/**
	 * The feature id for the '<em><b>Many</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD__MANY = 2;

	/**
	 * The number of structural features of the '<em>Field</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_FEATURE_COUNT = 3;


	/**
	 * The meta object id for the '{@link crossflow.impl.LanguageImpl <em>Language</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see crossflow.impl.LanguageImpl
	 * @see crossflow.impl.CrossflowPackageImpl#getLanguage()
	 * @generated
	 */
	int LANGUAGE = 14;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LANGUAGE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Package</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LANGUAGE__PACKAGE = 1;

	/**
	 * The feature id for the '<em><b>Output Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LANGUAGE__OUTPUT_FOLDER = 2;

	/**
	 * The feature id for the '<em><b>Gen Output Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LANGUAGE__GEN_OUTPUT_FOLDER = 3;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LANGUAGE__PARAMETERS = 4;

	/**
	 * The number of structural features of the '<em>Language</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LANGUAGE_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link crossflow.impl.ParameterImpl <em>Parameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see crossflow.impl.ParameterImpl
	 * @see crossflow.impl.CrossflowPackageImpl#getParameter()
	 * @generated
	 */
	int PARAMETER = 15;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__NAME = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_FEATURE_COUNT = 2;


	/**
	 * Returns the meta object for class '{@link crossflow.Workflow <em>Workflow</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Workflow</em>'.
	 * @see crossflow.Workflow
	 * @generated
	 */
	EClass getWorkflow();

	/**
	 * Returns the meta object for the attribute '{@link crossflow.Workflow#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see crossflow.Workflow#getName()
	 * @see #getWorkflow()
	 * @generated
	 */
	EAttribute getWorkflow_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link crossflow.Workflow#getStreams <em>Streams</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Streams</em>'.
	 * @see crossflow.Workflow#getStreams()
	 * @see #getWorkflow()
	 * @generated
	 */
	EReference getWorkflow_Streams();

	/**
	 * Returns the meta object for the containment reference list '{@link crossflow.Workflow#getTasks <em>Tasks</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Tasks</em>'.
	 * @see crossflow.Workflow#getTasks()
	 * @see #getWorkflow()
	 * @generated
	 */
	EReference getWorkflow_Tasks();

	/**
	 * Returns the meta object for the containment reference list '{@link crossflow.Workflow#getTypes <em>Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Types</em>'.
	 * @see crossflow.Workflow#getTypes()
	 * @see #getWorkflow()
	 * @generated
	 */
	EReference getWorkflow_Types();

	/**
	 * Returns the meta object for the containment reference list '{@link crossflow.Workflow#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameters</em>'.
	 * @see crossflow.Workflow#getParameters()
	 * @see #getWorkflow()
	 * @generated
	 */
	EReference getWorkflow_Parameters();

	/**
	 * Returns the meta object for the containment reference list '{@link crossflow.Workflow#getLanguages <em>Languages</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Languages</em>'.
	 * @see crossflow.Workflow#getLanguages()
	 * @see #getWorkflow()
	 * @generated
	 */
	EReference getWorkflow_Languages();

	/**
	 * Returns the meta object for class '{@link crossflow.Stream <em>Stream</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Stream</em>'.
	 * @see crossflow.Stream
	 * @generated
	 */
	EClass getStream();

	/**
	 * Returns the meta object for the attribute '{@link crossflow.Stream#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see crossflow.Stream#getName()
	 * @see #getStream()
	 * @generated
	 */
	EAttribute getStream_Name();

	/**
	 * Returns the meta object for the reference '{@link crossflow.Stream#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see crossflow.Stream#getType()
	 * @see #getStream()
	 * @generated
	 */
	EReference getStream_Type();

	/**
	 * Returns the meta object for the reference list '{@link crossflow.Stream#getInputOf <em>Input Of</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Input Of</em>'.
	 * @see crossflow.Stream#getInputOf()
	 * @see #getStream()
	 * @generated
	 */
	EReference getStream_InputOf();

	/**
	 * Returns the meta object for the reference list '{@link crossflow.Stream#getOutputOf <em>Output Of</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Output Of</em>'.
	 * @see crossflow.Stream#getOutputOf()
	 * @see #getStream()
	 * @generated
	 */
	EReference getStream_OutputOf();

	/**
	 * Returns the meta object for class '{@link crossflow.Topic <em>Topic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Topic</em>'.
	 * @see crossflow.Topic
	 * @generated
	 */
	EClass getTopic();

	/**
	 * Returns the meta object for class '{@link crossflow.Queue <em>Queue</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Queue</em>'.
	 * @see crossflow.Queue
	 * @generated
	 */
	EClass getQueue();

	/**
	 * Returns the meta object for class '{@link crossflow.Task <em>Task</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Task</em>'.
	 * @see crossflow.Task
	 * @generated
	 */
	EClass getTask();

	/**
	 * Returns the meta object for the attribute '{@link crossflow.Task#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see crossflow.Task#getName()
	 * @see #getTask()
	 * @generated
	 */
	EAttribute getTask_Name();

	/**
	 * Returns the meta object for the reference list '{@link crossflow.Task#getInput <em>Input</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Input</em>'.
	 * @see crossflow.Task#getInput()
	 * @see #getTask()
	 * @generated
	 */
	EReference getTask_Input();

	/**
	 * Returns the meta object for the reference list '{@link crossflow.Task#getOutput <em>Output</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Output</em>'.
	 * @see crossflow.Task#getOutput()
	 * @see #getTask()
	 * @generated
	 */
	EReference getTask_Output();

	/**
	 * Returns the meta object for the attribute '{@link crossflow.Task#getMasterOnly <em>Master Only</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Master Only</em>'.
	 * @see crossflow.Task#getMasterOnly()
	 * @see #getTask()
	 * @generated
	 */
	EAttribute getTask_MasterOnly();

	/**
	 * Returns the meta object for the attribute '{@link crossflow.Task#getParallel <em>Parallel</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Parallel</em>'.
	 * @see crossflow.Task#getParallel()
	 * @see #getTask()
	 * @generated
	 */
	EAttribute getTask_Parallel();

	/**
	 * Returns the meta object for the attribute '{@link crossflow.Task#getCached <em>Cached</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cached</em>'.
	 * @see crossflow.Task#getCached()
	 * @see #getTask()
	 * @generated
	 */
	EAttribute getTask_Cached();

	/**
	 * Returns the meta object for the attribute '{@link crossflow.Task#getMultipleOutputs <em>Multiple Outputs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Multiple Outputs</em>'.
	 * @see crossflow.Task#getMultipleOutputs()
	 * @see #getTask()
	 * @generated
	 */
	EAttribute getTask_MultipleOutputs();

	/**
	 * Returns the meta object for the attribute '{@link crossflow.Task#getImpl <em>Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Impl</em>'.
	 * @see crossflow.Task#getImpl()
	 * @see #getTask()
	 * @generated
	 */
	EAttribute getTask_Impl();

	/**
	 * Returns the meta object for the reference list '{@link crossflow.Task#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Parameters</em>'.
	 * @see crossflow.Task#getParameters()
	 * @see #getTask()
	 * @generated
	 */
	EReference getTask_Parameters();

	/**
	 * Returns the meta object for the reference list '{@link crossflow.Task#getLanguages <em>Languages</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Languages</em>'.
	 * @see crossflow.Task#getLanguages()
	 * @see #getTask()
	 * @generated
	 */
	EReference getTask_Languages();

	/**
	 * Returns the meta object for the reference list '{@link crossflow.Task#getConfigurations <em>Configurations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Configurations</em>'.
	 * @see crossflow.Task#getConfigurations()
	 * @see #getTask()
	 * @generated
	 */
	EReference getTask_Configurations();

	/**
	 * Returns the meta object for class '{@link crossflow.Source <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Source</em>'.
	 * @see crossflow.Source
	 * @generated
	 */
	EClass getSource();

	/**
	 * Returns the meta object for class '{@link crossflow.CsvSource <em>Csv Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Csv Source</em>'.
	 * @see crossflow.CsvSource
	 * @generated
	 */
	EClass getCsvSource();

	/**
	 * Returns the meta object for the attribute '{@link crossflow.CsvSource#getFileName <em>File Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>File Name</em>'.
	 * @see crossflow.CsvSource#getFileName()
	 * @see #getCsvSource()
	 * @generated
	 */
	EAttribute getCsvSource_FileName();

	/**
	 * Returns the meta object for class '{@link crossflow.Sink <em>Sink</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sink</em>'.
	 * @see crossflow.Sink
	 * @generated
	 */
	EClass getSink();

	/**
	 * Returns the meta object for class '{@link crossflow.CsvSink <em>Csv Sink</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Csv Sink</em>'.
	 * @see crossflow.CsvSink
	 * @generated
	 */
	EClass getCsvSink();

	/**
	 * Returns the meta object for the attribute '{@link crossflow.CsvSink#getFileName <em>File Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>File Name</em>'.
	 * @see crossflow.CsvSink#getFileName()
	 * @see #getCsvSink()
	 * @generated
	 */
	EAttribute getCsvSink_FileName();

	/**
	 * Returns the meta object for class '{@link crossflow.CommitmentTask <em>Commitment Task</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Commitment Task</em>'.
	 * @see crossflow.CommitmentTask
	 * @generated
	 */
	EClass getCommitmentTask();

	/**
	 * Returns the meta object for the attribute '{@link crossflow.CommitmentTask#getCommitAfter <em>Commit After</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Commit After</em>'.
	 * @see crossflow.CommitmentTask#getCommitAfter()
	 * @see #getCommitmentTask()
	 * @generated
	 */
	EAttribute getCommitmentTask_CommitAfter();

	/**
	 * Returns the meta object for class '{@link crossflow.OpinionatedTask <em>Opinionated Task</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Opinionated Task</em>'.
	 * @see crossflow.OpinionatedTask
	 * @generated
	 */
	EClass getOpinionatedTask();

	/**
	 * Returns the meta object for class '{@link crossflow.ScriptedTask <em>Scripted Task</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Scripted Task</em>'.
	 * @see crossflow.ScriptedTask
	 * @generated
	 */
	EClass getScriptedTask();

	/**
	 * Returns the meta object for the attribute '{@link crossflow.ScriptedTask#getScriptingLanguage <em>Scripting Language</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Scripting Language</em>'.
	 * @see crossflow.ScriptedTask#getScriptingLanguage()
	 * @see #getScriptedTask()
	 * @generated
	 */
	EAttribute getScriptedTask_ScriptingLanguage();

	/**
	 * Returns the meta object for the attribute '{@link crossflow.ScriptedTask#getScript <em>Script</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Script</em>'.
	 * @see crossflow.ScriptedTask#getScript()
	 * @see #getScriptedTask()
	 * @generated
	 */
	EAttribute getScriptedTask_Script();

	/**
	 * Returns the meta object for the containment reference list '{@link crossflow.ScriptedTask#getOutputVariables <em>Output Variables</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Output Variables</em>'.
	 * @see crossflow.ScriptedTask#getOutputVariables()
	 * @see #getScriptedTask()
	 * @generated
	 */
	EReference getScriptedTask_OutputVariables();

	/**
	 * Returns the meta object for class '{@link crossflow.Type <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type</em>'.
	 * @see crossflow.Type
	 * @generated
	 */
	EClass getType();

	/**
	 * Returns the meta object for the attribute '{@link crossflow.Type#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see crossflow.Type#getName()
	 * @see #getType()
	 * @generated
	 */
	EAttribute getType_Name();

	/**
	 * Returns the meta object for the attribute '{@link crossflow.Type#getImpl <em>Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Impl</em>'.
	 * @see crossflow.Type#getImpl()
	 * @see #getType()
	 * @generated
	 */
	EAttribute getType_Impl();

	/**
	 * Returns the meta object for the attribute '{@link crossflow.Type#isIsMany <em>Is Many</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Many</em>'.
	 * @see crossflow.Type#isIsMany()
	 * @see #getType()
	 * @generated
	 */
	EAttribute getType_IsMany();

	/**
	 * Returns the meta object for the reference list '{@link crossflow.Type#getExtending <em>Extending</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Extending</em>'.
	 * @see crossflow.Type#getExtending()
	 * @see #getType()
	 * @generated
	 */
	EReference getType_Extending();

	/**
	 * Returns the meta object for the containment reference list '{@link crossflow.Type#getFields <em>Fields</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Fields</em>'.
	 * @see crossflow.Type#getFields()
	 * @see #getType()
	 * @generated
	 */
	EReference getType_Fields();

	/**
	 * Returns the meta object for class '{@link crossflow.Field <em>Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Field</em>'.
	 * @see crossflow.Field
	 * @generated
	 */
	EClass getField();

	/**
	 * Returns the meta object for the attribute '{@link crossflow.Field#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see crossflow.Field#getName()
	 * @see #getField()
	 * @generated
	 */
	EAttribute getField_Name();

	/**
	 * Returns the meta object for the attribute '{@link crossflow.Field#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see crossflow.Field#getType()
	 * @see #getField()
	 * @generated
	 */
	EAttribute getField_Type();

	/**
	 * Returns the meta object for the attribute '{@link crossflow.Field#isMany <em>Many</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Many</em>'.
	 * @see crossflow.Field#isMany()
	 * @see #getField()
	 * @generated
	 */
	EAttribute getField_Many();

	/**
	 * Returns the meta object for class '{@link crossflow.Language <em>Language</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Language</em>'.
	 * @see crossflow.Language
	 * @generated
	 */
	EClass getLanguage();

	/**
	 * Returns the meta object for the attribute '{@link crossflow.Language#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see crossflow.Language#getName()
	 * @see #getLanguage()
	 * @generated
	 */
	EAttribute getLanguage_Name();

	/**
	 * Returns the meta object for the attribute '{@link crossflow.Language#getPackage <em>Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Package</em>'.
	 * @see crossflow.Language#getPackage()
	 * @see #getLanguage()
	 * @generated
	 */
	EAttribute getLanguage_Package();

	/**
	 * Returns the meta object for the attribute '{@link crossflow.Language#getOutputFolder <em>Output Folder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Output Folder</em>'.
	 * @see crossflow.Language#getOutputFolder()
	 * @see #getLanguage()
	 * @generated
	 */
	EAttribute getLanguage_OutputFolder();

	/**
	 * Returns the meta object for the attribute '{@link crossflow.Language#getGenOutputFolder <em>Gen Output Folder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Gen Output Folder</em>'.
	 * @see crossflow.Language#getGenOutputFolder()
	 * @see #getLanguage()
	 * @generated
	 */
	EAttribute getLanguage_GenOutputFolder();

	/**
	 * Returns the meta object for the containment reference list '{@link crossflow.Language#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameters</em>'.
	 * @see crossflow.Language#getParameters()
	 * @see #getLanguage()
	 * @generated
	 */
	EReference getLanguage_Parameters();

	/**
	 * Returns the meta object for class '{@link crossflow.Parameter <em>Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parameter</em>'.
	 * @see crossflow.Parameter
	 * @generated
	 */
	EClass getParameter();

	/**
	 * Returns the meta object for the attribute '{@link crossflow.Parameter#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see crossflow.Parameter#getName()
	 * @see #getParameter()
	 * @generated
	 */
	EAttribute getParameter_Name();

	/**
	 * Returns the meta object for the attribute '{@link crossflow.Parameter#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see crossflow.Parameter#getValue()
	 * @see #getParameter()
	 * @generated
	 */
	EAttribute getParameter_Value();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	CrossflowFactory getCrossflowFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link crossflow.impl.WorkflowImpl <em>Workflow</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see crossflow.impl.WorkflowImpl
		 * @see crossflow.impl.CrossflowPackageImpl#getWorkflow()
		 * @generated
		 */
		EClass WORKFLOW = eINSTANCE.getWorkflow();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WORKFLOW__NAME = eINSTANCE.getWorkflow_Name();

		/**
		 * The meta object literal for the '<em><b>Streams</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WORKFLOW__STREAMS = eINSTANCE.getWorkflow_Streams();

		/**
		 * The meta object literal for the '<em><b>Tasks</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WORKFLOW__TASKS = eINSTANCE.getWorkflow_Tasks();

		/**
		 * The meta object literal for the '<em><b>Types</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WORKFLOW__TYPES = eINSTANCE.getWorkflow_Types();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WORKFLOW__PARAMETERS = eINSTANCE.getWorkflow_Parameters();

		/**
		 * The meta object literal for the '<em><b>Languages</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WORKFLOW__LANGUAGES = eINSTANCE.getWorkflow_Languages();

		/**
		 * The meta object literal for the '{@link crossflow.impl.StreamImpl <em>Stream</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see crossflow.impl.StreamImpl
		 * @see crossflow.impl.CrossflowPackageImpl#getStream()
		 * @generated
		 */
		EClass STREAM = eINSTANCE.getStream();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STREAM__NAME = eINSTANCE.getStream_Name();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STREAM__TYPE = eINSTANCE.getStream_Type();

		/**
		 * The meta object literal for the '<em><b>Input Of</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STREAM__INPUT_OF = eINSTANCE.getStream_InputOf();

		/**
		 * The meta object literal for the '<em><b>Output Of</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STREAM__OUTPUT_OF = eINSTANCE.getStream_OutputOf();

		/**
		 * The meta object literal for the '{@link crossflow.impl.TopicImpl <em>Topic</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see crossflow.impl.TopicImpl
		 * @see crossflow.impl.CrossflowPackageImpl#getTopic()
		 * @generated
		 */
		EClass TOPIC = eINSTANCE.getTopic();

		/**
		 * The meta object literal for the '{@link crossflow.impl.QueueImpl <em>Queue</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see crossflow.impl.QueueImpl
		 * @see crossflow.impl.CrossflowPackageImpl#getQueue()
		 * @generated
		 */
		EClass QUEUE = eINSTANCE.getQueue();

		/**
		 * The meta object literal for the '{@link crossflow.impl.TaskImpl <em>Task</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see crossflow.impl.TaskImpl
		 * @see crossflow.impl.CrossflowPackageImpl#getTask()
		 * @generated
		 */
		EClass TASK = eINSTANCE.getTask();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TASK__NAME = eINSTANCE.getTask_Name();

		/**
		 * The meta object literal for the '<em><b>Input</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TASK__INPUT = eINSTANCE.getTask_Input();

		/**
		 * The meta object literal for the '<em><b>Output</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TASK__OUTPUT = eINSTANCE.getTask_Output();

		/**
		 * The meta object literal for the '<em><b>Master Only</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TASK__MASTER_ONLY = eINSTANCE.getTask_MasterOnly();

		/**
		 * The meta object literal for the '<em><b>Parallel</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TASK__PARALLEL = eINSTANCE.getTask_Parallel();

		/**
		 * The meta object literal for the '<em><b>Cached</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TASK__CACHED = eINSTANCE.getTask_Cached();

		/**
		 * The meta object literal for the '<em><b>Multiple Outputs</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TASK__MULTIPLE_OUTPUTS = eINSTANCE.getTask_MultipleOutputs();

		/**
		 * The meta object literal for the '<em><b>Impl</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TASK__IMPL = eINSTANCE.getTask_Impl();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TASK__PARAMETERS = eINSTANCE.getTask_Parameters();

		/**
		 * The meta object literal for the '<em><b>Languages</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TASK__LANGUAGES = eINSTANCE.getTask_Languages();

		/**
		 * The meta object literal for the '<em><b>Configurations</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TASK__CONFIGURATIONS = eINSTANCE.getTask_Configurations();

		/**
		 * The meta object literal for the '{@link crossflow.impl.SourceImpl <em>Source</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see crossflow.impl.SourceImpl
		 * @see crossflow.impl.CrossflowPackageImpl#getSource()
		 * @generated
		 */
		EClass SOURCE = eINSTANCE.getSource();

		/**
		 * The meta object literal for the '{@link crossflow.impl.CsvSourceImpl <em>Csv Source</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see crossflow.impl.CsvSourceImpl
		 * @see crossflow.impl.CrossflowPackageImpl#getCsvSource()
		 * @generated
		 */
		EClass CSV_SOURCE = eINSTANCE.getCsvSource();

		/**
		 * The meta object literal for the '<em><b>File Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CSV_SOURCE__FILE_NAME = eINSTANCE.getCsvSource_FileName();

		/**
		 * The meta object literal for the '{@link crossflow.impl.SinkImpl <em>Sink</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see crossflow.impl.SinkImpl
		 * @see crossflow.impl.CrossflowPackageImpl#getSink()
		 * @generated
		 */
		EClass SINK = eINSTANCE.getSink();

		/**
		 * The meta object literal for the '{@link crossflow.impl.CsvSinkImpl <em>Csv Sink</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see crossflow.impl.CsvSinkImpl
		 * @see crossflow.impl.CrossflowPackageImpl#getCsvSink()
		 * @generated
		 */
		EClass CSV_SINK = eINSTANCE.getCsvSink();

		/**
		 * The meta object literal for the '<em><b>File Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CSV_SINK__FILE_NAME = eINSTANCE.getCsvSink_FileName();

		/**
		 * The meta object literal for the '{@link crossflow.impl.CommitmentTaskImpl <em>Commitment Task</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see crossflow.impl.CommitmentTaskImpl
		 * @see crossflow.impl.CrossflowPackageImpl#getCommitmentTask()
		 * @generated
		 */
		EClass COMMITMENT_TASK = eINSTANCE.getCommitmentTask();

		/**
		 * The meta object literal for the '<em><b>Commit After</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMMITMENT_TASK__COMMIT_AFTER = eINSTANCE.getCommitmentTask_CommitAfter();

		/**
		 * The meta object literal for the '{@link crossflow.impl.OpinionatedTaskImpl <em>Opinionated Task</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see crossflow.impl.OpinionatedTaskImpl
		 * @see crossflow.impl.CrossflowPackageImpl#getOpinionatedTask()
		 * @generated
		 */
		EClass OPINIONATED_TASK = eINSTANCE.getOpinionatedTask();

		/**
		 * The meta object literal for the '{@link crossflow.impl.ScriptedTaskImpl <em>Scripted Task</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see crossflow.impl.ScriptedTaskImpl
		 * @see crossflow.impl.CrossflowPackageImpl#getScriptedTask()
		 * @generated
		 */
		EClass SCRIPTED_TASK = eINSTANCE.getScriptedTask();

		/**
		 * The meta object literal for the '<em><b>Scripting Language</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCRIPTED_TASK__SCRIPTING_LANGUAGE = eINSTANCE.getScriptedTask_ScriptingLanguage();

		/**
		 * The meta object literal for the '<em><b>Script</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCRIPTED_TASK__SCRIPT = eINSTANCE.getScriptedTask_Script();

		/**
		 * The meta object literal for the '<em><b>Output Variables</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SCRIPTED_TASK__OUTPUT_VARIABLES = eINSTANCE.getScriptedTask_OutputVariables();

		/**
		 * The meta object literal for the '{@link crossflow.impl.TypeImpl <em>Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see crossflow.impl.TypeImpl
		 * @see crossflow.impl.CrossflowPackageImpl#getType()
		 * @generated
		 */
		EClass TYPE = eINSTANCE.getType();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPE__NAME = eINSTANCE.getType_Name();

		/**
		 * The meta object literal for the '<em><b>Impl</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPE__IMPL = eINSTANCE.getType_Impl();

		/**
		 * The meta object literal for the '<em><b>Is Many</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPE__IS_MANY = eINSTANCE.getType_IsMany();

		/**
		 * The meta object literal for the '<em><b>Extending</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TYPE__EXTENDING = eINSTANCE.getType_Extending();

		/**
		 * The meta object literal for the '<em><b>Fields</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TYPE__FIELDS = eINSTANCE.getType_Fields();

		/**
		 * The meta object literal for the '{@link crossflow.impl.FieldImpl <em>Field</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see crossflow.impl.FieldImpl
		 * @see crossflow.impl.CrossflowPackageImpl#getField()
		 * @generated
		 */
		EClass FIELD = eINSTANCE.getField();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FIELD__NAME = eINSTANCE.getField_Name();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FIELD__TYPE = eINSTANCE.getField_Type();

		/**
		 * The meta object literal for the '<em><b>Many</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FIELD__MANY = eINSTANCE.getField_Many();

		/**
		 * The meta object literal for the '{@link crossflow.impl.LanguageImpl <em>Language</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see crossflow.impl.LanguageImpl
		 * @see crossflow.impl.CrossflowPackageImpl#getLanguage()
		 * @generated
		 */
		EClass LANGUAGE = eINSTANCE.getLanguage();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LANGUAGE__NAME = eINSTANCE.getLanguage_Name();

		/**
		 * The meta object literal for the '<em><b>Package</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LANGUAGE__PACKAGE = eINSTANCE.getLanguage_Package();

		/**
		 * The meta object literal for the '<em><b>Output Folder</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LANGUAGE__OUTPUT_FOLDER = eINSTANCE.getLanguage_OutputFolder();

		/**
		 * The meta object literal for the '<em><b>Gen Output Folder</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LANGUAGE__GEN_OUTPUT_FOLDER = eINSTANCE.getLanguage_GenOutputFolder();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LANGUAGE__PARAMETERS = eINSTANCE.getLanguage_Parameters();

		/**
		 * The meta object literal for the '{@link crossflow.impl.ParameterImpl <em>Parameter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see crossflow.impl.ParameterImpl
		 * @see crossflow.impl.CrossflowPackageImpl#getParameter()
		 * @generated
		 */
		EClass PARAMETER = eINSTANCE.getParameter();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETER__NAME = eINSTANCE.getParameter_Name();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETER__VALUE = eINSTANCE.getParameter_Value();

	}

} //CrossflowPackage
