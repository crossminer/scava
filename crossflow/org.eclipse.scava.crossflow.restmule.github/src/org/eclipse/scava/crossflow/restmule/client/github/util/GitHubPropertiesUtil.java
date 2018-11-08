package org.eclipse.scava.crossflow.restmule.client.github.util;

import java.util.Properties;

import org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil;

public class GitHubPropertiesUtil {

	private static final String PROPERTIES = "github.properties";

	public static String get(String property){
		Properties properties = PropertiesUtil.load(GitHubPropertiesUtil.class, PROPERTIES);
		return properties.getProperty(property);
	}

}