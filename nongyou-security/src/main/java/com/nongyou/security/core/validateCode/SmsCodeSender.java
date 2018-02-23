package com.nongyou.security.core.validateCode;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public class SmsCodeSender implements ValidateCodeSender {

	@Override
	public void send(ValidateCode code, HttpServletResponse response) throws IOException {
		response.getWriter().write(code.getCode());
	}

}
