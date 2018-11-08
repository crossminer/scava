/**
 */
package crossflow.impl;

import crossflow.Configuration;
import crossflow.CrossflowPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link crossflow.impl.ConfigurationImpl#getNumberOfWorkers <em>Number Of Workers</em>}</li>
 *   <li>{@link crossflow.impl.ConfigurationImpl#getIsMasterAlsoWorker <em>Is Master Also Worker</em>}</li>
 *   <li>{@link crossflow.impl.ConfigurationImpl#getRootPackageName <em>Root Package Name</em>}</li>
 *   <li>{@link crossflow.impl.ConfigurationImpl#getProjectName <em>Project Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ConfigurationImpl extends EObjectImpl implements Configuration {
	/**
	 * The default value of the '{@link #getNumberOfWorkers() <em>Number Of Workers</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberOfWorkers()
	 * @generated
	 * @ordered
	 */
	protected static final Integer NUMBER_OF_WORKERS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getNumberOfWorkers() <em>Number Of Workers</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberOfWorkers()
	 * @generated
	 * @ordered
	 */
	protected Integer numberOfWorkers = NUMBER_OF_WORKERS_EDEFAULT;

	/**
	 * The default value of the '{@link #getIsMasterAlsoWorker() <em>Is Master Also Worker</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIsMasterAlsoWorker()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean IS_MASTER_ALSO_WORKER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIsMasterAlsoWorker() <em>Is Master Also Worker</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIsMasterAlsoWorker()
	 * @generated
	 * @ordered
	 */
	protected Boolean isMasterAlsoWorker = IS_MASTER_ALSO_WORKER_EDEFAULT;

	/**
	 * The default value of the '{@link #getRootPackageName() <em>Root Package Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRootPackageName()
	 * @generated
	 * @ordered
	 */
	protected static final String ROOT_PACKAGE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRootPackageName() <em>Root Package Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRootPackageName()
	 * @generated
	 * @ordered
	 */
	protected String rootPackageName = ROOT_PACKAGE_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getProjectName() <em>Project Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProjectName()
	 * @generated
	 * @ordered
	 */
	protected static final String PROJECT_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProjectName() <em>Project Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProjectName()
	 * @generated
	 * @ordered
	 */
	protected String projectName = PROJECT_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConfigurationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CrossflowPackage.Literals.CONFIGURATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Integer getNumberOfWorkers() {
		return numberOfWorkers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNumberOfWorkers(Integer newNumberOfWorkers) {
		Integer oldNumberOfWorkers = numberOfWorkers;
		numberOfWorkers = newNumberOfWorkers;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CrossflowPackage.CONFIGURATION__NUMBER_OF_WORKERS, oldNumberOfWorkers, numberOfWorkers));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Boolean getIsMasterAlsoWorker() {
		return isMasterAlsoWorker;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsMasterAlsoWorker(Boolean newIsMasterAlsoWorker) {
		Boolean oldIsMasterAlsoWorker = isMasterAlsoWorker;
		isMasterAlsoWorker = newIsMasterAlsoWorker;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CrossflowPackage.CONFIGURATION__IS_MASTER_ALSO_WORKER, oldIsMasterAlsoWorker, isMasterAlsoWorker));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getRootPackageName() {
		return rootPackageName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRootPackageName(String newRootPackageName) {
		String oldRootPackageName = rootPackageName;
		rootPackageName = newRootPackageName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CrossflowPackage.CONFIGURATION__ROOT_PACKAGE_NAME, oldRootPackageName, rootPackageName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProjectName(String newProjectName) {
		String oldProjectName = projectName;
		projectName = newProjectName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CrossflowPackage.CONFIGURATION__PROJECT_NAME, oldProjectName, projectName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CrossflowPackage.CONFIGURATION__NUMBER_OF_WORKERS:
				return getNumberOfWorkers();
			case CrossflowPackage.CONFIGURATION__IS_MASTER_ALSO_WORKER:
				return getIsMasterAlsoWorker();
			case CrossflowPackage.CONFIGURATION__ROOT_PACKAGE_NAME:
				return getRootPackageName();
			case CrossflowPackage.CONFIGURATION__PROJECT_NAME:
				return getProjectName();
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
			case CrossflowPackage.CONFIGURATION__NUMBER_OF_WORKERS:
				setNumberOfWorkers((Integer)newValue);
				return;
			case CrossflowPackage.CONFIGURATION__IS_MASTER_ALSO_WORKER:
				setIsMasterAlsoWorker((Boolean)newValue);
				return;
			case CrossflowPackage.CONFIGURATION__ROOT_PACKAGE_NAME:
				setRootPackageName((String)newValue);
				return;
			case CrossflowPackage.CONFIGURATION__PROJECT_NAME:
				setProjectName((String)newValue);
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
			case CrossflowPackage.CONFIGURATION__NUMBER_OF_WORKERS:
				setNumberOfWorkers(NUMBER_OF_WORKERS_EDEFAULT);
				return;
			case CrossflowPackage.CONFIGURATION__IS_MASTER_ALSO_WORKER:
				setIsMasterAlsoWorker(IS_MASTER_ALSO_WORKER_EDEFAULT);
				return;
			case CrossflowPackage.CONFIGURATION__ROOT_PACKAGE_NAME:
				setRootPackageName(ROOT_PACKAGE_NAME_EDEFAULT);
				return;
			case CrossflowPackage.CONFIGURATION__PROJECT_NAME:
				setProjectName(PROJECT_NAME_EDEFAULT);
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
			case CrossflowPackage.CONFIGURATION__NUMBER_OF_WORKERS:
				return NUMBER_OF_WORKERS_EDEFAULT == null ? numberOfWorkers != null : !NUMBER_OF_WORKERS_EDEFAULT.equals(numberOfWorkers);
			case CrossflowPackage.CONFIGURATION__IS_MASTER_ALSO_WORKER:
				return IS_MASTER_ALSO_WORKER_EDEFAULT == null ? isMasterAlsoWorker != null : !IS_MASTER_ALSO_WORKER_EDEFAULT.equals(isMasterAlsoWorker);
			case CrossflowPackage.CONFIGURATION__ROOT_PACKAGE_NAME:
				return ROOT_PACKAGE_NAME_EDEFAULT == null ? rootPackageName != null : !ROOT_PACKAGE_NAME_EDEFAULT.equals(rootPackageName);
			case CrossflowPackage.CONFIGURATION__PROJECT_NAME:
				return PROJECT_NAME_EDEFAULT == null ? projectName != null : !PROJECT_NAME_EDEFAULT.equals(projectName);
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

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (numberOfWorkers: ");
		result.append(numberOfWorkers);
		result.append(", isMasterAlsoWorker: ");
		result.append(isMasterAlsoWorker);
		result.append(", rootPackageName: ");
		result.append(rootPackageName);
		result.append(", projectName: ");
		result.append(projectName);
		result.append(')');
		return result.toString();
	}

} //ConfigurationImpl
