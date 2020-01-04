package com.otakuma.data.admin;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ImpUserDetails implements UserDetails {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -357080297039126247L;
	
	private Long id;
	private String password;
	private String username;
	private Collection<? extends GrantedAuthority> authorities;

	
	public ImpUserDetails(Long id, String password, String username) {
		this.id = id;
		this.password = password;
		this.username = username;
	}
	
	public ImpUserDetails() {
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setPassword(String sr) {
		password = sr;
	}
	public void setUsername(String sr) {
		username= sr;
	}

	public void setAuthorities(HashSet<? extends GrantedAuthority> set) {
		authorities = set;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
	@Override
	public String getPassword() {
		return password;
	}
	
	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String toString() {
		return "ImplUserDetails [id=" + id + ", password=" + password + ", username=" + username + ", authorities="
				+ authorities + "]\n";
	}

	
}
