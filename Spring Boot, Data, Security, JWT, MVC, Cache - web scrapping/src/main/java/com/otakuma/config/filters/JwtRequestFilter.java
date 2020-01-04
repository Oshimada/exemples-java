package com.otakuma.config.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.otakuma.data.admin.Token;
import com.otakuma.services.admin.TokenService;
import com.otakuma.utils.JwtUtils;

@Component
public class JwtRequestFilter extends OncePerRequestFilter{

	@Autowired
	UserDetailsService userservice;
	
	@Autowired
	JwtUtils jwtutils;
	
	@Autowired
	TokenService tokenservice;
	
    private static final String SCHEME = "Bearer";
    private static final String AUTHORIZATION = "Authorization";
    
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		
		String authHeader = request.getHeader(AUTHORIZATION);
		
		if(authHeader == null || !authHeader.startsWith(SCHEME)) {
			chain.doFilter(request, response);
			return;
		}
		
		
		
		String jwtoken  = authHeader.substring((SCHEME+" ").length() );
		String accestoken = jwtutils.extractUsername(jwtoken); 
		
		if(accestoken == null || SecurityContextHolder.getContext().getAuthentication() != null) {
			chain.doFilter(request, response);
			return;
		}
		
		Token token = tokenservice.getToken(accestoken);

		if(token == null) {	
			chain.doFilter(request, response);
			return;
		}
		
		UserDetails details = userservice.loadUserByUsername(token.getPseudo());
		
		if(!jwtutils.validateToken(token.getPseudo(), jwtoken, details)) {
			chain.doFilter(request, response);
			return;
		}
		
		UsernamePasswordAuthenticationToken usertoken = new UsernamePasswordAuthenticationToken(
				details, null, details.getAuthorities());
		
		usertoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		
		SecurityContextHolder.getContext().setAuthentication(usertoken);
		
		chain.doFilter(request, response);
	}

}









