/**
 */
package crossflow;

import crossflowComponents.Component;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Reusable Component</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link crossflow.ReusableComponent#getComponent <em>Component</em>}</li>
 * </ul>
 *
 * @see crossflow.CrossflowPackage#getReusableComponent()
 * @model annotation="gmf.node label='name'"
 * @generated
 */
public interface ReusableComponent extends Task {
	/**
	 * Returns the value of the '<em><b>Component</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Component</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Component</em>' reference.
	 * @see #setComponent(Component)
	 * @see crossflow.CrossflowPackage#getReusableComponent_Component()
	 * @model required="true"
	 * @generated
	 */
	Component getComponent();

	/**
	 * Sets the value of the '{@link crossflow.ReusableComponent#getComponent <em>Component</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Component</em>' reference.
	 * @see #getComponent()
	 * @generated
	 */
	void setComponent(Component value);

} // ReusableComponent
