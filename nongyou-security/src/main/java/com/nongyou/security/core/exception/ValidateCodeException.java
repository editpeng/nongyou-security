package com.nongyou.security.core.exception;

import org.springframework.security.core.AuthenticationException;

public class ValidateCodeException extends AuthenticationException {

	private static final long serialVersionUID = -4977788405637211776L;

	public ValidateCodeException(String msg) {
		super(msg);
	}

}
