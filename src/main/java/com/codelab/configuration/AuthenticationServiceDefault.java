package com.codelab.configuration;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

/**
 * @author Vivek Jain This class is used for authentication and logout tasks
 */

public class AuthenticationServiceDefault implements AuthenticationService {
	static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationServiceDefault.class);

	@Autowired
	private ApplicationContext applicationContext;

	private final AuthenticationManager authenticationManager;
	private final TokenManager tokenManager;

	public AuthenticationServiceDefault(AuthenticationManager authenticationManager, TokenManager tokenManager) {
		this.authenticationManager = authenticationManager;
		this.tokenManager = tokenManager;
	}

	@PostConstruct
	public void init() {
		LOGGER.debug("Execute Method : init() with: " + applicationContext);
	}

	public TokenInfo authenticate(String login, String password)
			throws AuthenticationException, NoSuchAlgorithmException, UnsupportedEncodingException {
		LOGGER.debug("Execute Method : authenticate()");

		Authentication authentication = new UsernamePasswordAuthenticationToken(login, password);
		authentication = authenticationManager.authenticate(authentication);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		if (authentication.getPrincipal() != null) {
			UserDetails userContext = (UserDetails) authentication.getPrincipal();
			TokenInfo newToken = tokenManager.createNewToken(userContext);
			if (newToken == null) {
				return null;
			}
			return newToken;
		}
		return null;
	}

	public boolean checkToken(String token) {
		LOGGER.debug("Execute Method : checkToken()");
		UserDetails userDetails = tokenManager.getUserDetails(token);
		if (userDetails == null) {
			return false;
		}
		Authentication securityToken = new PreAuthenticatedAuthenticationToken(userDetails, null,
				userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(securityToken);

		return true;
	}

	public void logout(String token) {
		UserDetails logoutUser = tokenManager.removeToken(token);
		LOGGER.debug("Execute Method : logout() : " + logoutUser);
		SecurityContextHolder.clearContext();

	}

	public UserDetails currentUser() {
		LOGGER.debug("Execute Method : currentUser()");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return null;
		}
		return (UserDetails) authentication.getPrincipal();
	}

}
