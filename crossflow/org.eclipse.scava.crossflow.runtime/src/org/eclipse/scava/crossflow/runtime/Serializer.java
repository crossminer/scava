package org.eclipse.scava.crossflow.runtime;

import java.io.File;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class Serializer {
	
	protected XStream xstream = new XStream(new DomDriver());
	
	
	public String toString(Object object) {
		return xstream.toXML(object);
	}
	
	@SuppressWarnings("unchecked")
	public <O> O toObject(String string) {
		return (O) xstream.fromXML(string);
	}
	
	@SuppressWarnings("unchecked")
	public <O> O toObject(File file) {
		return (O) xstream.fromXML(file);
	}
	
	public void setClassloader(ClassLoader classLoader) {
		xstream.setClassLoader(classLoader);
	}
	
}
