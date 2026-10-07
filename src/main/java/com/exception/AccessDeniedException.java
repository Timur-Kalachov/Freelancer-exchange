package com.exception;

public class AccessDeniedException extends RuntimeException {

	public AccessDeniedException(String role,String userEmail) {
		super("Access denied( role: "+ role+" | "+"email: " + userEmail+" )");
	}
	
}
