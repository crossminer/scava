/**
 */
package crossflow;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Data Field</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link crossflow.DataField#getType <em>Type</em>}</li>
 *   <li>{@link crossflow.DataField#isMany <em>Many</em>}</li>
 * </ul>
 *
 * @see crossflow.CrossflowPackage#getDataField()
 * @model annotation="gmf.node label='name,type' figure='rectangle' label.pattern='{0}:{1}' label.icon='false' color='181,255,255'"
 * @generated
 */
public interface DataField extends Field {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The default value is <code>"String"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(String)
	 * @see crossflow.CrossflowPackage#getDataField_Type()
	 * @model default="String"
	 * @generated
	 */
	String getType();

	/**
	 * Sets the value of the '{@link crossflow.DataField#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(String value);

	/**
	 * Returns the value of the '<em><b>Many</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Many</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Many</em>' attribute.
	 * @see #setMany(boolean)
	 * @see crossflow.CrossflowPackage#getDataField_Many()
	 * @model default="false"
	 * @generated
	 */
	boolean isMany();

	/**
	 * Sets the value of the '{@link crossflow.DataField#isMany <em>Many</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Many</em>' attribute.
	 * @see #isMany()
	 * @generated
	 */
	void setMany(boolean value);

} // DataField
