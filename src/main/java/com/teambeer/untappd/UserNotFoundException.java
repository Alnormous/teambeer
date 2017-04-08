package com.teambeer.untappd;

public class UserNotFoundException extends Exception {

	private static final long serialVersionUID = -8219835590810416002L;

	public UserNotFoundException() {
		super();
	}

	public UserNotFoundException(String message) {
		super(message);
	}
}
