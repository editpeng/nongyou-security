package com.nongyou.security.core.exception;

import org.springframework.security.core.AuthenticationException;

public class SmsCodeException extends AuthenticationException {

	private static final long serialVersionUID = 8419215214074149316L;

	public SmsCodeException(String msg) {
		super(msg);
	}

}
