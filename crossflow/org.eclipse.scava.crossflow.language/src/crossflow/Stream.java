/**
 */
package crossflow;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Stream</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link crossflow.Stream#getName <em>Name</em>}</li>
 *   <li>{@link crossflow.Stream#isMany <em>Many</em>}</li>
 *   <li>{@link crossflow.Stream#getType <em>Type</em>}</li>
 *   <li>{@link crossflow.Stream#getInputOf <em>Input Of</em>}</li>
 *   <li>{@link crossflow.Stream#getOutputOf <em>Output Of</em>}</li>
 * </ul>
 *
 * @see crossflow.CrossflowPackage#getStream()
 * @model abstract="true"
 *        annotation="gmf.node figure='ellipse' label='name' label.icon='false' color='255,224,224'"
 * @generated
 */
public interface Stream extends EObject {
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
	 * @see crossflow.CrossflowPackage#getStream_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link crossflow.Stream#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Many</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Many</em>' attribute.
	 * @see #setMany(boolean)
	 * @see crossflow.CrossflowPackage#getStream_Many()
	 * @model default="false"
	 * @generated
	 */
	boolean isMany();

	/**
	 * Sets the value of the '{@link crossflow.Stream#isMany <em>Many</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Many</em>' attribute.
	 * @see #isMany()
	 * @generated
	 */
	void setMany(boolean value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(Type)
	 * @see crossflow.CrossflowPackage#getStream_Type()
	 * @model annotation="gmf.link"
	 * @generated
	 */
	Type getType();

	/**
	 * Sets the value of the '{@link crossflow.Stream#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(Type value);

	/**
	 * Returns the value of the '<em><b>Input Of</b></em>' reference list.
	 * The list contents are of type {@link crossflow.Task}.
	 * It is bidirectional and its opposite is '{@link crossflow.Task#getInput <em>Input</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Input Of</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Input Of</em>' reference list.
	 * @see crossflow.CrossflowPackage#getStream_InputOf()
	 * @see crossflow.Task#getInput
	 * @model opposite="input"
	 *        annotation="gmf.link target.decoration='filledclosedarrow'"
	 * @generated
	 */
	EList<Task> getInputOf();

	/**
	 * Returns the value of the '<em><b>Output Of</b></em>' reference list.
	 * The list contents are of type {@link crossflow.Task}.
	 * It is bidirectional and its opposite is '{@link crossflow.Task#getOutput <em>Output</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Output Of</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Output Of</em>' reference list.
	 * @see crossflow.CrossflowPackage#getStream_OutputOf()
	 * @see crossflow.Task#getOutput
	 * @model opposite="output"
	 * @generated
	 */
	EList<Task> getOutputOf();

} // Stream
