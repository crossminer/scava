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
	 * The feature id for the '<em><b>Configuration</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKFLOW__CONFIGURATION = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKFLOW__NAME = 1;

	/**
	 * The feature id for the '<em><b>Package</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKFLOW__PACKAGE = 2;

	/**
	 * The feature id for the '<em><b>Streams</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKFLOW__STREAMS = 3;

	/**
	 * The feature id for the '<em><b>Tasks</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKFLOW__TASKS = 4;

	/**
	 * The feature id for the '<em><b>Types</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKFLOW__TYPES = 5;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKFLOW__PARAMETERS = 6;

	/**
	 * The number of structural features of the '<em>Workflow</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKFLOW_FEATURE_COUNT = 7;

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
	 * The number of structural features of the '<em>Stream</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_FEATURE_COUNT = 2;

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
	 * The feature id for the '<em><b>Parallel</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK__PARALLEL = 3;

	/**
	 * The feature id for the '<em><b>Cached</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK__CACHED = 4;

	/**
	 * The number of structural features of the '<em>Task</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_FEATURE_COUNT = 5;

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
	 * The number of structural features of the '<em>Source</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_FEATURE_COUNT = TASK_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link crossflow.impl.SinkImpl <em>Sink</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see crossflow.impl.SinkImpl
	 * @see crossflow.impl.CrossflowPackageImpl#getSink()
	 * @generated
	 */
	int SINK = 6;

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
	 * The number of structural features of the '<em>Sink</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINK_FEATURE_COUNT = TASK_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link crossflow.impl.TypeImpl <em>Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see crossflow.impl.TypeImpl
	 * @see crossflow.impl.CrossflowPackageImpl#getType()
	 * @generated
	 */
	int TYPE = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Is Many</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE__IS_MANY = 1;

	/**
	 * The feature id for the '<em><b>Extending</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE__EXTENDING = 2;

	/**
	 * The feature id for the '<em><b>Fields</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE__FIELDS = 3;

	/**
	 * The number of structural features of the '<em>Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link crossflow.impl.FieldImpl <em>Field</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see crossflow.impl.FieldImpl
	 * @see crossflow.impl.CrossflowPackageImpl#getField()
	 * @generated
	 */
	int FIELD = 8;

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
	 * The number of structural features of the '<em>Field</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link crossflow.impl.ConfigurationImpl <em>Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see crossflow.impl.ConfigurationImpl
	 * @see crossflow.impl.CrossflowPackageImpl#getConfiguration()
	 * @generated
	 */
	int CONFIGURATION = 9;

	/**
	 * The feature id for the '<em><b>Number Of Workers</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION__NUMBER_OF_WORKERS = 0;

	/**
	 * The feature id for the '<em><b>Is Master Also Worker</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION__IS_MASTER_ALSO_WORKER = 1;

	/**
	 * The feature id for the '<em><b>Root Package Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION__ROOT_PACKAGE_NAME = 2;

	/**
	 * The feature id for the '<em><b>Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION__PROJECT_NAME = 3;

	/**
	 * The number of structural features of the '<em>Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION_FEATURE_COUNT = 4;


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
	 * Returns the meta object for the containment reference '{@link crossflow.Workflow#getConfiguration <em>Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Configuration</em>'.
	 * @see crossflow.Workflow#getConfiguration()
	 * @see #getWorkflow()
	 * @generated
	 */
	EReference getWorkflow_Configuration();

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
	 * Returns the meta object for the attribute '{@link crossflow.Workflow#getPackage <em>Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Package</em>'.
	 * @see crossflow.Workflow#getPackage()
	 * @see #getWorkflow()
	 * @generated
	 */
	EAttribute getWorkflow_Package();

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
	 * Returns the meta object for class '{@link crossflow.Source <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Source</em>'.
	 * @see crossflow.Source
	 * @generated
	 */
	EClass getSource();

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
	 * Returns the meta object for class '{@link crossflow.Configuration <em>Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Configuration</em>'.
	 * @see crossflow.Configuration
	 * @generated
	 */
	EClass getConfiguration();

	/**
	 * Returns the meta object for the attribute '{@link crossflow.Configuration#getNumberOfWorkers <em>Number Of Workers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Number Of Workers</em>'.
	 * @see crossflow.Configuration#getNumberOfWorkers()
	 * @see #getConfiguration()
	 * @generated
	 */
	EAttribute getConfiguration_NumberOfWorkers();

	/**
	 * Returns the meta object for the attribute '{@link crossflow.Configuration#getIsMasterAlsoWorker <em>Is Master Also Worker</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Master Also Worker</em>'.
	 * @see crossflow.Configuration#getIsMasterAlsoWorker()
	 * @see #getConfiguration()
	 * @generated
	 */
	EAttribute getConfiguration_IsMasterAlsoWorker();

	/**
	 * Returns the meta object for the attribute '{@link crossflow.Configuration#getRootPackageName <em>Root Package Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Root Package Name</em>'.
	 * @see crossflow.Configuration#getRootPackageName()
	 * @see #getConfiguration()
	 * @generated
	 */
	EAttribute getConfiguration_RootPackageName();

	/**
	 * Returns the meta object for the attribute '{@link crossflow.Configuration#getProjectName <em>Project Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Project Name</em>'.
	 * @see crossflow.Configuration#getProjectName()
	 * @see #getConfiguration()
	 * @generated
	 */
	EAttribute getConfiguration_ProjectName();

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
		 * The meta object literal for the '<em><b>Configuration</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WORKFLOW__CONFIGURATION = eINSTANCE.getWorkflow_Configuration();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WORKFLOW__NAME = eINSTANCE.getWorkflow_Name();

		/**
		 * The meta object literal for the '<em><b>Package</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WORKFLOW__PACKAGE = eINSTANCE.getWorkflow_Package();

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
		 * The meta object literal for the '{@link crossflow.impl.SourceImpl <em>Source</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see crossflow.impl.SourceImpl
		 * @see crossflow.impl.CrossflowPackageImpl#getSource()
		 * @generated
		 */
		EClass SOURCE = eINSTANCE.getSource();

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
		 * The meta object literal for the '{@link crossflow.impl.ConfigurationImpl <em>Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see crossflow.impl.ConfigurationImpl
		 * @see crossflow.impl.CrossflowPackageImpl#getConfiguration()
		 * @generated
		 */
		EClass CONFIGURATION = eINSTANCE.getConfiguration();

		/**
		 * The meta object literal for the '<em><b>Number Of Workers</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONFIGURATION__NUMBER_OF_WORKERS = eINSTANCE.getConfiguration_NumberOfWorkers();

		/**
		 * The meta object literal for the '<em><b>Is Master Also Worker</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONFIGURATION__IS_MASTER_ALSO_WORKER = eINSTANCE.getConfiguration_IsMasterAlsoWorker();

		/**
		 * The meta object literal for the '<em><b>Root Package Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONFIGURATION__ROOT_PACKAGE_NAME = eINSTANCE.getConfiguration_RootPackageName();

		/**
		 * The meta object literal for the '<em><b>Project Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONFIGURATION__PROJECT_NAME = eINSTANCE.getConfiguration_ProjectName();

	}

} //CrossflowPackage
