package org.eclipse.scava.crossflow.runtime.serialization;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * {@link Serializer} implementation that uses {@link XStream} as the backing
 * implementation
 */
public class XstreamSerializer implements Serializer {

	protected Map<String, Class<?>> registeredTypes;
	protected XStream xstream;

	public XstreamSerializer() {
		this.registeredTypes = new HashMap<String, Class<?>>();
		xstream = new XStream(new DomDriver());
		XStream.setupDefaultSecurity(xstream);
	}

	@Override
	public Serializer registerType(Class<?> clazz) {
		if (!isRegistered(clazz)) {
			registeredTypes.put(clazz.getSimpleName(), clazz);
			doXstreamRegisterType(clazz);
		}
		return this;
	}

	@Override
	public Collection<Class<?>> getRegisteredTypes() {
		return registeredTypes.values();
	}

	@Override
	public boolean isRegistered(String clazz) {
		return registeredTypes.containsKey(clazz);
	}

	@Override
	public <O> String serialize(O input) {
		return xstream.toXML((O) input);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <O> O deserialize(String input) {
		return (O) xstream.fromXML(input);
	}

	private void doXstreamRegisterType(Class<?> clazz) {
		xstream.alias(clazz.getSimpleName(), clazz);
		xstream.allowTypes(new Class[] { clazz });
	}

}
