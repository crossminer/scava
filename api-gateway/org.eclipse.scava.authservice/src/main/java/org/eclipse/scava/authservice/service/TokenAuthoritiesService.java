package org.eclipse.scava.authservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.scava.authservice.domain.TokenAuthorities;
import org.eclipse.scava.authservice.domain.User;
import org.eclipse.scava.authservice.repository.UserRepository;
import org.eclipse.scava.authservice.service.dto.TokenAuthoritiesDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

@Service
public class TokenAuthoritiesService {

	private final Logger log = LoggerFactory.getLogger(UserService.class);
	
	private final static String _MONITORING = "monitoring_authorities";

	private final UserRepository userRepository;

	public TokenAuthoritiesService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void generateNewUUIDToken(TokenAuthoritiesDTO tokenAuthoritiesDTO, String currentLogin) {
		final String uuid = UUID.randomUUID().toString().replace("-", "");
		TokenAuthorities newTokenAuthorities = new TokenAuthorities();
		newTokenAuthorities.setLabel(tokenAuthoritiesDTO.getLabel());
		newTokenAuthorities.setAccessToken(uuid);
		newTokenAuthorities.setMonitoringAuthorities(tokenAuthoritiesDTO.getMonitoringAuthorities());
		userRepository.findOneByLogin(currentLogin).ifPresent(user -> {
			user.getTokenAuthorities().add(newTokenAuthorities);
			userRepository.save(user);
			log.debug("Updated User: {} with TokenAuthorities", user);
		});
	}

	public List<TokenAuthorities> getAllTokensAuthorities(String login) {
		List<TokenAuthorities> tokenAuthorities = new ArrayList<TokenAuthorities>();
		userRepository.findOneByLogin(login).ifPresent(user -> {
			tokenAuthorities.addAll(user.getTokenAuthorities());
			log.debug("Retrieve all Token Authorities: {} ", tokenAuthorities);
		});
		return tokenAuthorities;
	}
	
	public TokenAuthorities getTokensAuthoritiesByLabel(String login, String label) {
		TokenAuthorities tokenAuthorities = new TokenAuthorities();
		if(userRepository.findOneByLogin(login).isPresent()) {
			User user = userRepository.findOneByLogin(login).get();
			for (TokenAuthorities token : user.getTokenAuthorities()) {
				if(token.getLabel().equals(label))
					tokenAuthorities = token;
			}
		}
		return tokenAuthorities;
	}

	public void deleteTokenAuthorities(String label, String currentLogin) {
		userRepository.findOneByLogin(currentLogin).ifPresent(user -> {
			user.getTokenAuthorities().removeIf(tokenAuthorities -> tokenAuthorities.getLabel().equals(label));
			userRepository.save(user);
		});
	}

	public boolean isTokenAuthoritiesValid(String token, String rightAccess) {
		for (User user : userRepository.findAll()) {
			for (TokenAuthorities tokenAuth : user.getTokenAuthorities()) {
				if (Criteria.where(_MONITORING).getKey().equals(rightAccess.toLowerCase())) {
					if (tokenAuth.getAccessToken().equals(token) && tokenAuth.getMonitoringAuthorities()) {
						return true;
					}
				}
			}
		}
		return false;
	}

}
