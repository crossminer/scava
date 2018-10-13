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
    @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.basePackage("org.eclipse.scava.presentation.rest"))              
          .paths(PathSelectors.any())                          
          .build();                                          
    }
    private ApiInfo apiInfo() {
//    	new ApiInfo
        return new ApiInfo(
          "CROSSMINER APIs", 
          "Some custom description of API.", 
          "API TOS", 
          "Terms of service", 
          new Contact("John Doe", "www.example.com", "myeaddress@company.com"), 
          "License of API", "", Collections.emptyList());
   }
}