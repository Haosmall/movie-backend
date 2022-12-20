package com.moviebackend.exception;

import java.util.Map;

public class MyExceptionHelper {

	private static IllegalArgumentException illegalArgumentException;
	private static AuthenticationException authenticationException;

	public static IllegalArgumentException throwIllegalArgumentException() {

		if (illegalArgumentException == null)
			illegalArgumentException = new IllegalArgumentException("Invalid input parameter");

		return illegalArgumentException;
	}

	public static ResourceNotFoundException throwResourceNotFoundException(String message) {

		return new ResourceNotFoundException(message);
	}

	public static AuthenticationException throwAuthenticationException() {

		if (authenticationException == null)
			authenticationException = new AuthenticationException("No permission to access");

		return authenticationException;
	}

	public static EntityValidatorException throwEntityValidatorException(Map<String, String> errors) {

		return new EntityValidatorException(errors);
	}

	public static RuntimeCustomException throwRuntimeCustomException(Object error) {

		return new RuntimeCustomException(error);
	}

}
