package org.eclipse.scava.crossflow.restmule.client.bitbucket.util;

import java.util.Properties;

import org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil;

public class BitbucketPropertiesUtil {

	private static final String PROPERTIES_FILE = "bitbucket.properties";

	public static String get(String property){
		Properties properties = PropertiesUtil.load(BitbucketPropertiesUtil.class, PROPERTIES_FILE);
		return properties.getProperty(property);
	}

}