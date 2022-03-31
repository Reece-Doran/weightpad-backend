package com.weightpad.webapp.service;

import java.time.LocalDateTime;


import com.weightpad.webapp.model.Token;
import com.weightpad.webapp.model.User;


public interface TokenService {
	
	void saveToken(Token token);
		
	Token generateToken(User user);
	
	Token getToken(String token);

	int setConfirmed(String token);

	void updateToken(Long token, LocalDateTime created, LocalDateTime expires);

	Token findTokenByUser(Long id);
}
