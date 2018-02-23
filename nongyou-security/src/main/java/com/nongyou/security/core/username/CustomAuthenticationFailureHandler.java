package com.nongyou.security.core.username;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.nongyou.security.core.properties.SecurityConstant;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write("{\"code\":" + SecurityConstant.UNAUTHENTICATED_EXCEPTION_CODE + ",\"msg\":\"" + exception.getMessage() + "\"}");
		response.flushBuffer();
	}
}
