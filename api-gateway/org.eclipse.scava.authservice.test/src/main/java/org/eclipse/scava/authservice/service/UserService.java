package org.eclipse.scava.authservice.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.scava.authservice.repository.AuthorityRepository;
import org.eclipse.scava.authservice.repository.UserRepository;
import org.eclipse.scava.authservice.security.AuthoritiesConstants;
import org.eclipse.scava.authservice.security.SecurityUtils;
import org.eclipse.scava.authservice.service.dto.UserDTO;
import org.eclipse.scava.authservice.web.rest.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.eclipse.scava.authservice.configuration.Constants;
import org.eclipse.scava.authservice.domain.Authority;
import org.eclipse.scava.authservice.domain.User;
/**
 * Service class for managing users.
 */
@Service
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    
    private final PasswordEncoder passwordEncoder;
    
    private final AuthorityRepository authorityRepository;
    
    //private final CacheManager cacheManager;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
        
    }
    
    public Optional<User> activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
        return userRepository.findOneByActivationKey(key)
            .map(user -> {
                // activate given user for the registration key.
                user.setActivated(true);
                user.setActivationKey(null);
                userRepository.save(user);
                //cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE).evict(user.getLogin());
                //cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE).evict(user.getEmail());
                log.debug("Activated user: {}", user);
                return user;
            });
    }
    
    public Optional<User> completePasswordReset(String newPassword, String key) {
        log.debug("Reset user password for reset key {}", key);

        return userRepository.findOneByResetKey(key)
            .filter(user -> user.getResetDate().isAfter(Instant.now().minusSeconds(86400)))
            .map(user -> {
                 user.setPassword(passwordEncoder.encode(newPassword));
                 user.setResetKey(null);
                 user.setResetDate(null);
                 userRepository.save(user);
                 //cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE).evict(user.getLogin());
                 //cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE).evict(user.getEmail());
                 return user;
            });
     }
    
    public Optional<User> requestPasswordReset(String mail) {
        return userRepository.findOneByEmailIgnoreCase(mail)
            .filter(User::getActivated)
            .map(user -> {
                user.setResetKey(RandomUtil.generateResetKey());
                user.setResetDate(Instant.now());
                userRepository.save(user);
                //cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE).evict(user.getLogin());
                //cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE).evict(user.getEmail());
                return user;
            });
    }
    
    public User registerUser(UserDTO userDTO, String password) {

        User newUser = new User();
        //Authority authority = authorityRepository.findOne(AuthoritiesConstants.USER);
        Authority authority = new Authority();
        authority.setName(AuthoritiesConstants.USER);
        Set<Authority> authorities = new HashSet<>();
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setLogin(userDTO.getLogin());
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);
        newUser.setFirstName(userDTO.getFirstName());
        newUser.setLastName(userDTO.getLastName());
        newUser.setEmail(userDTO.getEmail());
        newUser.setImageUrl(userDTO.getImageUrl());
        if (userDTO.getLangKey() == null) {
            newUser.setLangKey(Constants.DEFAULT_LANGUAGE); // default language
        }
        // new user is not active
        newUser.setActivated(false);
        // new user gets registration key
        newUser.setActivationKey(RandomUtil.generateActivationKey());
        authorities.add(authority);
        newUser.setAuthorities(authorities);
        userRepository.save(newUser);
        //cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE).evict(newUser.getLogin());
        //cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE).evict(newUser.getEmail());
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }
    
    public User createUser(UserDTO userDTO) {
        User user = new User();
        user.setLogin(userDTO.getLogin());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setImageUrl(userDTO.getImageUrl());
        if (userDTO.getLangKey() == null) {
            user.setLangKey(Constants.DEFAULT_LANGUAGE); // default language
        } else {
            user.setLangKey(userDTO.getLangKey());
        }
        if (userDTO.getAuthorities() != null) {
            Set<Authority> authorities = userDTO.getAuthorities().stream()
                .map(authorityRepository::findOne)
                .collect(Collectors.toSet());
            user.setAuthorities(authorities);
        }
        String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
        user.setPassword(encryptedPassword);
        user.setResetKey(RandomUtil.generateResetKey());
        user.setResetDate(Instant.now());
        user.setActivated(true);
        userRepository.save(user);
        //cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE).evict(user.getLogin());
        //cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE).evict(user.getEmail());
        log.debug("Created Information for User: {}", user);
        return user;
    }
    
    /**
     * Update basic information (first name, last name, email, language) for the current user.
     *
     * @param firstName first name of user
     * @param lastName last name of user
     * @param email email id of user
     * @param langKey language key
     * @param imageUrl image URL of user
     */
    public void updateUser(String firstName, String lastName, String email, String langKey, String imageUrl) {
        SecurityUtils.getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin)
            .ifPresent(user -> {
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setEmail(email);
                user.setLangKey(langKey);
                user.setImageUrl(imageUrl);
                userRepository.save(user);
                //cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE).evict(user.getLogin());
                //cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE).evict(user.getEmail());
                log.debug("Changed Information for User: {}", user);
            });
    }
    
    /**
     * Update all information for a specific user, and return the modified user.
     *
     * @param userDTO user to update
     * @return updated user
     */
    public Optional<UserDTO> updateUser(UserDTO userDTO) {
        return Optional.of(userRepository
            .findOne(userDTO.getId()))
            .map(user -> {
                user.setLogin(userDTO.getLogin());
                user.setFirstName(userDTO.getFirstName());
                user.setLastName(userDTO.getLastName());
                user.setEmail(userDTO.getEmail());
                user.setImageUrl(userDTO.getImageUrl());
                user.setActivated(userDTO.isActivated());
                user.setLangKey(userDTO.getLangKey());
                Set<Authority> managedAuthorities = user.getAuthorities();
                managedAuthorities.clear();
                
                userDTO.getAuthorities().stream()
                	.map(authorithy -> new Authority(authorithy))
                	.forEach(managedAuthorities::add);
                
                userRepository.save(user);
                //cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE).evict(user.getLogin());
                //cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE).evict(user.getEmail());
                log.debug("Changed Information for User: {}", user);
                return user;
            })
            .map(UserDTO::new);
    }
    
    public void deleteUser(String login) {
        userRepository.findOneByLogin(login).ifPresent(user -> {
            userRepository.delete(user);
            //cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE).evict(user.getLogin());
            //cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE).evict(user.getEmail());
            log.debug("Deleted User: {}", user);
        });
    }
    
    public void changePassword(String password) {
        SecurityUtils.getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin)
            .ifPresent(user -> {
                String encryptedPassword = passwordEncoder.encode(password);
                user.setPassword(encryptedPassword);
                userRepository.save(user);
                //cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE).evict(user.getLogin());
                //cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE).evict(user.getEmail());
                log.debug("Changed password for User: {}", user);
            });
    }
    
    public Page<UserDTO> getAllManagedUsers(Pageable pageable) {
        return userRepository.findAllByLoginNot(pageable, Constants.ANONYMOUS_USER).map(UserDTO::new);
    }
    
    public Optional<User> getUserWithAuthoritiesByLogin(String login) {
        return userRepository.findOneByLogin(login);
    }
    
    public Optional<User> getUserWithAuthorities() {
        return SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByLogin);
    }
    
}
