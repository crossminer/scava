/**
 */
package crossflowComponents.impl;

import crossflow.CrossflowPackage;

import crossflow.impl.CrossflowPackageImpl;

import crossflowComponents.Component;
import crossflowComponents.ComponentTypes;
import crossflowComponents.CrossflowComponentsFactory;
import crossflowComponents.CrossflowComponentsPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class CrossflowComponentsPackageImpl extends EPackageImpl implements CrossflowComponentsPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass componentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum componentTypesEEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see crossflowComponents.CrossflowComponentsPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private CrossflowComponentsPackageImpl() {
		super(eNS_URI, CrossflowComponentsFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link CrossflowComponentsPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static CrossflowComponentsPackage init() {
		if (isInited) return (CrossflowComponentsPackage)EPackage.Registry.INSTANCE.getEPackage(CrossflowComponentsPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredCrossflowComponentsPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		CrossflowComponentsPackageImpl theCrossflowComponentsPackage = registeredCrossflowComponentsPackage instanceof CrossflowComponentsPackageImpl ? (CrossflowComponentsPackageImpl)registeredCrossflowComponentsPackage : new CrossflowComponentsPackageImpl();

		isInited = true;

		// Obtain or create and register interdependencies
		Object registeredPackage = EPackage.Registry.INSTANCE.getEPackage(CrossflowPackage.eNS_URI);
		CrossflowPackageImpl theCrossflowPackage = (CrossflowPackageImpl)(registeredPackage instanceof CrossflowPackageImpl ? registeredPackage : CrossflowPackage.eINSTANCE);

		// Create package meta-data objects
		theCrossflowComponentsPackage.createPackageContents();
		theCrossflowPackage.createPackageContents();

		// Initialize created meta-data
		theCrossflowComponentsPackage.initializePackageContents();
		theCrossflowPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theCrossflowComponentsPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(CrossflowComponentsPackage.eNS_URI, theCrossflowComponentsPackage);
		return theCrossflowComponentsPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getComponent() {
		return componentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getComponent_FullyQualifiedName() {
		return (EAttribute)componentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getComponent_Language() {
		return (EReference)componentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getComponent_Consumes() {
		return (EReference)componentEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getComponent_Produces() {
		return (EReference)componentEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getComponent_MultipleOutputs() {
		return (EAttribute)componentEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getComponent_ComponentType() {
		return (EAttribute)componentEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getComponentTypes() {
		return componentTypesEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CrossflowComponentsFactory getCrossflowComponentsFactory() {
		return (CrossflowComponentsFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		componentEClass = createEClass(COMPONENT);
		createEAttribute(componentEClass, COMPONENT__FULLY_QUALIFIED_NAME);
		createEReference(componentEClass, COMPONENT__LANGUAGE);
		createEReference(componentEClass, COMPONENT__CONSUMES);
		createEReference(componentEClass, COMPONENT__PRODUCES);
		createEAttribute(componentEClass, COMPONENT__MULTIPLE_OUTPUTS);
		createEAttribute(componentEClass, COMPONENT__COMPONENT_TYPE);

		// Create enums
		componentTypesEEnum = createEEnum(COMPONENT_TYPES);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		CrossflowPackage theCrossflowPackage = (CrossflowPackage)EPackage.Registry.INSTANCE.getEPackage(CrossflowPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(componentEClass, Component.class, "Component", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getComponent_FullyQualifiedName(), ecorePackage.getEString(), "fullyQualifiedName", null, 1, 1, Component.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getComponent_Language(), theCrossflowPackage.getLanguage(), null, "language", null, 0, 1, Component.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getComponent_Consumes(), theCrossflowPackage.getType(), null, "consumes", null, 1, 1, Component.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getComponent_Produces(), theCrossflowPackage.getType(), null, "produces", null, 1, 1, Component.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getComponent_MultipleOutputs(), ecorePackage.getEBoolean(), "multipleOutputs", null, 0, 1, Component.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getComponent_ComponentType(), this.getComponentTypes(), "componentType", null, 0, 1, Component.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(componentTypesEEnum, ComponentTypes.class, "ComponentTypes");
		addEEnumLiteral(componentTypesEEnum, ComponentTypes.NONE);
		addEEnumLiteral(componentTypesEEnum, ComponentTypes.OPINIONATED);
		addEEnumLiteral(componentTypesEEnum, ComponentTypes.COMMITMENT);

		// Create resource
		createResource(eNS_URI);
	}

} //CrossflowComponentsPackageImpl
