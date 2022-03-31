package com.weightpad.webapp.config.security;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weightpad.webapp.exception.AccountNotEnabledException;
import com.weightpad.webapp.exception.AccountNotFoundException;
import com.weightpad.webapp.service.UserService;


import org.springframework.http.*;

@CrossOrigin(origins = "http://localhost:3000/")
public class JWTAuthFilter extends UsernamePasswordAuthenticationFilter  {
	
	
	
	private final UserService userService;
	private final AuthenticationManager authManager;

	
	
	public JWTAuthFilter(AuthenticationManager authManager, UserService userService) {
		this.userService = userService;
		this.authManager = authManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			UsernameAndPasswordAuthRequest authRequest = new ObjectMapper().readValue(request.getInputStream(), UsernameAndPasswordAuthRequest.class);
			
			if(userService.checkIfEnabled(authRequest.getUsername())==null) {
				throw new AccountNotFoundException("Account has not been found");
			}
			
			if (!userService.checkIfEnabled(authRequest.getUsername())) {
				throw new AccountNotEnabledException("Account has not been enabled");
			}
						
			Authentication auth = new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());
			return authManager.authenticate(auth);
			
		} catch (IOException | AccountNotEnabledException | AccountNotFoundException e) {			
			response.setHeader("error", e.getMessage());
			response.setStatus(HttpStatus.FORBIDDEN.value());
			return null;
		} 
		
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		User user = (User) authResult.getPrincipal();
		KeyGen alg = new KeyGen();
		
		String access_token = JWT.create()
				.withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis()+15 *60*1000))
//				.withExpiresAt(new Date(System.currentTimeMillis()+1 *60*1000))
				.withIssuer(request.getRequestURL().toString())
				.withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.sign(alg.getKey());
		
		String refresh_token = JWT.create()
				.withSubject(user.getUsername())
//				.withExpiresAt(new Date(System.currentTimeMillis()+60 *60*1000))
				.withExpiresAt(new Date(System.currentTimeMillis()+60 *60*1000))
				.withIssuer(request.getRequestURL().toString())
				.sign(alg.getKey());
		
		
		Map<String, String> tokens = new HashMap<>();
		tokens.put("access_token", access_token);
		tokens.put("refresh_token", refresh_token);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		new ObjectMapper().writeValue(response.getOutputStream(), tokens);
		
	}


}
