/*******************************************************************************
 * Copyright (c) 2018 Softeam
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.authservice.test.configuration;

import org.springframework.beans.factory.annotation.Value;

public class JwtAuthenticationConfig {

	@Value("${apigateway.security.jwt.url:/api/authentication}")
	private String url;

	@Value("${apigateway.security.jwt.header:Authorization}")
	private String header;

	@Value("${apigateway.security.jwt.prefix:Bearer}")
	private String prefix;

	@Value("${apigateway.security.jwt.expiration:#{24*60*60}}")
	private int expiration; // default 24 hours

	@Value("${apigateway.security.jwt.secret}")
	private String secret;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public int getExpiration() {
		return expiration;
	}

	public void setExpiration(int expiration) {
		this.expiration = expiration;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

}
