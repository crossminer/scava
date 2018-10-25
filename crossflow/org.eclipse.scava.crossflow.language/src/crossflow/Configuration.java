/**
 */
package crossflow;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Configuration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link crossflow.Configuration#getNumberOfWorkers <em>Number Of Workers</em>}</li>
 *   <li>{@link crossflow.Configuration#getIsMasterAlsoWorker <em>Is Master Also Worker</em>}</li>
 *   <li>{@link crossflow.Configuration#getRootPackageName <em>Root Package Name</em>}</li>
 *   <li>{@link crossflow.Configuration#getProjectName <em>Project Name</em>}</li>
 * </ul>
 *
 * @see crossflow.CrossflowPackage#getConfiguration()
 * @model annotation="gmf.node label='numberOfWorkers,isMasterAlsoWorker' label.pattern='Config: NoW:{0}, iMaW:{1}'"
 * @generated
 */
public interface Configuration extends EObject {
	/**
	 * Returns the value of the '<em><b>Number Of Workers</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Number Of Workers</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Number Of Workers</em>' attribute.
	 * @see #setNumberOfWorkers(Integer)
	 * @see crossflow.CrossflowPackage#getConfiguration_NumberOfWorkers()
	 * @model
	 * @generated
	 */
	Integer getNumberOfWorkers();

	/**
	 * Sets the value of the '{@link crossflow.Configuration#getNumberOfWorkers <em>Number Of Workers</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Number Of Workers</em>' attribute.
	 * @see #getNumberOfWorkers()
	 * @generated
	 */
	void setNumberOfWorkers(Integer value);

	/**
	 * Returns the value of the '<em><b>Is Master Also Worker</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Is Master Also Worker</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Master Also Worker</em>' attribute.
	 * @see #setIsMasterAlsoWorker(Boolean)
	 * @see crossflow.CrossflowPackage#getConfiguration_IsMasterAlsoWorker()
	 * @model
	 * @generated
	 */
	Boolean getIsMasterAlsoWorker();

	/**
	 * Sets the value of the '{@link crossflow.Configuration#getIsMasterAlsoWorker <em>Is Master Also Worker</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Master Also Worker</em>' attribute.
	 * @see #getIsMasterAlsoWorker()
	 * @generated
	 */
	void setIsMasterAlsoWorker(Boolean value);

	/**
	 * Returns the value of the '<em><b>Root Package Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Root Package Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Root Package Name</em>' attribute.
	 * @see #setRootPackageName(String)
	 * @see crossflow.CrossflowPackage#getConfiguration_RootPackageName()
	 * @model required="true"
	 * @generated
	 */
	String getRootPackageName();

	/**
	 * Sets the value of the '{@link crossflow.Configuration#getRootPackageName <em>Root Package Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Root Package Name</em>' attribute.
	 * @see #getRootPackageName()
	 * @generated
	 */
	void setRootPackageName(String value);

	/**
	 * Returns the value of the '<em><b>Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Project Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Project Name</em>' attribute.
	 * @see #setProjectName(String)
	 * @see crossflow.CrossflowPackage#getConfiguration_ProjectName()
	 * @model required="true"
	 * @generated
	 */
	String getProjectName();

	/**
	 * Sets the value of the '{@link crossflow.Configuration#getProjectName <em>Project Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Project Name</em>' attribute.
	 * @see #getProjectName()
	 * @generated
	 */
	void setProjectName(String value);

} // Configuration
