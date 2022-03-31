package com.weightpad.webapp.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.weightpad.webapp.model.Token;
import com.weightpad.webapp.model.User;
import com.weightpad.webapp.repository.TokenRepository;

@Service
public class TokenServiceImpl implements TokenService {
	
	private final TokenRepository tokenRepository;

	public TokenServiceImpl(TokenRepository tokenRepository) {
		super();
		this.tokenRepository = tokenRepository;
	}
	
	public void saveToken(Token token) {
		tokenRepository.save(token);
	}
	
	
	@Override
	public void updateToken(Long token, LocalDateTime created, LocalDateTime expires) {
		tokenRepository.update(token, created, expires);
		
	}
	
	@Override
	public Token findTokenByUser(Long id) {
		return tokenRepository.findTokenByUser(id);
	}
	
	@Override
	public Token generateToken(User user) {
		String tokenId= UUID.randomUUID().toString();
		Token newtoken = new Token(
				tokenId, 
				LocalDateTime.now(), 
				LocalDateTime.now().plusMinutes(15),
				user);
		return newtoken;
	}
	@Override
	public Token getToken(String token) {
        return tokenRepository.findByToken(token);
    }
	
	@Override
	public int setConfirmed(String token) {
        return tokenRepository.updateConfirmedAt(
                token, LocalDateTime.now());
    }

	

	
}
