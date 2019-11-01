package org.eclipse.scava.crossflow.runtime.serialization;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * {@link Serializer} implementation that uses {@link XStream} as the backing
 * implementation
 */
public class XstreamSerializer implements Serializer {

	protected Set<Class<?>> registeredTypes;
	protected XStream xstream;

	public XstreamSerializer() {
		this.registeredTypes = new HashSet<Class<?>>();
		this.xstream = null;
	}

	@Override
	public void init() {
		xstream = new XStream(new DomDriver());
		XStream.setupDefaultSecurity(xstream);
		for (Class<?> clazz : registeredTypes) {
			doXstreamRegisterType(clazz);
		}
	}

	@Override
	public Serializer registerType(Class<?> clazz) {
		boolean modified = registeredTypes.add(clazz);
		if (modified && xstream != null) {
			doXstreamRegisterType(clazz);
		}
		return this;
	}

	@Override
	public Collection<Class<?>> getRegisteredTypes() {
		return registeredTypes;
	}

	@Override
	public <O> String serialize(O input) {
		if (xstream == null) init();
		return xstream.toXML((O) input);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <O> O deserialize(String input) {
		if (xstream == null) init();
		return (O) xstream.fromXML(input);
	}

	private void doXstreamRegisterType(Class<?> clazz) {
		checkNotNull(xstream);
		xstream.alias(clazz.getSimpleName(), clazz);
		xstream.allowTypes(new Class[] { clazz });
	}

}
