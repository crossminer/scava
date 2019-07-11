/*******************************************************************************
 * Copyright (C) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {           
	
	ApiInfo apiInfo = new ApiInfo(
	          "CROSSMINER APIs", 
	          "This web interface allows one to use with the provided recommendations.", 
	          "0.0.1.1",  
	          "EPL-2.0", 
	          new Contact("Juri Di Rocco", "", "juri.dirocco@univaq.it"), 
	          "EPL-2.0", "https://www.eclipse.org/legal/epl-2.0/", Collections.EMPTY_LIST);
    @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)
        		.apiInfo(apiInfo)
        		.select()               
        		.apis(RequestHandlerSelectors.basePackage("org.eclipse.scava.presentation.rest"))              
        		.paths(PathSelectors.any())                          
        		.build();                                          
    }
    
}