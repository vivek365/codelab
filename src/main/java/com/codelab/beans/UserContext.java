package com.codelab.beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserContext implements UserDetails {
	private static final long serialVersionUID = 1L;
	private User user;

	public UserContext(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		// for (String role : user.getRoles()) {
		// authorities.add(new SimpleGrantedAuthority(role));
		// }
		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUserName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		return this == o || o != null && o instanceof UserContext && Objects.equals(user, ((UserContext) o).user);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(user);
	}

	@Override
	public String toString() {
		return "UserContext{" + "user=" + user + '}';
	}

	public User getUser() {
		return this.user;
	}
}
