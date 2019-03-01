package org.eclipse.scava.workflow.restmule.generated.client.github.util;

import java.util.Properties;

import org.eclipse.scava.workflow.restmule.core.util.PropertiesUtil;

public class GitHubPropertiesUtil {

	private static final String PROPERTIES_FILE = "github.properties";

	public static String get(String property){
		Properties properties = PropertiesUtil.load(GitHubPropertiesUtil.class, PROPERTIES_FILE);
		return properties.getProperty(property);
	}

}