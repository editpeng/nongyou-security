package com.nongyou.security.core.username;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;

import com.nongyou.security.core.properties.SecurityConstant;

public class CustomUnAuthenticatedHandler extends HttpServlet {
	private static final long serialVersionUID = -3449709106544288500L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write("{\"code\":" + SecurityConstant.UNAUTHENTICATED_EXCEPTION_CODE + ",\"msg\":\"未认证\"}");
		response.flushBuffer();
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
