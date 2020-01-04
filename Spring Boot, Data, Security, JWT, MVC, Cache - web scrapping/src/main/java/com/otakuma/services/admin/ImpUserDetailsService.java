package com.otakuma.services.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.otakuma.data.admin.ImpUserDetails;
import com.otakuma.repositories.admin.AdminLoginRepository;

@Service
public class ImpUserDetailsService implements UserDetailsService {

	@Autowired
	AdminLoginRepository loginrepo;
	@Autowired
	AdminService service;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		ImpUserDetails user = loginrepo.getUserDetailsByUsername(username)
			.orElseThrow(() -> new UsernameNotFoundException("Admin "+username+" introuvable"));
		
		user.setAuthorities(service.getAuthoritiesById(user.getId()));
		return user;
	}

}
