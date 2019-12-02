/**
 */
package crossflow.impl;

import crossflow.CrossflowPackage;
import crossflow.Field;
import crossflow.Language;
import crossflow.Serializer;
import crossflow.Stream;
import crossflow.Task;
import crossflow.Type;
import crossflow.Workflow;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Workflow</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link crossflow.impl.WorkflowImpl#getName <em>Name</em>}</li>
 *   <li>{@link crossflow.impl.WorkflowImpl#getStreams <em>Streams</em>}</li>
 *   <li>{@link crossflow.impl.WorkflowImpl#getTasks <em>Tasks</em>}</li>
 *   <li>{@link crossflow.impl.WorkflowImpl#getTypes <em>Types</em>}</li>
 *   <li>{@link crossflow.impl.WorkflowImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link crossflow.impl.WorkflowImpl#getLanguages <em>Languages</em>}</li>
 *   <li>{@link crossflow.impl.WorkflowImpl#getSerializer <em>Serializer</em>}</li>
 * </ul>
 *
 * @generated
 */
public class WorkflowImpl extends EObjectImpl implements Workflow {
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
	 * The cached value of the '{@link #getStreams() <em>Streams</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStreams()
	 * @generated
	 * @ordered
	 */
	protected EList<Stream> streams;

	/**
	 * The cached value of the '{@link #getTasks() <em>Tasks</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTasks()
	 * @generated
	 * @ordered
	 */
	protected EList<Task> tasks;

	/**
	 * The cached value of the '{@link #getTypes() <em>Types</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypes()
	 * @generated
	 * @ordered
	 */
	protected EList<Type> types;

	/**
	 * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
	protected EList<Field> parameters;

	/**
	 * The cached value of the '{@link #getLanguages() <em>Languages</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLanguages()
	 * @generated
	 * @ordered
	 */
	protected EList<Language> languages;

	/**
	 * The cached value of the '{@link #getSerializer() <em>Serializer</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSerializer()
	 * @generated
	 * @ordered
	 */
	protected Serializer serializer;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected WorkflowImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CrossflowPackage.Literals.WORKFLOW;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CrossflowPackage.WORKFLOW__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Stream> getStreams() {
		if (streams == null) {
			streams = new EObjectContainmentEList<Stream>(Stream.class, this, CrossflowPackage.WORKFLOW__STREAMS);
		}
		return streams;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Task> getTasks() {
		if (tasks == null) {
			tasks = new EObjectContainmentEList<Task>(Task.class, this, CrossflowPackage.WORKFLOW__TASKS);
		}
		return tasks;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Type> getTypes() {
		if (types == null) {
			types = new EObjectContainmentEList<Type>(Type.class, this, CrossflowPackage.WORKFLOW__TYPES);
		}
		return types;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Field> getParameters() {
		if (parameters == null) {
			parameters = new EObjectContainmentEList<Field>(Field.class, this, CrossflowPackage.WORKFLOW__PARAMETERS);
		}
		return parameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Language> getLanguages() {
		if (languages == null) {
			languages = new EObjectContainmentEList<Language>(Language.class, this, CrossflowPackage.WORKFLOW__LANGUAGES);
		}
		return languages;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Serializer getSerializer() {
		return serializer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSerializer(Serializer newSerializer, NotificationChain msgs) {
		Serializer oldSerializer = serializer;
		serializer = newSerializer;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CrossflowPackage.WORKFLOW__SERIALIZER, oldSerializer, newSerializer);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSerializer(Serializer newSerializer) {
		if (newSerializer != serializer) {
			NotificationChain msgs = null;
			if (serializer != null)
				msgs = ((InternalEObject)serializer).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CrossflowPackage.WORKFLOW__SERIALIZER, null, msgs);
			if (newSerializer != null)
				msgs = ((InternalEObject)newSerializer).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CrossflowPackage.WORKFLOW__SERIALIZER, null, msgs);
			msgs = basicSetSerializer(newSerializer, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CrossflowPackage.WORKFLOW__SERIALIZER, newSerializer, newSerializer));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CrossflowPackage.WORKFLOW__STREAMS:
				return ((InternalEList<?>)getStreams()).basicRemove(otherEnd, msgs);
			case CrossflowPackage.WORKFLOW__TASKS:
				return ((InternalEList<?>)getTasks()).basicRemove(otherEnd, msgs);
			case CrossflowPackage.WORKFLOW__TYPES:
				return ((InternalEList<?>)getTypes()).basicRemove(otherEnd, msgs);
			case CrossflowPackage.WORKFLOW__PARAMETERS:
				return ((InternalEList<?>)getParameters()).basicRemove(otherEnd, msgs);
			case CrossflowPackage.WORKFLOW__LANGUAGES:
				return ((InternalEList<?>)getLanguages()).basicRemove(otherEnd, msgs);
			case CrossflowPackage.WORKFLOW__SERIALIZER:
				return basicSetSerializer(null, msgs);
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
			case CrossflowPackage.WORKFLOW__NAME:
				return getName();
			case CrossflowPackage.WORKFLOW__STREAMS:
				return getStreams();
			case CrossflowPackage.WORKFLOW__TASKS:
				return getTasks();
			case CrossflowPackage.WORKFLOW__TYPES:
				return getTypes();
			case CrossflowPackage.WORKFLOW__PARAMETERS:
				return getParameters();
			case CrossflowPackage.WORKFLOW__LANGUAGES:
				return getLanguages();
			case CrossflowPackage.WORKFLOW__SERIALIZER:
				return getSerializer();
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
			case CrossflowPackage.WORKFLOW__NAME:
				setName((String)newValue);
				return;
			case CrossflowPackage.WORKFLOW__STREAMS:
				getStreams().clear();
				getStreams().addAll((Collection<? extends Stream>)newValue);
				return;
			case CrossflowPackage.WORKFLOW__TASKS:
				getTasks().clear();
				getTasks().addAll((Collection<? extends Task>)newValue);
				return;
			case CrossflowPackage.WORKFLOW__TYPES:
				getTypes().clear();
				getTypes().addAll((Collection<? extends Type>)newValue);
				return;
			case CrossflowPackage.WORKFLOW__PARAMETERS:
				getParameters().clear();
				getParameters().addAll((Collection<? extends Field>)newValue);
				return;
			case CrossflowPackage.WORKFLOW__LANGUAGES:
				getLanguages().clear();
				getLanguages().addAll((Collection<? extends Language>)newValue);
				return;
			case CrossflowPackage.WORKFLOW__SERIALIZER:
				setSerializer((Serializer)newValue);
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
			case CrossflowPackage.WORKFLOW__NAME:
				setName(NAME_EDEFAULT);
				return;
			case CrossflowPackage.WORKFLOW__STREAMS:
				getStreams().clear();
				return;
			case CrossflowPackage.WORKFLOW__TASKS:
				getTasks().clear();
				return;
			case CrossflowPackage.WORKFLOW__TYPES:
				getTypes().clear();
				return;
			case CrossflowPackage.WORKFLOW__PARAMETERS:
				getParameters().clear();
				return;
			case CrossflowPackage.WORKFLOW__LANGUAGES:
				getLanguages().clear();
				return;
			case CrossflowPackage.WORKFLOW__SERIALIZER:
				setSerializer((Serializer)null);
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
			case CrossflowPackage.WORKFLOW__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case CrossflowPackage.WORKFLOW__STREAMS:
				return streams != null && !streams.isEmpty();
			case CrossflowPackage.WORKFLOW__TASKS:
				return tasks != null && !tasks.isEmpty();
			case CrossflowPackage.WORKFLOW__TYPES:
				return types != null && !types.isEmpty();
			case CrossflowPackage.WORKFLOW__PARAMETERS:
				return parameters != null && !parameters.isEmpty();
			case CrossflowPackage.WORKFLOW__LANGUAGES:
				return languages != null && !languages.isEmpty();
			case CrossflowPackage.WORKFLOW__SERIALIZER:
				return serializer != null;
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

} //WorkflowImpl
