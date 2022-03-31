package com.weightpad.webapp;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;


import com.weightpad.webapp.repository.UserRepository;

@SpringBootApplication
public class WebappApplication {
	
	
	public static void main(String[] args) {
		SpringApplication.run(WebappApplication.class, args);
	}
	

}
