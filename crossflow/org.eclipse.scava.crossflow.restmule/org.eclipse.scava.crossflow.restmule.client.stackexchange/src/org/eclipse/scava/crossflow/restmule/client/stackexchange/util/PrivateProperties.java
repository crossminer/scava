package org.eclipse.scava.crossflow.restmule.client.stackexchange.util;

import java.util.Properties;

import org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil;

public class PrivateProperties {

	private static final String STACKEXCHANGE_PRIVATE_PROPERTIES_FILE = "stackexchangeprivate.properties";

	public static String get(String property) {
		Properties properties = PropertiesUtil.load(PrivateProperties.class, STACKEXCHANGE_PRIVATE_PROPERTIES_FILE);
		return properties.getProperty(property);
	}

	public static String get(Class<? extends Object> c, String property) {
		if (c == null)
			return get(property);
		else
			return PropertiesUtil.load(c, STACKEXCHANGE_PRIVATE_PROPERTIES_FILE).getProperty(property);
	}

	public static boolean exists() {
		return PrivateProperties.class.getClassLoader().getResourceAsStream(STACKEXCHANGE_PRIVATE_PROPERTIES_FILE) != null;
	}

	public static boolean exists(Class<? extends Object> c) {
		if (c == null)
			return exists();
		else
			return c.getClassLoader().getResourceAsStream(STACKEXCHANGE_PRIVATE_PROPERTIES_FILE) != null;
	}

}
