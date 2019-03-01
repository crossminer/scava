package org.eclipse.scava.workflow.restmule.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;;

/**
 * 
 * {@link PropertiesUtil}
 * <p>
 * @version 1.0.0
 *
 */
public class PropertiesUtil {
    
	public final static String API_BASE_URL = "api.base.url";
	public final static String USER_AGENT = "user.agent";
	public final static String ACCEPT = "accept";
	
	public final static String OAUTH2_AUTH_URL = "oauth2.auth.url";
	public final static String OAUTH2_TOKEN_URL = "oauth2.token.url";
	public final static String OAUTH2_SCOPES = "oauth2.scopes";
	
	public final static String RATE_LIMIT_LIMIT = "rate.limit.limit";
	public final static String RATE_LIMIT_REMAINING = "rate.limit.remaining";
	public final static String RATE_LIMIT_RESET = "rate.limit.reset";
	
	public final static String PAGE_START_VALUE = "page.start.value"; 
	public final static String PAGE_MAX_VALUE = "page.max.value";
	public final static String PAGE_INCREMENT = "page.increment";
	public final static String PER_ITERATION_VALUE = "per.iteration.value";
	public final static String PER_ITERATION_LABEL = "per.iteration.label";
	public final static String PAGE_LABEL = "page";
	public final static String PAGE_INFO = "page.header";
	
	// PRIVATE PROPERTIES
	public final static String CLIENT_ID = "client.id";
	public final static String CLIENT_SECRET = "client.secret";
	public final static String ACCESS_TOKEN = "access.token";
	public final static String USERNAME = "username";
	public final static String PASSWORD = "password";
	public final static String PERSONAL_ACCESS_TOKEN = "personal.access.token";
	
	public static Properties load(String propertiesFile) {
		return PropertiesUtil.load(PropertiesUtil.class, propertiesFile);
	} 

	public static Properties load(Class<?> clazz, String propertiesFile) {
		Properties properties = new Properties();
		InputStream input = null;
		try {
			input = clazz.getClassLoader().getResourceAsStream(propertiesFile);
			properties.load(input);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally{
			if(input!=null){
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return properties;
	}
}
