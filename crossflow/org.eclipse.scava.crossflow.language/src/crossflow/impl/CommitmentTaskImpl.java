/**
 */
package crossflow.impl;

import crossflow.CommitmentTask;
import crossflow.CrossflowPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Commitment Task</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link crossflow.impl.CommitmentTaskImpl#getCommitAfter <em>Commit After</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CommitmentTaskImpl extends TaskImpl implements CommitmentTask {
	/**
	 * The default value of the '{@link #getCommitAfter() <em>Commit After</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCommitAfter()
	 * @generated
	 * @ordered
	 */
	protected static final int COMMIT_AFTER_EDEFAULT = 1;

	/**
	 * The cached value of the '{@link #getCommitAfter() <em>Commit After</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCommitAfter()
	 * @generated
	 * @ordered
	 */
	protected int commitAfter = COMMIT_AFTER_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CommitmentTaskImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CrossflowPackage.Literals.COMMITMENT_TASK;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getCommitAfter() {
		return commitAfter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCommitAfter(int newCommitAfter) {
		int oldCommitAfter = commitAfter;
		commitAfter = newCommitAfter;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CrossflowPackage.COMMITMENT_TASK__COMMIT_AFTER, oldCommitAfter, commitAfter));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CrossflowPackage.COMMITMENT_TASK__COMMIT_AFTER:
				return getCommitAfter();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case CrossflowPackage.COMMITMENT_TASK__COMMIT_AFTER:
				setCommitAfter((Integer)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case CrossflowPackage.COMMITMENT_TASK__COMMIT_AFTER:
				setCommitAfter(COMMIT_AFTER_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case CrossflowPackage.COMMITMENT_TASK__COMMIT_AFTER:
				return commitAfter != COMMIT_AFTER_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (commitAfter: ");
		result.append(commitAfter);
		result.append(')');
		return result.toString();
	}

} //CommitmentTaskImpl
