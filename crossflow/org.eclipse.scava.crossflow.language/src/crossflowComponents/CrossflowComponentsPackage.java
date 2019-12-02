/**
 */
package crossflowComponents;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see crossflowComponents.CrossflowComponentsFactory
 * @model kind="package"
 * @generated
 */
public interface CrossflowComponentsPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "crossflowComponents";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "org.eclipse.scava.crossflow.components";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "cfc";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CrossflowComponentsPackage eINSTANCE = crossflowComponents.impl.CrossflowComponentsPackageImpl.init();

	/**
	 * The meta object id for the '{@link crossflowComponents.impl.ComponentImpl <em>Component</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see crossflowComponents.impl.ComponentImpl
	 * @see crossflowComponents.impl.CrossflowComponentsPackageImpl#getComponent()
	 * @generated
	 */
	int COMPONENT = 0;

	/**
	 * The feature id for the '<em><b>Fully Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__FULLY_QUALIFIED_NAME = 0;

	/**
	 * The feature id for the '<em><b>Language</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__LANGUAGE = 1;

	/**
	 * The feature id for the '<em><b>Consumes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__CONSUMES = 2;

	/**
	 * The feature id for the '<em><b>Produces</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__PRODUCES = 3;

	/**
	 * The feature id for the '<em><b>Multiple Outputs</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__MULTIPLE_OUTPUTS = 4;

	/**
	 * The feature id for the '<em><b>Component Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__COMPONENT_TYPE = 5;

	/**
	 * The number of structural features of the '<em>Component</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_FEATURE_COUNT = 6;

	/**
	 * The meta object id for the '{@link crossflowComponents.ComponentTypes <em>Component Types</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see crossflowComponents.ComponentTypes
	 * @see crossflowComponents.impl.CrossflowComponentsPackageImpl#getComponentTypes()
	 * @generated
	 */
	int COMPONENT_TYPES = 1;


	/**
	 * Returns the meta object for class '{@link crossflowComponents.Component <em>Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Component</em>'.
	 * @see crossflowComponents.Component
	 * @generated
	 */
	EClass getComponent();

	/**
	 * Returns the meta object for the attribute '{@link crossflowComponents.Component#getFullyQualifiedName <em>Fully Qualified Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Fully Qualified Name</em>'.
	 * @see crossflowComponents.Component#getFullyQualifiedName()
	 * @see #getComponent()
	 * @generated
	 */
	EAttribute getComponent_FullyQualifiedName();

	/**
	 * Returns the meta object for the reference '{@link crossflowComponents.Component#getLanguage <em>Language</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Language</em>'.
	 * @see crossflowComponents.Component#getLanguage()
	 * @see #getComponent()
	 * @generated
	 */
	EReference getComponent_Language();

	/**
	 * Returns the meta object for the containment reference '{@link crossflowComponents.Component#getConsumes <em>Consumes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Consumes</em>'.
	 * @see crossflowComponents.Component#getConsumes()
	 * @see #getComponent()
	 * @generated
	 */
	EReference getComponent_Consumes();

	/**
	 * Returns the meta object for the containment reference '{@link crossflowComponents.Component#getProduces <em>Produces</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Produces</em>'.
	 * @see crossflowComponents.Component#getProduces()
	 * @see #getComponent()
	 * @generated
	 */
	EReference getComponent_Produces();

	/**
	 * Returns the meta object for the attribute '{@link crossflowComponents.Component#isMultipleOutputs <em>Multiple Outputs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Multiple Outputs</em>'.
	 * @see crossflowComponents.Component#isMultipleOutputs()
	 * @see #getComponent()
	 * @generated
	 */
	EAttribute getComponent_MultipleOutputs();

	/**
	 * Returns the meta object for the attribute '{@link crossflowComponents.Component#getComponentType <em>Component Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Component Type</em>'.
	 * @see crossflowComponents.Component#getComponentType()
	 * @see #getComponent()
	 * @generated
	 */
	EAttribute getComponent_ComponentType();

	/**
	 * Returns the meta object for enum '{@link crossflowComponents.ComponentTypes <em>Component Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Component Types</em>'.
	 * @see crossflowComponents.ComponentTypes
	 * @generated
	 */
	EEnum getComponentTypes();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	CrossflowComponentsFactory getCrossflowComponentsFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link crossflowComponents.impl.ComponentImpl <em>Component</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see crossflowComponents.impl.ComponentImpl
		 * @see crossflowComponents.impl.CrossflowComponentsPackageImpl#getComponent()
		 * @generated
		 */
		EClass COMPONENT = eINSTANCE.getComponent();

		/**
		 * The meta object literal for the '<em><b>Fully Qualified Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPONENT__FULLY_QUALIFIED_NAME = eINSTANCE.getComponent_FullyQualifiedName();

		/**
		 * The meta object literal for the '<em><b>Language</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPONENT__LANGUAGE = eINSTANCE.getComponent_Language();

		/**
		 * The meta object literal for the '<em><b>Consumes</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPONENT__CONSUMES = eINSTANCE.getComponent_Consumes();

		/**
		 * The meta object literal for the '<em><b>Produces</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPONENT__PRODUCES = eINSTANCE.getComponent_Produces();

		/**
		 * The meta object literal for the '<em><b>Multiple Outputs</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPONENT__MULTIPLE_OUTPUTS = eINSTANCE.getComponent_MultipleOutputs();

		/**
		 * The meta object literal for the '<em><b>Component Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPONENT__COMPONENT_TYPE = eINSTANCE.getComponent_ComponentType();

		/**
		 * The meta object literal for the '{@link crossflowComponents.ComponentTypes <em>Component Types</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see crossflowComponents.ComponentTypes
		 * @see crossflowComponents.impl.CrossflowComponentsPackageImpl#getComponentTypes()
		 * @generated
		 */
		EEnum COMPONENT_TYPES = eINSTANCE.getComponentTypes();

	}

} //CrossflowComponentsPackage
