/*******************************************************************************
 * Copyright (c) 2018 Softeam
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.authservice;

import javax.servlet.http.HttpServletResponse;

import org.eclipse.scava.authservice.configuration.JwtAuthenticationConfig;
import org.eclipse.scava.authservice.jwt.JwtUsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final UserDetailsService userDetailsService;
	
    @Autowired 
    private JwtAuthenticationConfig config;
    
    @Value("${scava.administration.username}")
    private String username;
    
    @Value("${scava.administration.password}")
    private String password;
    
    @Value("${scava.administration.admin-role}")
    private String adminRole;
    
    @Value("${scava.administration.project-manager-role}")
    private String projectManagerRole;
    
    @Value("${scava.administration.user-role}")
    private String userRole;
    
    public SecurityConfig(AuthenticationManagerBuilder authenticationManagerBuilder, UserDetailsService userDetailsService) {
		this.authenticationManagerBuilder = authenticationManagerBuilder;
		this.userDetailsService = userDetailsService;
	}

	@Bean
    public JwtAuthenticationConfig jwtConfig() {
        return new JwtAuthenticationConfig();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    	
        auth.inMemoryAuthentication()
                .withUser(username).password(password).roles(adminRole, projectManagerRole, userRole);
        
        auth.userDetailsService(userDetailsService)
        	.passwordEncoder(passwordEncoder());
        
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
        	.csrf()
        		.disable()
            .logout()
            	.disable()
            .formLogin()
            	.disable()
            .sessionManagement()
            	.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
          	.anonymous()
        .and()
            .exceptionHandling()
             	.authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
        .and()
            .addFilterAfter(new JwtUsernamePasswordAuthenticationFilter(config, authenticationManager()),
            	UsernamePasswordAuthenticationFilter.class)
            .authorizeRequests()
        		.anyRequest().permitAll();
    }
}