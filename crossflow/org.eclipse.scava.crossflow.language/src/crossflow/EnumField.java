/**
 */
package crossflow;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Enum Field</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link crossflow.EnumField#getValues <em>Values</em>}</li>
 * </ul>
 *
 * @see crossflow.CrossflowPackage#getEnumField()
 * @model annotation="gmf.node label='name' figure='rectangle' label.icon='false' color='181,255,255'"
 * @generated
 */
public interface EnumField extends Field {
	/**
	 * Returns the value of the '<em><b>Values</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Values</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Values</em>' attribute list.
	 * @see crossflow.CrossflowPackage#getEnumField_Values()
	 * @model
	 * @generated
	 */
	EList<String> getValues();

} // EnumField
