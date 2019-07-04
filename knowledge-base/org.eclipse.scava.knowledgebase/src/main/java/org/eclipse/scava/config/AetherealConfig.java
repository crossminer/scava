package org.eclipse.scava.config;

import org.maracas.Maracas;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import nl.cwi.swat.aethereal.AetherDownloader;

@Configuration
public class AetherealConfig {                                    
    @Bean
    public AetherDownloader aetherDownloader() {
    	return new AetherDownloader(32);
    }
    @Bean
    public Maracas maracas() {
    	return new Maracas();
    }
}