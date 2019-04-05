/**
 */
package crossflow;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Task</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link crossflow.Task#getName <em>Name</em>}</li>
 *   <li>{@link crossflow.Task#getInput <em>Input</em>}</li>
 *   <li>{@link crossflow.Task#getOutput <em>Output</em>}</li>
 *   <li>{@link crossflow.Task#getMasterOnly <em>Master Only</em>}</li>
 *   <li>{@link crossflow.Task#getParallel <em>Parallel</em>}</li>
 *   <li>{@link crossflow.Task#getCached <em>Cached</em>}</li>
 *   <li>{@link crossflow.Task#getMultipleOutputs <em>Multiple Outputs</em>}</li>
 *   <li>{@link crossflow.Task#getParameters <em>Parameters</em>}</li>
 * </ul>
 *
 * @see crossflow.CrossflowPackage#getTask()
 * @model annotation="gmf.node label='name' label.icon='false'"
 * @generated
 */
public interface Task extends EObject {
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
	 * @see crossflow.CrossflowPackage#getTask_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link crossflow.Task#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Input</b></em>' reference list.
	 * The list contents are of type {@link crossflow.Stream}.
	 * It is bidirectional and its opposite is '{@link crossflow.Stream#getInputOf <em>Input Of</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Input</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Input</em>' reference list.
	 * @see crossflow.CrossflowPackage#getTask_Input()
	 * @see crossflow.Stream#getInputOf
	 * @model opposite="inputOf"
	 *        annotation="gmf.link source.decoration='filledclosedarrow'"
	 * @generated
	 */
	EList<Stream> getInput();

	/**
	 * Returns the value of the '<em><b>Output</b></em>' reference list.
	 * The list contents are of type {@link crossflow.Stream}.
	 * It is bidirectional and its opposite is '{@link crossflow.Stream#getOutputOf <em>Output Of</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Output</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Output</em>' reference list.
	 * @see crossflow.CrossflowPackage#getTask_Output()
	 * @see crossflow.Stream#getOutputOf
	 * @model opposite="outputOf"
	 *        annotation="gmf.link target.decoration='filledclosedarrow'"
	 * @generated
	 */
	EList<Stream> getOutput();

	/**
	 * Returns the value of the '<em><b>Master Only</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Master Only</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Master Only</em>' attribute.
	 * @see #setMasterOnly(Boolean)
	 * @see crossflow.CrossflowPackage#getTask_MasterOnly()
	 * @model default="false"
	 * @generated
	 */
	Boolean getMasterOnly();

	/**
	 * Sets the value of the '{@link crossflow.Task#getMasterOnly <em>Master Only</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Master Only</em>' attribute.
	 * @see #getMasterOnly()
	 * @generated
	 */
	void setMasterOnly(Boolean value);

	/**
	 * Returns the value of the '<em><b>Parallel</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parallel</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parallel</em>' attribute.
	 * @see #setParallel(Boolean)
	 * @see crossflow.CrossflowPackage#getTask_Parallel()
	 * @model
	 * @generated
	 */
	Boolean getParallel();

	/**
	 * Sets the value of the '{@link crossflow.Task#getParallel <em>Parallel</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parallel</em>' attribute.
	 * @see #getParallel()
	 * @generated
	 */
	void setParallel(Boolean value);

	/**
	 * Returns the value of the '<em><b>Cached</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cached</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cached</em>' attribute.
	 * @see #setCached(Boolean)
	 * @see crossflow.CrossflowPackage#getTask_Cached()
	 * @model
	 * @generated
	 */
	Boolean getCached();

	/**
	 * Sets the value of the '{@link crossflow.Task#getCached <em>Cached</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cached</em>' attribute.
	 * @see #getCached()
	 * @generated
	 */
	void setCached(Boolean value);

	/**
	 * Returns the value of the '<em><b>Multiple Outputs</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Multiple Outputs</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Multiple Outputs</em>' attribute.
	 * @see #setMultipleOutputs(Boolean)
	 * @see crossflow.CrossflowPackage#getTask_MultipleOutputs()
	 * @model default="false"
	 * @generated
	 */
	Boolean getMultipleOutputs();

	/**
	 * Sets the value of the '{@link crossflow.Task#getMultipleOutputs <em>Multiple Outputs</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Multiple Outputs</em>' attribute.
	 * @see #getMultipleOutputs()
	 * @generated
	 */
	void setMultipleOutputs(Boolean value);

	/**
	 * Returns the value of the '<em><b>Parameters</b></em>' reference list.
	 * The list contents are of type {@link crossflow.Field}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameters</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameters</em>' reference list.
	 * @see crossflow.CrossflowPackage#getTask_Parameters()
	 * @model
	 * @generated
	 */
	EList<Field> getParameters();

} // Task
