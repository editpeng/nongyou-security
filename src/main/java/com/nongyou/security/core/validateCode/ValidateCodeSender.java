package com.nongyou.security.core.validateCode;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public interface ValidateCodeSender {
	void send(ValidateCode code,HttpServletResponse response) throws IOException;
}
