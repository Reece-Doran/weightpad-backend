package com.weightpad.webapp.config;


public class Config {
//	private static String domain = "https://project-backend-springbot.herokuapp.com/api/v1/";

	// I have to pass this value into @CrossOrigin annotation in the API controller endpoints, however 
	// using a public static final variable was the only way I could get it to work as I cannot instantiate an object outside of a 
	// class, furthermore the relevant get method failed to work
	public static final String frontendDomain = "http://localhost:3000/";
	private String domain = "https://weightpad-backend.herokuapp.com/api/v1/";

	public  String getDomain() {
		return domain;
	}

	public static final String getFrontendDomain() {
		return frontendDomain;
	}
	
}
