package com.nongyou.security.core.validateCode;

import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.context.request.ServletWebRequest;
import com.nongyou.security.core.properties.SecurityConstant;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ValidateCodeServlet extends HttpServlet {
	private static final long serialVersionUID = -4992468084219566208L;
	private Map<String,ValidateCodeGenerator> validateCodeGenerators;
	private Map<String,ValidateCodeSender> validateCodeSenders;
	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
	
	public ValidateCodeServlet(Map<String, ValidateCodeGenerator> validateCodeGenerators,
			Map<String, ValidateCodeSender> validateCodeSenders) {
		super();
		this.validateCodeGenerators = validateCodeGenerators;
		this.validateCodeSenders = validateCodeSenders;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ValidateCodeGenerator validateCodeGenerator = null;
		String sessionKey = "";
		ValidateCodeSender validateCodeSender = null;
		if(req.getRequestURI().endsWith("sms")) {
			validateCodeGenerator =  validateCodeGenerators.get("smsCodeGenerator");
			sessionKey = SecurityConstant.SMS_SESSION_KEY;
			validateCodeSender = validateCodeSenders.get("smsCodeSender");
			
		}
		if(req.getRequestURI().endsWith("image")) {
			validateCodeGenerator =  validateCodeGenerators.get("imageCodeGenerator");
			sessionKey = SecurityConstant.IMAGE_SESSION_KEY;
			validateCodeSender = validateCodeSenders.get("imageCodeSender");
		}
		
		ValidateCode validateCode = validateCodeGenerator.create(req);
		sessionStrategy.setAttribute(new ServletWebRequest(req), sessionKey, validateCode);
		validateCodeSender.send(validateCode, resp);	
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
