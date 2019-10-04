/**
 */
package crossflow;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Commitment Task</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link crossflow.CommitmentTask#getCommitAfter <em>Commit After</em>}</li>
 * </ul>
 *
 * @see crossflow.CrossflowPackage#getCommitmentTask()
 * @model annotation="gmf.node label='name'"
 * @generated
 */
public interface CommitmentTask extends Task {
	/**
	 * Returns the value of the '<em><b>Commit After</b></em>' attribute.
	 * The default value is <code>"1"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Commit After</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Commit After</em>' attribute.
	 * @see #setCommitAfter(int)
	 * @see crossflow.CrossflowPackage#getCommitmentTask_CommitAfter()
	 * @model default="1"
	 * @generated
	 */
	int getCommitAfter();

	/**
	 * Sets the value of the '{@link crossflow.CommitmentTask#getCommitAfter <em>Commit After</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Commit After</em>' attribute.
	 * @see #getCommitAfter()
	 * @generated
	 */
	void setCommitAfter(int value);

} // CommitmentTask
