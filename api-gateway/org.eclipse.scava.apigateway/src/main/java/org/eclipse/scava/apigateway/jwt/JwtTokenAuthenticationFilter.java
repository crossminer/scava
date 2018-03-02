/*******************************************************************************
 * Copyright (c) 2018 Softeam
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.apigateway.jwt;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import org.eclipse.scava.apigateway.config.JwtAuthenticationConfig;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

	private final JwtAuthenticationConfig config;

	public JwtTokenAuthenticationFilter(JwtAuthenticationConfig config) {
		this.config = config;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse rsp, FilterChain filterChain)
			throws ServletException, IOException {
		String token = req.getHeader(config.getHeader());
		if (token != null && token.startsWith(config.getPrefix() + " ")) {
			token = token.replace(config.getPrefix() + " ", "");
			try {
				Claims claims = Jwts.parser().setSigningKey(config.getSecret().getBytes()).parseClaimsJws(token)
						.getBody();
				String username = claims.getSubject();
				@SuppressWarnings("unchecked")
				List<String> authorities = claims.get("authorities", List.class);
				if (username != null) {
					UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null,
							authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
					SecurityContextHolder.getContext().setAuthentication(auth);
				}
			} catch (Exception ignore) {
				SecurityContextHolder.clearContext();
			}
		}
		filterChain.doFilter(req, rsp);
	}
}
