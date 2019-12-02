package org.eclipse.scava.crossflow.runtime.serialization;

import java.io.File;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * {@link Serializer} implementation that uses {@link XStream} as the backing
 * implementation
 */
public class XstreamSerializer extends AbstractSerializer {

	protected XStream xstream;

	public XstreamSerializer() {
		super(true);
		xstream = new XStream(new DomDriver());
		XStream.setupDefaultSecurity(xstream);
	}

	@Override
	protected void doRegisterType(Class<?> type) {
		xstream.alias(type.getSimpleName(), type);
		xstream.allowTypes(new Class[] { type });
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

	@SuppressWarnings("unchecked")
	@Override
	public <O> O deserialize(File input) {
		return (O) xstream.fromXML(input);
	}

}
