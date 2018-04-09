package com.googlecode.pongo.runtime;

import java.util.HashMap;

public class ClasspathPongoFactoryContributor implements PongoFactoryContributor {

	protected HashMap<String, Class<?>> classCache = new HashMap<String, Class<?>>();
	
	@Override
	public boolean canCreate(String className) {
		try {
			return classForName(className) != null;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Pongo create(String className) throws Exception {
		return (Pongo) classForName(className).newInstance();
	}
	
	protected Class<?> classForName(String className) throws Exception {
		Class<?> clazz = classCache.get(className);
		if (clazz == null) {
			clazz = Class.forName(className);
			classCache.put(className, clazz);
		}
		return clazz;
	}
	
}
