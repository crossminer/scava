package org.eclipse.scava.config;

import org.eclipse.scava.business.integration.PatternRepository;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class KBTestConfiguration {

	@Bean
	@Primary
	public PatternRepository nameService() {
		return Mockito.mock(PatternRepository.class);
	}
}
