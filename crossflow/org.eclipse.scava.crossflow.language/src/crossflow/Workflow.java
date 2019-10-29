/**
 */
package crossflow;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Workflow</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link crossflow.Workflow#getName <em>Name</em>}</li>
 *   <li>{@link crossflow.Workflow#getStreams <em>Streams</em>}</li>
 *   <li>{@link crossflow.Workflow#getTasks <em>Tasks</em>}</li>
 *   <li>{@link crossflow.Workflow#getTypes <em>Types</em>}</li>
 *   <li>{@link crossflow.Workflow#getParameters <em>Parameters</em>}</li>
 *   <li>{@link crossflow.Workflow#getLanguages <em>Languages</em>}</li>
 *   <li>{@link crossflow.Workflow#getSerialiser <em>Serialiser</em>}</li>
 * </ul>
 *
 * @see crossflow.CrossflowPackage#getWorkflow()
 * @model annotation="gmf.diagram onefile='true'"
 * @generated
 */
public interface Workflow extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see crossflow.CrossflowPackage#getWorkflow_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link crossflow.Workflow#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Streams</b></em>' containment reference list.
	 * The list contents are of type {@link crossflow.Stream}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Streams</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Streams</em>' containment reference list.
	 * @see crossflow.CrossflowPackage#getWorkflow_Streams()
	 * @model containment="true"
	 * @generated
	 */
	EList<Stream> getStreams();

	/**
	 * Returns the value of the '<em><b>Tasks</b></em>' containment reference list.
	 * The list contents are of type {@link crossflow.Task}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tasks</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tasks</em>' containment reference list.
	 * @see crossflow.CrossflowPackage#getWorkflow_Tasks()
	 * @model containment="true"
	 * @generated
	 */
	EList<Task> getTasks();

	/**
	 * Returns the value of the '<em><b>Types</b></em>' containment reference list.
	 * The list contents are of type {@link crossflow.Type}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Types</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Types</em>' containment reference list.
	 * @see crossflow.CrossflowPackage#getWorkflow_Types()
	 * @model containment="true"
	 * @generated
	 */
	EList<Type> getTypes();

	/**
	 * Returns the value of the '<em><b>Parameters</b></em>' containment reference list.
	 * The list contents are of type {@link crossflow.Field}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameters</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameters</em>' containment reference list.
	 * @see crossflow.CrossflowPackage#getWorkflow_Parameters()
	 * @model containment="true"
	 * @generated
	 */
	EList<Field> getParameters();

	/**
	 * Returns the value of the '<em><b>Languages</b></em>' containment reference list.
	 * The list contents are of type {@link crossflow.Language}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Languages</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Languages</em>' containment reference list.
	 * @see crossflow.CrossflowPackage#getWorkflow_Languages()
	 * @model containment="true"
	 * @generated
	 */
	EList<Language> getLanguages();

	/**
	 * Returns the value of the '<em><b>Serialiser</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Serialiser</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Serialiser</em>' containment reference.
	 * @see #setSerialiser(Serialiser)
	 * @see crossflow.CrossflowPackage#getWorkflow_Serialiser()
	 * @model containment="true"
	 * @generated
	 */
	Serialiser getSerialiser();

	/**
	 * Sets the value of the '{@link crossflow.Workflow#getSerialiser <em>Serialiser</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Serialiser</em>' containment reference.
	 * @see #getSerialiser()
	 * @generated
	 */
	void setSerialiser(Serialiser value);

} // Workflow
