/*******************************************************************************
 * Copyright (c) 2018 Softeam
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.authservice.test;

import javax.servlet.http.HttpServletResponse;

import org.eclipse.scava.authservice.test.configuration.JwtAuthenticationConfig;
import org.eclipse.scava.authservice.test.jwt.JwtUsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired JwtAuthenticationConfig config;

    @Bean
    public JwtAuthenticationConfig jwtConfig() {
        return new JwtAuthenticationConfig();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    	
        auth.inMemoryAuthentication()
                .withUser("admin").password("admin").roles("ADMIN", "USER").and()
                .withUser("shuaicj").password("shuaicj").roles("USER");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .logout().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .anonymous()
                .and()
                    .exceptionHandling().authenticationEntryPoint(
                            (req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                    .addFilterAfter(new JwtUsernamePasswordAuthenticationFilter(config, authenticationManager()),
                            UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                    .antMatchers(config.getUrl()).permitAll()
                    .anyRequest().authenticated();
    }
}

