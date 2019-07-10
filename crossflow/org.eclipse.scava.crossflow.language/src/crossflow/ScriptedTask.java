/**
 */
package crossflow;

import org.eclipse.emf.common.util.EList;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Scripted Task</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link crossflow.ScriptedTask#getLanguage <em>Language</em>}</li>
 *   <li>{@link crossflow.ScriptedTask#getScript <em>Script</em>}</li>
 *   <li>{@link crossflow.ScriptedTask#getOutputVariables <em>Output Variables</em>}</li>
 * </ul>
 *
 * @see crossflow.CrossflowPackage#getScriptedTask()
 * @model annotation="gmf.node label='name'"
 * @generated
 */
public interface ScriptedTask extends Task {
	/**
	 * Returns the value of the '<em><b>Language</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Language</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Language</em>' attribute.
	 * @see #setLanguage(String)
	 * @see crossflow.CrossflowPackage#getScriptedTask_Language()
	 * @model
	 * @generated
	 */
	String getLanguage();

	/**
	 * Sets the value of the '{@link crossflow.ScriptedTask#getLanguage <em>Language</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Language</em>' attribute.
	 * @see #getLanguage()
	 * @generated
	 */
	void setLanguage(String value);

	/**
	 * Returns the value of the '<em><b>Script</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Script</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Script</em>' attribute.
	 * @see #setScript(String)
	 * @see crossflow.CrossflowPackage#getScriptedTask_Script()
	 * @model annotation="emf.gen propertyMultiLine='true'"
	 * @generated
	 */
	String getScript();

	/**
	 * Sets the value of the '{@link crossflow.ScriptedTask#getScript <em>Script</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Script</em>' attribute.
	 * @see #getScript()
	 * @generated
	 */
	void setScript(String value);

	/**
	 * Returns the value of the '<em><b>Output Variables</b></em>' containment reference list.
	 * The list contents are of type {@link crossflow.Field}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Output Variables</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Output Variables</em>' containment reference list.
	 * @see crossflow.CrossflowPackage#getScriptedTask_OutputVariables()
	 * @model containment="true"
	 *        annotation="gmf.compartment layout='list' collapsible='false'"
	 * @generated
	 */
	EList<Field> getOutputVariables();

} // ScriptedTask
