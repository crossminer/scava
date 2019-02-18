package org.eclipse.scava.crossflow.runtime;

import java.io.File;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class Serializer {
	
	protected XStream xstream = new XStream(new DomDriver());
	
	
	public String toString(Object object) {
		return xstream.toXML(object);
	}
	
	public Object toObject(String string) {
		return xstream.fromXML(string);
	}
	
	public Object toObject(File file) {
		return xstream.fromXML(file);
	}
	
	public void setClassloader(ClassLoader classLoader) {
		xstream.setClassLoader(classLoader);
	}
	
}
