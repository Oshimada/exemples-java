package com.otakuma.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.otakuma.config.Routes;
import com.otakuma.dto.authentication.AuthenticationRequest;
import com.otakuma.dto.authentication.AuthenticationResponse;
import com.otakuma.services.admin.ImpUserDetailsService;
import com.otakuma.utils.JwtUtils;

@RestController
public class AuthenticationController {

	@Autowired
	private AuthenticationManager auth;
	
	@Autowired
	private ImpUserDetailsService userservice;

	@Autowired
	private JwtUtils jwtutils;


	@RequestMapping(value = Routes.AUTHENTICATION + Routes.PING)
	public String ping() {
		return "Pong!";
	}
	
	
	
	@RequestMapping(method = RequestMethod.POST, value = Routes.AUTHENTICATION)
	public ResponseEntity<?> connecter( @RequestBody AuthenticationRequest request) throws Exception {
		
		try {
			auth.authenticate(new UsernamePasswordAuthenticationToken(request.getPseudo(), request.getPassword()));

			String jwt = jwtutils.generateToken(userservice.loadUserByUsername(request.getPseudo()));
			return ResponseEntity.ok(new AuthenticationResponse(jwt));
		}
		catch (BadCredentialsException bcex ) {
			bcex.printStackTrace();
			throw new BadCredentialsException("Pseudo ou mot de passe erronés");
		}
		catch(Exception ex ) {
			ex.printStackTrace();
			return null;
		}
	}
}
