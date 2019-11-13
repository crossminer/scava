/**
 */
package crossflowComponents;

import crossflow.Language;
import crossflow.Type;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Component</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link crossflowComponents.Component#getFullyQualifiedName <em>Fully Qualified Name</em>}</li>
 *   <li>{@link crossflowComponents.Component#getLanguage <em>Language</em>}</li>
 *   <li>{@link crossflowComponents.Component#getConsumes <em>Consumes</em>}</li>
 *   <li>{@link crossflowComponents.Component#getProduces <em>Produces</em>}</li>
 *   <li>{@link crossflowComponents.Component#isMultipleOutputs <em>Multiple Outputs</em>}</li>
 *   <li>{@link crossflowComponents.Component#getComponentType <em>Component Type</em>}</li>
 * </ul>
 *
 * @see crossflowComponents.CrossflowComponentsPackage#getComponent()
 * @model
 * @generated
 */
public interface Component extends EObject {
	/**
	 * Returns the value of the '<em><b>Fully Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fully Qualified Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fully Qualified Name</em>' attribute.
	 * @see #setFullyQualifiedName(String)
	 * @see crossflowComponents.CrossflowComponentsPackage#getComponent_FullyQualifiedName()
	 * @model required="true"
	 * @generated
	 */
	String getFullyQualifiedName();

	/**
	 * Sets the value of the '{@link crossflowComponents.Component#getFullyQualifiedName <em>Fully Qualified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fully Qualified Name</em>' attribute.
	 * @see #getFullyQualifiedName()
	 * @generated
	 */
	void setFullyQualifiedName(String value);

	/**
	 * Returns the value of the '<em><b>Language</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Language</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Language</em>' reference.
	 * @see #setLanguage(Language)
	 * @see crossflowComponents.CrossflowComponentsPackage#getComponent_Language()
	 * @model
	 * @generated
	 */
	Language getLanguage();

	/**
	 * Sets the value of the '{@link crossflowComponents.Component#getLanguage <em>Language</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Language</em>' reference.
	 * @see #getLanguage()
	 * @generated
	 */
	void setLanguage(Language value);

	/**
	 * Returns the value of the '<em><b>Consumes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Consumes</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Consumes</em>' containment reference.
	 * @see #setConsumes(Type)
	 * @see crossflowComponents.CrossflowComponentsPackage#getComponent_Consumes()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Type getConsumes();

	/**
	 * Sets the value of the '{@link crossflowComponents.Component#getConsumes <em>Consumes</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Consumes</em>' containment reference.
	 * @see #getConsumes()
	 * @generated
	 */
	void setConsumes(Type value);

	/**
	 * Returns the value of the '<em><b>Produces</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Produces</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Produces</em>' containment reference.
	 * @see #setProduces(Type)
	 * @see crossflowComponents.CrossflowComponentsPackage#getComponent_Produces()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Type getProduces();

	/**
	 * Sets the value of the '{@link crossflowComponents.Component#getProduces <em>Produces</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Produces</em>' containment reference.
	 * @see #getProduces()
	 * @generated
	 */
	void setProduces(Type value);

	/**
	 * Returns the value of the '<em><b>Multiple Outputs</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Multiple Outputs</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Multiple Outputs</em>' attribute.
	 * @see #setMultipleOutputs(boolean)
	 * @see crossflowComponents.CrossflowComponentsPackage#getComponent_MultipleOutputs()
	 * @model
	 * @generated
	 */
	boolean isMultipleOutputs();

	/**
	 * Sets the value of the '{@link crossflowComponents.Component#isMultipleOutputs <em>Multiple Outputs</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Multiple Outputs</em>' attribute.
	 * @see #isMultipleOutputs()
	 * @generated
	 */
	void setMultipleOutputs(boolean value);

	/**
	 * Returns the value of the '<em><b>Component Type</b></em>' attribute.
	 * The literals are from the enumeration {@link crossflowComponents.ComponentTypes}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Component Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Component Type</em>' attribute.
	 * @see crossflowComponents.ComponentTypes
	 * @see #setComponentType(ComponentTypes)
	 * @see crossflowComponents.CrossflowComponentsPackage#getComponent_ComponentType()
	 * @model
	 * @generated
	 */
	ComponentTypes getComponentType();

	/**
	 * Sets the value of the '{@link crossflowComponents.Component#getComponentType <em>Component Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Component Type</em>' attribute.
	 * @see crossflowComponents.ComponentTypes
	 * @see #getComponentType()
	 * @generated
	 */
	void setComponentType(ComponentTypes value);

} // Component
