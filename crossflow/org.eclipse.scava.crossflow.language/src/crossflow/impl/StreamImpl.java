/**
 */
package crossflow.impl;

import crossflow.CrossflowPackage;
import crossflow.Stream;
import crossflow.Task;
import crossflow.Type;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Stream</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link crossflow.impl.StreamImpl#getName <em>Name</em>}</li>
 *   <li>{@link crossflow.impl.StreamImpl#getType <em>Type</em>}</li>
 *   <li>{@link crossflow.impl.StreamImpl#getInputOf <em>Input Of</em>}</li>
 *   <li>{@link crossflow.impl.StreamImpl#getOutputOf <em>Output Of</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class StreamImpl extends EObjectImpl implements Stream {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected Type type;

	/**
	 * The cached value of the '{@link #getInputOf() <em>Input Of</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInputOf()
	 * @generated
	 * @ordered
	 */
	protected EList<Task> inputOf;

	/**
	 * The cached value of the '{@link #getOutputOf() <em>Output Of</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutputOf()
	 * @generated
	 * @ordered
	 */
	protected EList<Task> outputOf;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StreamImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CrossflowPackage.Literals.STREAM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CrossflowPackage.STREAM__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Type getType() {
		if (type != null && type.eIsProxy()) {
			InternalEObject oldType = (InternalEObject)type;
			type = (Type)eResolveProxy(oldType);
			if (type != oldType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CrossflowPackage.STREAM__TYPE, oldType, type));
			}
		}
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Type basicGetType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(Type newType) {
		Type oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CrossflowPackage.STREAM__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Task> getInputOf() {
		if (inputOf == null) {
			inputOf = new EObjectWithInverseResolvingEList.ManyInverse<Task>(Task.class, this, CrossflowPackage.STREAM__INPUT_OF, CrossflowPackage.TASK__INPUT);
		}
		return inputOf;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Task> getOutputOf() {
		if (outputOf == null) {
			outputOf = new EObjectWithInverseResolvingEList.ManyInverse<Task>(Task.class, this, CrossflowPackage.STREAM__OUTPUT_OF, CrossflowPackage.TASK__OUTPUT);
		}
		return outputOf;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CrossflowPackage.STREAM__INPUT_OF:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getInputOf()).basicAdd(otherEnd, msgs);
			case CrossflowPackage.STREAM__OUTPUT_OF:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getOutputOf()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CrossflowPackage.STREAM__INPUT_OF:
				return ((InternalEList<?>)getInputOf()).basicRemove(otherEnd, msgs);
			case CrossflowPackage.STREAM__OUTPUT_OF:
				return ((InternalEList<?>)getOutputOf()).basicRemove(otherEnd, msgs);
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
			case CrossflowPackage.STREAM__NAME:
				return getName();
			case CrossflowPackage.STREAM__TYPE:
				if (resolve) return getType();
				return basicGetType();
			case CrossflowPackage.STREAM__INPUT_OF:
				return getInputOf();
			case CrossflowPackage.STREAM__OUTPUT_OF:
				return getOutputOf();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case CrossflowPackage.STREAM__NAME:
				setName((String)newValue);
				return;
			case CrossflowPackage.STREAM__TYPE:
				setType((Type)newValue);
				return;
			case CrossflowPackage.STREAM__INPUT_OF:
				getInputOf().clear();
				getInputOf().addAll((Collection<? extends Task>)newValue);
				return;
			case CrossflowPackage.STREAM__OUTPUT_OF:
				getOutputOf().clear();
				getOutputOf().addAll((Collection<? extends Task>)newValue);
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
			case CrossflowPackage.STREAM__NAME:
				setName(NAME_EDEFAULT);
				return;
			case CrossflowPackage.STREAM__TYPE:
				setType((Type)null);
				return;
			case CrossflowPackage.STREAM__INPUT_OF:
				getInputOf().clear();
				return;
			case CrossflowPackage.STREAM__OUTPUT_OF:
				getOutputOf().clear();
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
			case CrossflowPackage.STREAM__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case CrossflowPackage.STREAM__TYPE:
				return type != null;
			case CrossflowPackage.STREAM__INPUT_OF:
				return inputOf != null && !inputOf.isEmpty();
			case CrossflowPackage.STREAM__OUTPUT_OF:
				return outputOf != null && !outputOf.isEmpty();
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
		result.append(" (name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //StreamImpl
