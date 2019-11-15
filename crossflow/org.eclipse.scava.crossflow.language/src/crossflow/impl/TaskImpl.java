/**
 */
package crossflow.impl;

import crossflow.CrossflowPackage;
import crossflow.Field;
import crossflow.Language;
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

import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Task</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link crossflow.impl.TaskImpl#getName <em>Name</em>}</li>
 *   <li>{@link crossflow.impl.TaskImpl#getInput <em>Input</em>}</li>
 *   <li>{@link crossflow.impl.TaskImpl#getOutput <em>Output</em>}</li>
 *   <li>{@link crossflow.impl.TaskImpl#getMasterOnly <em>Master Only</em>}</li>
 *   <li>{@link crossflow.impl.TaskImpl#getParallel <em>Parallel</em>}</li>
 *   <li>{@link crossflow.impl.TaskImpl#getCached <em>Cached</em>}</li>
 *   <li>{@link crossflow.impl.TaskImpl#getMultipleOutputs <em>Multiple Outputs</em>}</li>
 *   <li>{@link crossflow.impl.TaskImpl#getImpl <em>Impl</em>}</li>
 *   <li>{@link crossflow.impl.TaskImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link crossflow.impl.TaskImpl#getLanguages <em>Languages</em>}</li>
 *   <li>{@link crossflow.impl.TaskImpl#getConfigurations <em>Configurations</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TaskImpl extends EObjectImpl implements Task {
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
	 * The cached value of the '{@link #getInput() <em>Input</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInput()
	 * @generated
	 * @ordered
	 */
	protected EList<Stream> input;

	/**
	 * The cached value of the '{@link #getOutput() <em>Output</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutput()
	 * @generated
	 * @ordered
	 */
	protected EList<Stream> output;

	/**
	 * The default value of the '{@link #getMasterOnly() <em>Master Only</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMasterOnly()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean MASTER_ONLY_EDEFAULT = Boolean.FALSE;

	/**
	 * The cached value of the '{@link #getMasterOnly() <em>Master Only</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMasterOnly()
	 * @generated
	 * @ordered
	 */
	protected Boolean masterOnly = MASTER_ONLY_EDEFAULT;

	/**
	 * The default value of the '{@link #getParallel() <em>Parallel</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParallel()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean PARALLEL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getParallel() <em>Parallel</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParallel()
	 * @generated
	 * @ordered
	 */
	protected Boolean parallel = PARALLEL_EDEFAULT;

	/**
	 * The default value of the '{@link #getCached() <em>Cached</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCached()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean CACHED_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCached() <em>Cached</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCached()
	 * @generated
	 * @ordered
	 */
	protected Boolean cached = CACHED_EDEFAULT;

	/**
	 * The default value of the '{@link #getMultipleOutputs() <em>Multiple Outputs</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMultipleOutputs()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean MULTIPLE_OUTPUTS_EDEFAULT = Boolean.FALSE;

	/**
	 * The cached value of the '{@link #getMultipleOutputs() <em>Multiple Outputs</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMultipleOutputs()
	 * @generated
	 * @ordered
	 */
	protected Boolean multipleOutputs = MULTIPLE_OUTPUTS_EDEFAULT;

	/**
	 * The default value of the '{@link #getImpl() <em>Impl</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImpl()
	 * @generated
	 * @ordered
	 */
	protected static final String IMPL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getImpl() <em>Impl</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImpl()
	 * @generated
	 * @ordered
	 */
	protected String impl = IMPL_EDEFAULT;

	/**
	 * The cached value of the '{@link #getParameters() <em>Parameters</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
	protected EList<Field> parameters;

	/**
	 * The cached value of the '{@link #getLanguages() <em>Languages</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLanguages()
	 * @generated
	 * @ordered
	 */
	protected EList<Language> languages;

	/**
	 * The cached value of the '{@link #getConfigurations() <em>Configurations</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConfigurations()
	 * @generated
	 * @ordered
	 */
	protected EList<Type> configurations;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TaskImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CrossflowPackage.Literals.TASK;
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
			eNotify(new ENotificationImpl(this, Notification.SET, CrossflowPackage.TASK__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Stream> getInput() {
		if (input == null) {
			input = new EObjectWithInverseResolvingEList.ManyInverse<Stream>(Stream.class, this, CrossflowPackage.TASK__INPUT, CrossflowPackage.STREAM__INPUT_OF);
		}
		return input;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Stream> getOutput() {
		if (output == null) {
			output = new EObjectWithInverseResolvingEList.ManyInverse<Stream>(Stream.class, this, CrossflowPackage.TASK__OUTPUT, CrossflowPackage.STREAM__OUTPUT_OF);
		}
		return output;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Boolean getMasterOnly() {
		return masterOnly;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMasterOnly(Boolean newMasterOnly) {
		Boolean oldMasterOnly = masterOnly;
		masterOnly = newMasterOnly;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CrossflowPackage.TASK__MASTER_ONLY, oldMasterOnly, masterOnly));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Boolean getParallel() {
		return parallel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParallel(Boolean newParallel) {
		Boolean oldParallel = parallel;
		parallel = newParallel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CrossflowPackage.TASK__PARALLEL, oldParallel, parallel));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Boolean getCached() {
		return cached;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCached(Boolean newCached) {
		Boolean oldCached = cached;
		cached = newCached;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CrossflowPackage.TASK__CACHED, oldCached, cached));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Boolean getMultipleOutputs() {
		return multipleOutputs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMultipleOutputs(Boolean newMultipleOutputs) {
		Boolean oldMultipleOutputs = multipleOutputs;
		multipleOutputs = newMultipleOutputs;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CrossflowPackage.TASK__MULTIPLE_OUTPUTS, oldMultipleOutputs, multipleOutputs));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getImpl() {
		return impl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImpl(String newImpl) {
		String oldImpl = impl;
		impl = newImpl;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CrossflowPackage.TASK__IMPL, oldImpl, impl));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Field> getParameters() {
		if (parameters == null) {
			parameters = new EObjectResolvingEList<Field>(Field.class, this, CrossflowPackage.TASK__PARAMETERS);
		}
		return parameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Language> getLanguages() {
		if (languages == null) {
			languages = new EObjectResolvingEList<Language>(Language.class, this, CrossflowPackage.TASK__LANGUAGES);
		}
		return languages;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Type> getConfigurations() {
		if (configurations == null) {
			configurations = new EObjectResolvingEList<Type>(Type.class, this, CrossflowPackage.TASK__CONFIGURATIONS);
		}
		return configurations;
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
			case CrossflowPackage.TASK__INPUT:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getInput()).basicAdd(otherEnd, msgs);
			case CrossflowPackage.TASK__OUTPUT:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getOutput()).basicAdd(otherEnd, msgs);
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
			case CrossflowPackage.TASK__INPUT:
				return ((InternalEList<?>)getInput()).basicRemove(otherEnd, msgs);
			case CrossflowPackage.TASK__OUTPUT:
				return ((InternalEList<?>)getOutput()).basicRemove(otherEnd, msgs);
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
			case CrossflowPackage.TASK__NAME:
				return getName();
			case CrossflowPackage.TASK__INPUT:
				return getInput();
			case CrossflowPackage.TASK__OUTPUT:
				return getOutput();
			case CrossflowPackage.TASK__MASTER_ONLY:
				return getMasterOnly();
			case CrossflowPackage.TASK__PARALLEL:
				return getParallel();
			case CrossflowPackage.TASK__CACHED:
				return getCached();
			case CrossflowPackage.TASK__MULTIPLE_OUTPUTS:
				return getMultipleOutputs();
			case CrossflowPackage.TASK__IMPL:
				return getImpl();
			case CrossflowPackage.TASK__PARAMETERS:
				return getParameters();
			case CrossflowPackage.TASK__LANGUAGES:
				return getLanguages();
			case CrossflowPackage.TASK__CONFIGURATIONS:
				return getConfigurations();
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
			case CrossflowPackage.TASK__NAME:
				setName((String)newValue);
				return;
			case CrossflowPackage.TASK__INPUT:
				getInput().clear();
				getInput().addAll((Collection<? extends Stream>)newValue);
				return;
			case CrossflowPackage.TASK__OUTPUT:
				getOutput().clear();
				getOutput().addAll((Collection<? extends Stream>)newValue);
				return;
			case CrossflowPackage.TASK__MASTER_ONLY:
				setMasterOnly((Boolean)newValue);
				return;
			case CrossflowPackage.TASK__PARALLEL:
				setParallel((Boolean)newValue);
				return;
			case CrossflowPackage.TASK__CACHED:
				setCached((Boolean)newValue);
				return;
			case CrossflowPackage.TASK__MULTIPLE_OUTPUTS:
				setMultipleOutputs((Boolean)newValue);
				return;
			case CrossflowPackage.TASK__IMPL:
				setImpl((String)newValue);
				return;
			case CrossflowPackage.TASK__PARAMETERS:
				getParameters().clear();
				getParameters().addAll((Collection<? extends Field>)newValue);
				return;
			case CrossflowPackage.TASK__LANGUAGES:
				getLanguages().clear();
				getLanguages().addAll((Collection<? extends Language>)newValue);
				return;
			case CrossflowPackage.TASK__CONFIGURATIONS:
				getConfigurations().clear();
				getConfigurations().addAll((Collection<? extends Type>)newValue);
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
			case CrossflowPackage.TASK__NAME:
				setName(NAME_EDEFAULT);
				return;
			case CrossflowPackage.TASK__INPUT:
				getInput().clear();
				return;
			case CrossflowPackage.TASK__OUTPUT:
				getOutput().clear();
				return;
			case CrossflowPackage.TASK__MASTER_ONLY:
				setMasterOnly(MASTER_ONLY_EDEFAULT);
				return;
			case CrossflowPackage.TASK__PARALLEL:
				setParallel(PARALLEL_EDEFAULT);
				return;
			case CrossflowPackage.TASK__CACHED:
				setCached(CACHED_EDEFAULT);
				return;
			case CrossflowPackage.TASK__MULTIPLE_OUTPUTS:
				setMultipleOutputs(MULTIPLE_OUTPUTS_EDEFAULT);
				return;
			case CrossflowPackage.TASK__IMPL:
				setImpl(IMPL_EDEFAULT);
				return;
			case CrossflowPackage.TASK__PARAMETERS:
				getParameters().clear();
				return;
			case CrossflowPackage.TASK__LANGUAGES:
				getLanguages().clear();
				return;
			case CrossflowPackage.TASK__CONFIGURATIONS:
				getConfigurations().clear();
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
			case CrossflowPackage.TASK__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case CrossflowPackage.TASK__INPUT:
				return input != null && !input.isEmpty();
			case CrossflowPackage.TASK__OUTPUT:
				return output != null && !output.isEmpty();
			case CrossflowPackage.TASK__MASTER_ONLY:
				return MASTER_ONLY_EDEFAULT == null ? masterOnly != null : !MASTER_ONLY_EDEFAULT.equals(masterOnly);
			case CrossflowPackage.TASK__PARALLEL:
				return PARALLEL_EDEFAULT == null ? parallel != null : !PARALLEL_EDEFAULT.equals(parallel);
			case CrossflowPackage.TASK__CACHED:
				return CACHED_EDEFAULT == null ? cached != null : !CACHED_EDEFAULT.equals(cached);
			case CrossflowPackage.TASK__MULTIPLE_OUTPUTS:
				return MULTIPLE_OUTPUTS_EDEFAULT == null ? multipleOutputs != null : !MULTIPLE_OUTPUTS_EDEFAULT.equals(multipleOutputs);
			case CrossflowPackage.TASK__IMPL:
				return IMPL_EDEFAULT == null ? impl != null : !IMPL_EDEFAULT.equals(impl);
			case CrossflowPackage.TASK__PARAMETERS:
				return parameters != null && !parameters.isEmpty();
			case CrossflowPackage.TASK__LANGUAGES:
				return languages != null && !languages.isEmpty();
			case CrossflowPackage.TASK__CONFIGURATIONS:
				return configurations != null && !configurations.isEmpty();
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
		result.append(", masterOnly: ");
		result.append(masterOnly);
		result.append(", parallel: ");
		result.append(parallel);
		result.append(", cached: ");
		result.append(cached);
		result.append(", multipleOutputs: ");
		result.append(multipleOutputs);
		result.append(", impl: ");
		result.append(impl);
		result.append(')');
		return result.toString();
	}

} //TaskImpl
