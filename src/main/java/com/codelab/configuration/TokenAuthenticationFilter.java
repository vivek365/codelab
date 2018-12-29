package com.codelab.configuration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.StringTokenizer;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.web.filter.GenericFilterBean;

import com.codelab.common.Constant;


/**
 * Takes care of HTTP request/response pre-processing for login/logout and token check. Login can be performed on any URL, logout only on specified {@link #logoutLink}. All the interaction with Spring
 * Security should be performed via {@link AuthenticationService}.
 * <p>
 * {@link SecurityContextHolder} is used here only for debug outputs. While this class is configured to be used by Spring Security (configured filter on FORM_LOGIN_FILTER position), but it doesn't
 * really depend on it at all.
 * 
 * @author Bharat
 *
 */
public class TokenAuthenticationFilter extends GenericFilterBean implements Constant{
	static final Logger logger = LoggerFactory.getLogger(TokenAuthenticationFilter.class);

	private static final String HEADER_USERNAME = "X-Username";
	private static final String HEADER_PASSWORD = "X-Password";

	/**
	 * Request attribute that indicates that this filter will not continue with the chain. Handy after login/logout, etc.
	 */
	private static final String REQUEST_ATTR_DO_NOT_CONTINUE = "MyAuthenticationFilter-doNotContinue";

	private final String logoutLink;
	private final AuthenticationService authenticationService;

	public TokenAuthenticationFilter(AuthenticationService authenticationService, String logoutLink) {
		this.authenticationService = authenticationService;
		this.logoutLink = logoutLink;
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		logger.debug(" *** MyAuthenticationFilter.doFilter");
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		boolean authenticated = checkToken(httpRequest, httpResponse);

		if (canRequestProcessingContinue(httpRequest) && httpRequest.getMethod().equals("POST")) {
			// If we're not authenticated, we don't bother with logout at all.
			// Logout does not work in the same request with login - this does
			// not make sense,
			// because logout works with token and login returns it.
			if (authenticated) {
				checkLogout(httpRequest);
			}

			// Login works just fine even when we provide token that is valid up
			// to this request,
			// because then we get a new one.
			try {
				checkLogin(httpRequest, httpResponse);
			} catch (NoSuchAlgorithmException e) {
				throw new CodelabRuntimeException("NoSuchAlgorithmException", e);
			} catch (BadCredentialsException e) {
				throw new CodelabRuntimeException("BadCredentialsException", e);
			}
		}

		if (canRequestProcessingContinue(httpRequest)) {

			chain.doFilter(request, response);
		}
		logger.debug(" === AUTHENTICATION: " + SecurityContextHolder.getContext().getAuthentication());
		
	}

	private void checkLogin(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException, NoSuchAlgorithmException {
		String authorization = httpRequest.getHeader("Authorization");
		String username = httpRequest.getHeader(HEADER_USERNAME);
		String password = httpRequest.getHeader(HEADER_PASSWORD);

		if (authorization != null) {
			checkBasicAuthorization(authorization, httpResponse, httpRequest);
			String token = httpRequest.getHeader(XAUTHTOKEN);
			doNotContinueWithRequestProcessing(httpRequest,token);
		} else if (username != null && password != null) {
			//String encryptedPassword = SHA1Encryptor.sHA1(password);
			checkUsernameAndPassword(username, password, httpResponse, httpRequest);
			// doNotContinueWithRequestProcessing(httpRequest);
		}
	}

	private void checkBasicAuthorization(String authorization, HttpServletResponse httpResponse, HttpServletRequest httpRequest) throws IOException, AuthenticationException, NoSuchAlgorithmException {
		StringTokenizer tokenizer = new StringTokenizer(authorization);
		if (tokenizer.countTokens() < 2) {
			return;
		}
		if (!tokenizer.nextToken().equalsIgnoreCase("Basic")) {
			return;
		}

		String base64 = tokenizer.nextToken();
		String loginPassword = new String(Base64.decode(base64.getBytes(StandardCharsets.UTF_8)));

		logger.debug("loginPassword = " + loginPassword);
		tokenizer = new StringTokenizer(loginPassword, ":");
		logger.debug("tokenizer = " + tokenizer);
		checkUsernameAndPassword(tokenizer.nextToken(), tokenizer.nextToken(), httpResponse, httpRequest);
	}

	private void checkUsernameAndPassword(String username, String password, HttpServletResponse httpResponse, HttpServletRequest httpRequest)
			throws IOException, AuthenticationException, NoSuchAlgorithmException {
			String pass = SHA1Encryptor.sHA1(password);
			TokenInfo tokenInfo = authenticationService.authenticate(username, pass);
			if (tokenInfo != null) {
				httpResponse.setHeader(XAUTHTOKEN, tokenInfo.getToken());
				httpRequest.setAttribute(REQUEST_ATTR_DO_NOT_CONTINUE, null);
				// TODO set other token information possible: IP, ...
			} else {
				httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			}
	}

	/** Returns true, if request contains valid authentication token. */
	private boolean checkToken(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException {
		String token = httpRequest.getHeader(XAUTHTOKEN);
		if (token == null) {
			return false;
		}

		if (authenticationService.checkToken(token)) {
			logger.debug(" *** " + XAUTHTOKEN + " valid for: " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
			return true;
		} else {
			logger.debug(" *** Invalid " + XAUTHTOKEN + ' ' + token);
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			doNotContinueWithRequestProcessing(httpRequest,token);
		}
		return false;
	}

	private void checkLogout(HttpServletRequest httpRequest) {
		if (currentLink(httpRequest).equals(logoutLink)) {
			String token = httpRequest.getHeader(XAUTHTOKEN);
			// we go here only authenticated, token must not be null
			authenticationService.logout(token);
			doNotContinueWithRequestProcessing(httpRequest,token);
		}
	}

	// or use Springs util instead: new
	// UrlPathHelper().getPathWithinApplication(httpRequest)
	// shame on Servlet API for not providing this without any hassle :-(
	private String currentLink(HttpServletRequest httpRequest) {
		if (httpRequest.getPathInfo() == null) {
			return httpRequest.getServletPath();
		}
		return httpRequest.getServletPath() + httpRequest.getPathInfo();
	}

	/**
	 * This is set in cases when we don't want to continue down the filter chain. This occurs for any {@link HttpServletResponse#SC_UNAUTHORIZED} and also for login or logout.
	 */
	private void doNotContinueWithRequestProcessing(HttpServletRequest httpRequest,String token) {
		
		//if(token != null && !token.isEmpty()){
			httpRequest.setAttribute(REQUEST_ATTR_DO_NOT_CONTINUE, "");
		//}
	}

	private boolean canRequestProcessingContinue(HttpServletRequest httpRequest) {
		return httpRequest.getAttribute(REQUEST_ATTR_DO_NOT_CONTINUE) == null;
	}



	
}
