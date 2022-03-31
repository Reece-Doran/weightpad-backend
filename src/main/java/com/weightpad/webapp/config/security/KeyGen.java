package com.weightpad.webapp.config.security;

import com.auth0.jwt.algorithms.Algorithm;

public class KeyGen {
	
	private Algorithm key = Algorithm.HMAC256("secret".getBytes());
	
	protected KeyGen () {}
	
	protected Algorithm getKey() {
		return key;
	}
}
