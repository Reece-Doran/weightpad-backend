package com.weightpad.webapp.service;

import java.util.List;



import com.weightpad.webapp.model.Role;
import com.weightpad.webapp.model.User;


public interface UserService {
	User findById(Long id);
	
	User saveUser(User user);
	
	Boolean checkForEmail(String username);
	
	Boolean checkIfEnabled(String username);
	
	Role saveRole(Role role);
	
	void addRoleToUser(String username, String role);
	
	User getUser(String username);
	
//	List<User> getUsers();
	
	String confirmEmailToken(String token);
	
//	String buildEmail(String name, String link);

	int enableUser(String email);
}



	
	
	
