package org.eclipse.scava.apigateway.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("scava.routes.config")
public class ScavaRoutesConfig {

	private List<String> adminAccessApi;
	private List<String> projectManagerAccessApi;
	private List<String> userAccessApi;
	
	public List<String> getAdminAccessApi() {
		return adminAccessApi;
	}
	public void setAdminAccessApi(List<String> adminAccessApi) {
		this.adminAccessApi = adminAccessApi;
	}
	public List<String> getProjectManagerAccessApi() {
		return projectManagerAccessApi;
	}
	public void setProjectManagerAccessApi(List<String> projectManagerAccessApi) {
		this.projectManagerAccessApi = projectManagerAccessApi;
	}
	public List<String> getUserAccessApi() {
		return userAccessApi;
	}
	public void setUserAccessApi(List<String> userAccessApi) {
		this.userAccessApi = userAccessApi;
	}
		
}
