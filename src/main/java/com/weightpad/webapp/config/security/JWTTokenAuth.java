package com.weightpad.webapp.config.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import static java.util.Arrays.stream;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;


public class JWTTokenAuth extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		if(request.getServletPath().equals("/api/v1/login") || request.getServletPath().equals("/api/v1/refreshtoken")) {
			filterChain.doFilter(request, response);
		} else {
			String authHeader = request.getHeader(AUTHORIZATION);
			
			if(authHeader != null && authHeader.startsWith("Bearer ")) {
								
				try {
					String token = authHeader.replace("Bearer ", "");
					
					KeyGen alg = new KeyGen();
					
					JWTVerifier verifier = JWT.require(alg.getKey()).build();					
					DecodedJWT decodedToken = verifier.verify(token);		
					
					String username = decodedToken.getSubject();
					String[] roles = decodedToken.getClaim("roles").asArray(String.class);
					Collection<SimpleGrantedAuthority> auth = new ArrayList<>();
					
					stream(roles).forEach( role -> {
						auth.add(new SimpleGrantedAuthority(role));
					});
									
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null, auth);
					SecurityContextHolder.getContext().setAuthentication(authToken);
					filterChain.doFilter(request, response);
					
				} catch(Exception e) {
					response.setHeader("error", e.getMessage());
					response.setStatus(HttpStatus.FORBIDDEN.value());
//					response.sendError(HttpStatus.FORBIDDEN.value());
					
					
					Map<String, String> error = new HashMap<>();
					error.put("error_msg", e.getMessage());
					response.setContentType(MediaType.APPLICATION_JSON_VALUE);
					new ObjectMapper().writeValue(response.getOutputStream(), error);
				}
			} else filterChain.doFilter(request, response);
		}
		
	}
	
	
}
