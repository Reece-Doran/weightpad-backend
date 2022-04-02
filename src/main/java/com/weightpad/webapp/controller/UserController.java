package com.weightpad.webapp.controller;


import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weightpad.webapp.model.Role;
import com.weightpad.webapp.model.User;
import com.weightpad.webapp.service.ScheduleService;
import com.weightpad.webapp.service.UserService;
import com.weightpad.webapp.config.Config;

@RestController
//@CrossOrigin(origins = "https://college-project-frontend.herokuapp.com/")
@CrossOrigin(origins = Config.frontendDomain)
//@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/api/v1")
public class UserController {
	
	private final UserService userService;
	
	private final ScheduleService scheduleService;
	
	
	public UserController(UserService userService, ScheduleService scheduleService) {
		super();
		this.userService = userService;
		this.scheduleService = scheduleService;
	}
//	
//	@PostMapping("/test/user")
//	public Object getUser(@RequestBody Object user) {
//		System.out.println("at controller");
//
//		return user;
//	}
	
	@GetMapping("/userinfo/email/{username}")
	public User userInfoByEmail(@PathVariable String username) {

		return userService.getUser(username);

	}
	
	
	@GetMapping("/userinfo/{id}")
	public User userInfoById(@PathVariable String id) {
		System.out.println(id);
		Long longId = Long.parseLong(id);

		return userService.findById(longId);
	}
	
//	@GetMapping("/users")
//	public ResponseEntity<List<User>> getUsers() {
//		return ResponseEntity.ok().body(userService.getUsers());	
//	}
//	
	
	
	@PostMapping("/register")
	public ResponseEntity<User> registerUser(@RequestBody User user) {
//		System.out.println(user.toString());
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/register").toUriString());
		return ResponseEntity.created(uri).body(userService.saveUser(user));	
//		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/register").toUriString());
//		return ResponseEntity.created(uri).body(userService.saveUser(user));	
	}
	
	
	
	// for email token
	@GetMapping("/confirm")
    public String confirmEmailToken(@RequestParam("token") String token) {
        return userService.confirmEmailToken(token);
    }
	
	
	
	
	// This is for JWT refresh token
	@GetMapping("/refreshtoken")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws StreamWriteException, DatabindException, IOException {
		String authHeader = request.getHeader("Authorization");
		if(authHeader != null && authHeader.startsWith("Bearer ")) {
			
			try {
				String refresh_token = authHeader.replace("Bearer ", "");
				
				Algorithm alg = Algorithm.HMAC256("secret".getBytes());
				JWTVerifier verifier = JWT.require(alg).build();					
				DecodedJWT decodedToken = verifier.verify(refresh_token);		
				
				String username = decodedToken.getSubject();
				
				User user = userService.getUser(username);
				
				String access_token = JWT.create()
						.withSubject(user.getUsername())
						.withExpiresAt(new Date(System.currentTimeMillis()+15 *60*1000))
//						.withExpiresAt(new Date(System.currentTimeMillis()+1 *60*1000))
						.withIssuer(request.getRequestURL().toString())
						.withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
						.sign(alg);
				
				
				Map<String, String> tokens = new HashMap<>();
				tokens.put("access_token", access_token);
				tokens.put("refresh_token", refresh_token);
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), tokens);
				
				
				
			} catch(Exception e) {
				response.setHeader("error", e.getMessage());
				response.setStatus(HttpStatus.FORBIDDEN.value());				
				Map<String, String> error = new HashMap<>();
				error.put("error_msg", e.getMessage());
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), error);
			}
		} else throw new RuntimeException("Refresh token cannot be found");
		
	}
	
	
	
}

