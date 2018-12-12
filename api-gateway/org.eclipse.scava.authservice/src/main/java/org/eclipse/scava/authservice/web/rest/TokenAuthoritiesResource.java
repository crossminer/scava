package org.eclipse.scava.authservice.web.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.core.Context;

import org.eclipse.scava.authservice.domain.TokenAuthorities;
import org.eclipse.scava.authservice.service.TokenAuthoritiesService;
import org.eclipse.scava.authservice.service.UserService;
import org.eclipse.scava.authservice.service.dto.TokenAuthoritiesDTO;
import org.eclipse.scava.authservice.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

@RestController
@RequestMapping("/api")
public class TokenAuthoritiesResource {
	private final Logger log = LoggerFactory.getLogger(UserResource.class);
	
	private final TokenAuthoritiesService tokenAuthoritiesService;
	
	private final UserService userService;

	public TokenAuthoritiesResource(TokenAuthoritiesService tokenAuthoritiesService, UserService userService) {
		this.tokenAuthoritiesService = tokenAuthoritiesService;
		this.userService = userService;
	}
    
	/**
     * POST /token-authorities : generate a Token Authorities.
     *
     * @return the ResponseEntity with status 200 (OK) and with body all tokenAuthorities
     */
	@PostMapping("/token-authorities/generate-token")
	@Timed
	public void generateAccessToken(
			@Context HttpServletRequest req, 
			@Valid @RequestBody TokenAuthoritiesDTO tokenAuthoritiesDTO
	) {
		log.debug("REST request to create TokenAuthorities : {}", tokenAuthoritiesDTO);
		String currentLogin = userService.decodeJwtTokenUsername(req);
		tokenAuthoritiesService.generateNewUUIDToken(tokenAuthoritiesDTO, currentLogin);
	}
	
	/**
     * GET /token-authorities : get all users.
     *
     * @return the ResponseEntity with status 200 (OK) and with body all tokenAuthorities
     */
    @GetMapping("/token-authorities")
    @Timed
    public List<TokenAuthorities> getAllTokenAuthorities(@Context HttpServletRequest req) {
    	String currentLogin = userService.decodeJwtTokenUsername(req);
        final List<TokenAuthorities> tokenAuthorities = tokenAuthoritiesService.getAllTokensAuthorities(currentLogin);
        return tokenAuthorities;
    }
    
    /**
     * GET /token-authorities : get user by label.
     *
     * @return the ResponseEntity with status 200 (OK) and with body all tokenAuthorities
     */
    @GetMapping("/token-authorities/{label}")
    @Timed
    public TokenAuthorities getTokenAuthoritiesbyLabel(@Context HttpServletRequest req, @PathVariable String label) {
    	String currentLogin = userService.decodeJwtTokenUsername(req);
        final TokenAuthorities tokenAuthorities = tokenAuthoritiesService.getTokensAuthoritiesByLabel(currentLogin, label);
        return tokenAuthorities;
    }
    
    /**
     * DELETE /users/:login : delete the "login" User.
     *
     * @param login the login of the user to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/token-authorities/{label}")
    @Timed
    public ResponseEntity<Void> deleteTokenAuthorities(@Context HttpServletRequest req, @PathVariable String label) {
        log.debug("REST request to delete Token Authorities: {}", label);
        String currentLogin = userService.decodeJwtTokenUsername(req);
        tokenAuthoritiesService.deleteTokenAuthorities(label, currentLogin);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert( "A userAuthorities is deleted with identifier " + label, label)).build();
    }
    
    /**
     * GET /token-authorities/check-token : check if token is valid.
     *
     * @return the ResponseEntity with status 200 (OK) and with body all tokenAuthorities
     */
    @GetMapping("/token-authorities/check-token")
    @Timed
    public boolean checkTokenAuthorities(@RequestParam(value = "authToken") String token, @RequestParam(value = "rightAccess") String rightAccess) {
        return tokenAuthoritiesService.isTokenAuthoritiesValid(token, rightAccess);
    }

}
