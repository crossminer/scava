package org.eclipse.scava.crossflow.runtime.serialization;

import java.util.Collection;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public abstract class AbstractSerializer implements Serializer {

	protected boolean isStrict;
	protected BiMap<String, Class<?>> registeredTypes;

	public AbstractSerializer() {
		this(true);
	}

	public AbstractSerializer(boolean isStrict) {
		this.isStrict = isStrict;
		this.registeredTypes = HashBiMap.create();
	}

	@Override
	public void init() {
		;
	}

	@Override
	public boolean isStrict() {
		return isStrict;
	}

	@Override
	public AbstractSerializer setStrict(boolean isStrict) {
		this.isStrict = isStrict;
		return this;
	}

	protected abstract void doRegisterType(Class<?> type);

	@Override
	public Serializer registerType(Class<?> type) {
		if (!isRegistered(type)) {
			registeredTypes.put(type.getSimpleName(), type);
			doRegisterType(type);
		}
		return this;
	}

	@Override
	public boolean isRegistered(Object o) {
		return isRegistered(o.getClass());
	}

	@Override
	public boolean isRegistered(Class<?> type) {
		return registeredTypes.inverse().containsKey(type);
	}

	@Override
	public boolean isRegistered(String type) {
		return registeredTypes.containsKey(type);
	}

	@Override
	public Collection<Class<?>> getRegisteredTypes() {
		return registeredTypes.values();
	}

}
