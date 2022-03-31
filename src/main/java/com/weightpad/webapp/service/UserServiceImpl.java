package com.weightpad.webapp.service;

import java.util.List;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weightpad.webapp.config.Config;
import com.weightpad.webapp.email.template.TokenVerificationEmailBuilder;
import com.weightpad.webapp.model.Role;
import com.weightpad.webapp.model.Schedule;
import com.weightpad.webapp.model.Token;
import com.weightpad.webapp.model.User;
import com.weightpad.webapp.repository.RoleRepository;
import com.weightpad.webapp.repository.UserRepository;


@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService{
	
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	private final TokenService tokenService;
	private final EmailService emailService;
	private final ScheduleService scheduleService;
	
	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, TokenService tokenService, EmailService emailService, ScheduleService scheduleService) {
		super();
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.tokenService = tokenService;
		this.emailService = emailService;
		this.scheduleService = scheduleService;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepository.findByUsername(username);
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities());
	}
	
	@Override
	public Boolean checkForEmail(String username) {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			return false;
		} else return true;
		
	}
	
	@Override
	public Boolean checkIfEnabled(String username) {
		return userRepository.checkIfEnabled(username);
	}
	

	@Override
	public User saveUser(User user) {
		
		// checks if the email/user name exists and saves user if false
		if (!checkForEmail(user.getUsername())) {
			
			
			/* sets the new users role as "ROLE_USER" by default, 
			   as this method is for users exclusively
			 */			
			
			//TODO specific responses need to be sent back to the front-end for error handling
			
			Collection<Role> newRoles= new ArrayList<>();
			Role newRole = new Role("ROLE_USER");
			newRoles.add(newRole);
			
			user.setPassword(passwordEncoder.encode(user.getPassword()));		
			
			User newUser = new User(user.getUsername(), user.getPassword(), newRoles);
			
			userRepository.save(newUser);
						
			Schedule newSchedule = new Schedule();
			scheduleService.saveSchedule(newSchedule);
			newSchedule.setUser(newUser);
			newUser.setSchedule(newSchedule);
			// saves confirmation token
			
			Token newToken = tokenService.generateToken(newUser);
			tokenService.saveToken(newToken);
			
			// send email
			Config config = new Config();
			TokenVerificationEmailBuilder emailBuilder = new TokenVerificationEmailBuilder(config.getDomain()+"confirm?token="+newToken.getToken());
			
			emailService.send(user.getUsername(), emailBuilder.build());
			
			System.out.println("Registering user");
			return newUser;
			
		} else if (checkForEmail(user.getUsername()) && !checkIfEnabled(user.getUsername())) {
			
			User existingUser = getUser(user.getUsername());
			
			Token token = tokenService.findTokenByUser(existingUser.getId());
			
			tokenService.updateToken(token.getId(), LocalDateTime.now(), 
					LocalDateTime.now().plusMinutes(15));
			System.out.println("Updating token");
			return null;
			
		} else {
			System.out.println("User is already registered");
			return null; 
		}
	}
	
	@Override
	public int enableUser(String email) {
        return userRepository.enableUser(email);
    }
	
	@Transactional
	@Override
    public String confirmEmailToken(String token) {
        Token confirmationToken = tokenService
                .getToken(token);
//                .orElseThrow(() ->
//                        new IllegalStateException("token not found"));
        if (confirmationToken==null) {
        	new IllegalStateException("token not found");
        }
        
        if (confirmationToken.getConfirmed() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpires();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        tokenService.setConfirmed(token);
        enableUser(
                confirmationToken.getUser().getUsername());
        System.out.println("confirmed");
        
        return "confirmed";
    }
	

	@Override
	public Role saveRole(Role role) {
		return roleRepository.save(role);
	}

	@Override
	public void addRoleToUser(String username, String roleName) {
		User user = userRepository.findByUsername(username);
		Role role = roleRepository.findByName(roleName);
		user.getRoles().add(role);
	}

	@Override
	public User getUser(String username) {
		return userRepository.findByUsername(username);
	}

//	@Override
//	public List<User> getUsers() {
//		return userRepository.findAll();
//	}

	@Override
	public User findById(Long id) {
		return userRepository.getByUserId(id);
	}

	

	
}
