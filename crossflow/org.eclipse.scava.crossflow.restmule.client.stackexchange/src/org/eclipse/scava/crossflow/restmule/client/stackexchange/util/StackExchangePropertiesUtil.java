package org.eclipse.scava.crossflow.restmule.client.stackexchange.util;

import java.util.Properties;

import org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil;

public class StackExchangePropertiesUtil {

	private static final String PROPERTIES_FILE = "stackexchange.properties";

	public static String get(String property){
		Properties properties = PropertiesUtil.load(StackExchangePropertiesUtil.class, PROPERTIES_FILE);
		return properties.getProperty(property);
	}

}