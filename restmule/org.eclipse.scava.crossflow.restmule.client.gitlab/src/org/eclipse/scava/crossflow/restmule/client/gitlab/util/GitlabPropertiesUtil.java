package org.eclipse.scava.crossflow.restmule.client.gitlab.util;

import java.util.Properties;

import org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil;

public class GitlabPropertiesUtil {

	private static final String PROPERTIES_FILE = "gitlab.properties";

	public static String get(String property){
		Properties properties = PropertiesUtil.load(GitlabPropertiesUtil.class, PROPERTIES_FILE);
		return properties.getProperty(property);
	}

}