package com.weightpad.webapp.config.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.weightpad.webapp.service.UserService;



@Configuration
@EnableWebSecurity
public class SecurtiyConfig extends WebSecurityConfigurerAdapter {
	
	
//	private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	
	@Autowired
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final UserDetailsService userDetailsService;
	private final UserService userService;
//	private PasswordEncoder passwordEncoder;
	
//	@Autowired
	public SecurtiyConfig(UserDetailsService userDetailsService, UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.userDetailsService = userDetailsService;
		this.userService = userService;
		
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(bCryptPasswordEncoder);
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors();
		http.csrf().disable();
		http.httpBasic().disable();
		http.authorizeRequests().antMatchers("/api/v1/login").permitAll();
		JWTAuthFilter authFilter = new JWTAuthFilter(authenticationManagerBean(), userService);
		authFilter.setFilterProcessesUrl("/api/v1/login");
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.authorizeRequests().antMatchers("/api/v1/userinfo/email/**").authenticated();
		http.authorizeRequests().antMatchers("/api/v1/routine/**").authenticated();
		http.authorizeRequests().antMatchers("/api/v1/exercise/**").authenticated();
		http.authorizeRequests().antMatchers("/api/v1/schedule/**").authenticated();
		http.authorizeRequests().antMatchers("/api/v1/userinfo/**").authenticated();
		http.authorizeRequests().antMatchers("/api/v1/refreshtoken/**").permitAll();
		http.authorizeRequests().antMatchers("/api/v1/routinesByUserId/**").authenticated();
		http.authorizeRequests().antMatchers("/api/v1/user/**").authenticated();
		http.authorizeRequests().antMatchers("/api/v1/test/user/**").permitAll();
		http.authorizeRequests().antMatchers("/api/v1/confirm/**").permitAll();
		http.authorizeRequests().antMatchers("/api/v1/register/**").permitAll();

		http.authorizeRequests().anyRequest().authenticated();
		http.addFilter(authFilter);
		http.addFilterBefore(new JWTTokenAuth(), UsernamePasswordAuthenticationFilter.class);
		
		
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
		
	}
}
