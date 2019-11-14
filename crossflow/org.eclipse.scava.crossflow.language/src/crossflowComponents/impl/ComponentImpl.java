/**
 */
package crossflowComponents.impl;

import crossflow.Language;
import crossflow.Type;

import crossflowComponents.Component;
import crossflowComponents.ComponentTypes;
import crossflowComponents.CrossflowComponentsPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Component</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link crossflowComponents.impl.ComponentImpl#getFullyQualifiedName <em>Fully Qualified Name</em>}</li>
 *   <li>{@link crossflowComponents.impl.ComponentImpl#getLanguage <em>Language</em>}</li>
 *   <li>{@link crossflowComponents.impl.ComponentImpl#getConsumes <em>Consumes</em>}</li>
 *   <li>{@link crossflowComponents.impl.ComponentImpl#getProduces <em>Produces</em>}</li>
 *   <li>{@link crossflowComponents.impl.ComponentImpl#isMultipleOutputs <em>Multiple Outputs</em>}</li>
 *   <li>{@link crossflowComponents.impl.ComponentImpl#getComponentType <em>Component Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ComponentImpl extends EObjectImpl implements Component {
	/**
	 * The default value of the '{@link #getFullyQualifiedName() <em>Fully Qualified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFullyQualifiedName()
	 * @generated
	 * @ordered
	 */
	protected static final String FULLY_QUALIFIED_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFullyQualifiedName() <em>Fully Qualified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFullyQualifiedName()
	 * @generated
	 * @ordered
	 */
	protected String fullyQualifiedName = FULLY_QUALIFIED_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getLanguage() <em>Language</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLanguage()
	 * @generated
	 * @ordered
	 */
	protected Language language;

	/**
	 * The cached value of the '{@link #getConsumes() <em>Consumes</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConsumes()
	 * @generated
	 * @ordered
	 */
	protected Type consumes;

	/**
	 * The cached value of the '{@link #getProduces() <em>Produces</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProduces()
	 * @generated
	 * @ordered
	 */
	protected Type produces;

	/**
	 * The default value of the '{@link #isMultipleOutputs() <em>Multiple Outputs</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMultipleOutputs()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MULTIPLE_OUTPUTS_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isMultipleOutputs() <em>Multiple Outputs</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMultipleOutputs()
	 * @generated
	 * @ordered
	 */
	protected boolean multipleOutputs = MULTIPLE_OUTPUTS_EDEFAULT;

	/**
	 * The default value of the '{@link #getComponentType() <em>Component Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComponentType()
	 * @generated
	 * @ordered
	 */
	protected static final ComponentTypes COMPONENT_TYPE_EDEFAULT = ComponentTypes.NONE;

	/**
	 * The cached value of the '{@link #getComponentType() <em>Component Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComponentType()
	 * @generated
	 * @ordered
	 */
	protected ComponentTypes componentType = COMPONENT_TYPE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ComponentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CrossflowComponentsPackage.Literals.COMPONENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFullyQualifiedName() {
		return fullyQualifiedName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFullyQualifiedName(String newFullyQualifiedName) {
		String oldFullyQualifiedName = fullyQualifiedName;
		fullyQualifiedName = newFullyQualifiedName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CrossflowComponentsPackage.COMPONENT__FULLY_QUALIFIED_NAME, oldFullyQualifiedName, fullyQualifiedName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Language getLanguage() {
		if (language != null && language.eIsProxy()) {
			InternalEObject oldLanguage = (InternalEObject)language;
			language = (Language)eResolveProxy(oldLanguage);
			if (language != oldLanguage) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CrossflowComponentsPackage.COMPONENT__LANGUAGE, oldLanguage, language));
			}
		}
		return language;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Language basicGetLanguage() {
		return language;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLanguage(Language newLanguage) {
		Language oldLanguage = language;
		language = newLanguage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CrossflowComponentsPackage.COMPONENT__LANGUAGE, oldLanguage, language));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Type getConsumes() {
		return consumes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetConsumes(Type newConsumes, NotificationChain msgs) {
		Type oldConsumes = consumes;
		consumes = newConsumes;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CrossflowComponentsPackage.COMPONENT__CONSUMES, oldConsumes, newConsumes);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConsumes(Type newConsumes) {
		if (newConsumes != consumes) {
			NotificationChain msgs = null;
			if (consumes != null)
				msgs = ((InternalEObject)consumes).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CrossflowComponentsPackage.COMPONENT__CONSUMES, null, msgs);
			if (newConsumes != null)
				msgs = ((InternalEObject)newConsumes).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CrossflowComponentsPackage.COMPONENT__CONSUMES, null, msgs);
			msgs = basicSetConsumes(newConsumes, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CrossflowComponentsPackage.COMPONENT__CONSUMES, newConsumes, newConsumes));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Type getProduces() {
		return produces;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProduces(Type newProduces, NotificationChain msgs) {
		Type oldProduces = produces;
		produces = newProduces;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CrossflowComponentsPackage.COMPONENT__PRODUCES, oldProduces, newProduces);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProduces(Type newProduces) {
		if (newProduces != produces) {
			NotificationChain msgs = null;
			if (produces != null)
				msgs = ((InternalEObject)produces).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CrossflowComponentsPackage.COMPONENT__PRODUCES, null, msgs);
			if (newProduces != null)
				msgs = ((InternalEObject)newProduces).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CrossflowComponentsPackage.COMPONENT__PRODUCES, null, msgs);
			msgs = basicSetProduces(newProduces, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CrossflowComponentsPackage.COMPONENT__PRODUCES, newProduces, newProduces));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isMultipleOutputs() {
		return multipleOutputs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMultipleOutputs(boolean newMultipleOutputs) {
		boolean oldMultipleOutputs = multipleOutputs;
		multipleOutputs = newMultipleOutputs;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CrossflowComponentsPackage.COMPONENT__MULTIPLE_OUTPUTS, oldMultipleOutputs, multipleOutputs));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComponentTypes getComponentType() {
		return componentType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComponentType(ComponentTypes newComponentType) {
		ComponentTypes oldComponentType = componentType;
		componentType = newComponentType == null ? COMPONENT_TYPE_EDEFAULT : newComponentType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CrossflowComponentsPackage.COMPONENT__COMPONENT_TYPE, oldComponentType, componentType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CrossflowComponentsPackage.COMPONENT__CONSUMES:
				return basicSetConsumes(null, msgs);
			case CrossflowComponentsPackage.COMPONENT__PRODUCES:
				return basicSetProduces(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CrossflowComponentsPackage.COMPONENT__FULLY_QUALIFIED_NAME:
				return getFullyQualifiedName();
			case CrossflowComponentsPackage.COMPONENT__LANGUAGE:
				if (resolve) return getLanguage();
				return basicGetLanguage();
			case CrossflowComponentsPackage.COMPONENT__CONSUMES:
				return getConsumes();
			case CrossflowComponentsPackage.COMPONENT__PRODUCES:
				return getProduces();
			case CrossflowComponentsPackage.COMPONENT__MULTIPLE_OUTPUTS:
				return isMultipleOutputs();
			case CrossflowComponentsPackage.COMPONENT__COMPONENT_TYPE:
				return getComponentType();
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
			case CrossflowComponentsPackage.COMPONENT__FULLY_QUALIFIED_NAME:
				setFullyQualifiedName((String)newValue);
				return;
			case CrossflowComponentsPackage.COMPONENT__LANGUAGE:
				setLanguage((Language)newValue);
				return;
			case CrossflowComponentsPackage.COMPONENT__CONSUMES:
				setConsumes((Type)newValue);
				return;
			case CrossflowComponentsPackage.COMPONENT__PRODUCES:
				setProduces((Type)newValue);
				return;
			case CrossflowComponentsPackage.COMPONENT__MULTIPLE_OUTPUTS:
				setMultipleOutputs((Boolean)newValue);
				return;
			case CrossflowComponentsPackage.COMPONENT__COMPONENT_TYPE:
				setComponentType((ComponentTypes)newValue);
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
			case CrossflowComponentsPackage.COMPONENT__FULLY_QUALIFIED_NAME:
				setFullyQualifiedName(FULLY_QUALIFIED_NAME_EDEFAULT);
				return;
			case CrossflowComponentsPackage.COMPONENT__LANGUAGE:
				setLanguage((Language)null);
				return;
			case CrossflowComponentsPackage.COMPONENT__CONSUMES:
				setConsumes((Type)null);
				return;
			case CrossflowComponentsPackage.COMPONENT__PRODUCES:
				setProduces((Type)null);
				return;
			case CrossflowComponentsPackage.COMPONENT__MULTIPLE_OUTPUTS:
				setMultipleOutputs(MULTIPLE_OUTPUTS_EDEFAULT);
				return;
			case CrossflowComponentsPackage.COMPONENT__COMPONENT_TYPE:
				setComponentType(COMPONENT_TYPE_EDEFAULT);
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
			case CrossflowComponentsPackage.COMPONENT__FULLY_QUALIFIED_NAME:
				return FULLY_QUALIFIED_NAME_EDEFAULT == null ? fullyQualifiedName != null : !FULLY_QUALIFIED_NAME_EDEFAULT.equals(fullyQualifiedName);
			case CrossflowComponentsPackage.COMPONENT__LANGUAGE:
				return language != null;
			case CrossflowComponentsPackage.COMPONENT__CONSUMES:
				return consumes != null;
			case CrossflowComponentsPackage.COMPONENT__PRODUCES:
				return produces != null;
			case CrossflowComponentsPackage.COMPONENT__MULTIPLE_OUTPUTS:
				return multipleOutputs != MULTIPLE_OUTPUTS_EDEFAULT;
			case CrossflowComponentsPackage.COMPONENT__COMPONENT_TYPE:
				return componentType != COMPONENT_TYPE_EDEFAULT;
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
		result.append(" (fullyQualifiedName: ");
		result.append(fullyQualifiedName);
		result.append(", multipleOutputs: ");
		result.append(multipleOutputs);
		result.append(", componentType: ");
		result.append(componentType);
		result.append(')');
		return result.toString();
	}

} //ComponentImpl
