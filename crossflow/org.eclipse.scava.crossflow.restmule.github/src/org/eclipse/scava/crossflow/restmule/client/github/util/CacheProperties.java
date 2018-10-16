package org.eclipse.scava.crossflow.restmule.client.github.util;

import org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil;

import java.util.Properties;

public class CacheProperties {
    private static final String GITHUB_CACHE_PROPERTIES_FILE = "githubcache.properties";

    public static String get(String property) {
        Properties properties = PropertiesUtil.load(PrivateProperties.class, GITHUB_CACHE_PROPERTIES_FILE);
        return properties.getProperty(property);
    }

    public static String get(Class<? extends Object> c, String property) {
        if (c == null)
            return get(property);
        else
            return PropertiesUtil.load(c, GITHUB_CACHE_PROPERTIES_FILE).getProperty(property);
    }

    public static boolean exists() {
        return PrivateProperties.class.getClassLoader().getResourceAsStream(GITHUB_CACHE_PROPERTIES_FILE) != null;
    }

    public static boolean exists(Class<? extends Object> c) {
        if (c == null)
            return exists();
        else
            return c.getClassLoader().getResourceAsStream(GITHUB_CACHE_PROPERTIES_FILE) != null;
    }

}
