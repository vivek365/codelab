package com.codelab.configuration;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.codec.Base64;

public class TokenManagerSingle implements TokenManager {
	private Map<String, UserDetails> validUsers = new HashMap<>();
	static final Logger logger = LoggerFactory.getLogger(TokenManagerSingle.class);

	/**
	 * This maps system users to tokens because equals/hashCode is delegated to
	 * User entity. This can store either one token or list of them for each
	 * user, depending on what you want to do. Here we store single token, which
	 * means, that any older tokens are invalidated.
	 */
	private Map<UserDetails, TokenInfo> tokens = new HashMap<>();

	public TokenInfo createNewToken(UserDetails userDetails)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String token;
		do {
			token = generateToken();
		} while (validUsers.containsKey(token));

		TokenInfo tokenInfo = new TokenInfo(token, userDetails);
		removeUserDetails(userDetails);
		UserDetails previous = validUsers.put(token, userDetails);
		if (previous != null) {
			logger.debug(" *** SERIOUS PROBLEM HERE - we generated the same token (randomly?)!");
			return null;
		}
		tokens.put(userDetails, tokenInfo);

		return tokenInfo;
	}

	public void removeUserDetails(UserDetails userDetails) {
		TokenInfo token = tokens.remove(userDetails);
		if (token != null) {
			validUsers.remove(token.getToken());
		}
		
	}

	public UserDetails removeToken(String token) {
		UserDetails userDetails = validUsers.remove(token);
		if (userDetails != null) {
			tokens.remove(userDetails);
		}
		return userDetails;
	}

	public UserDetails getUserDetails(String token) {
		return validUsers.get(token);
	}

	public Collection<TokenInfo> getUserTokens(UserDetails userDetails) {
		return Arrays.asList(tokens.get(userDetails));
	}

	public Map<String, UserDetails> getValidUsers() {
		return Collections.unmodifiableMap(validUsers);
	}


	@SuppressWarnings("static-access")
	private String generateToken() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		byte[] tokenBytes = new byte[32];
		new SecureRandom().getInstance("SHA1PRNG").nextBytes(tokenBytes);
		return new String(Base64.encode(tokenBytes), StandardCharsets.ISO_8859_1);
	}





	
}
