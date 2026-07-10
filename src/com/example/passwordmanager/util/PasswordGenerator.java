package com.example.passwordmanager.util;

import java.security.SecureRandom;

public class PasswordGenerator {
	
	private static final String CHARACTERS =
	        "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
	      + "abcdefghijklmnopqrstuvwxyz"
	      + "0123456789"
	      + "!@#$%^&*=_";
	
	private static final SecureRandom random = new SecureRandom();
	
	public String generate(int length) {
		StringBuilder password = new StringBuilder();
		for(int i = 0; i < length; i++) {
			int index = random.nextInt(CHARACTERS.length());
			password.append(CHARACTERS.charAt(index));
		}
		return password.toString();
	}
}
