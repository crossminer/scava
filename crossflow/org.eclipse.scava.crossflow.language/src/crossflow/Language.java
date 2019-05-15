/**
 */
package crossflow;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Language</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link crossflow.Language#getName <em>Name</em>}</li>
 *   <li>{@link crossflow.Language#getPackage <em>Package</em>}</li>
 *   <li>{@link crossflow.Language#getOutputFolder <em>Output Folder</em>}</li>
 *   <li>{@link crossflow.Language#getGenOutputFolder <em>Gen Output Folder</em>}</li>
 *   <li>{@link crossflow.Language#getParameters <em>Parameters</em>}</li>
 * </ul>
 *
 * @see crossflow.CrossflowPackage#getLanguage()
 * @model annotation="gmf.node label='name' figure='rectangle'"
 * @generated
 */
public interface Language extends EObject {
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
	 * @see crossflow.CrossflowPackage#getLanguage_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link crossflow.Language#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Package</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Package</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Package</em>' attribute.
	 * @see #setPackage(String)
	 * @see crossflow.CrossflowPackage#getLanguage_Package()
	 * @model
	 * @generated
	 */
	String getPackage();

	/**
	 * Sets the value of the '{@link crossflow.Language#getPackage <em>Package</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Package</em>' attribute.
	 * @see #getPackage()
	 * @generated
	 */
	void setPackage(String value);

	/**
	 * Returns the value of the '<em><b>Output Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Output Folder</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Output Folder</em>' attribute.
	 * @see #setOutputFolder(String)
	 * @see crossflow.CrossflowPackage#getLanguage_OutputFolder()
	 * @model
	 * @generated
	 */
	String getOutputFolder();

	/**
	 * Sets the value of the '{@link crossflow.Language#getOutputFolder <em>Output Folder</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Output Folder</em>' attribute.
	 * @see #getOutputFolder()
	 * @generated
	 */
	void setOutputFolder(String value);

	/**
	 * Returns the value of the '<em><b>Gen Output Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Gen Output Folder</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Gen Output Folder</em>' attribute.
	 * @see #setGenOutputFolder(String)
	 * @see crossflow.CrossflowPackage#getLanguage_GenOutputFolder()
	 * @model
	 * @generated
	 */
	String getGenOutputFolder();

	/**
	 * Sets the value of the '{@link crossflow.Language#getGenOutputFolder <em>Gen Output Folder</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Gen Output Folder</em>' attribute.
	 * @see #getGenOutputFolder()
	 * @generated
	 */
	void setGenOutputFolder(String value);

	/**
	 * Returns the value of the '<em><b>Parameters</b></em>' containment reference list.
	 * The list contents are of type {@link crossflow.Parameter}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameters</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameters</em>' containment reference list.
	 * @see crossflow.CrossflowPackage#getLanguage_Parameters()
	 * @model containment="true"
	 *        annotation="gmf.compartment layout='list' collapsible='false'"
	 * @generated
	 */
	EList<Parameter> getParameters();

} // Language
