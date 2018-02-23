package com.nongyou.security.core.validateCode;

import java.io.IOException;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.ValidationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;
import com.nongyou.security.core.properties.SecurityConstant;
import com.nongyou.security.core.properties.SecurityProperties;

public class ValidateCodeFilter extends OncePerRequestFilter{
	private PathMatcher pathMatcher = new AntPathMatcher();
	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
	private SecurityProperties securityProperties;
	
	public PathMatcher getPathMatcher() {
		return pathMatcher;
	}

	public void setPathMatcher(PathMatcher pathMatcher) {
		this.pathMatcher = pathMatcher;
	}

	public SessionStrategy getSessionStrategy() {
		return sessionStrategy;
	}

	public void setSessionStrategy(SessionStrategy sessionStrategy) {
		this.sessionStrategy = sessionStrategy;
	}

	public ValidateCodeFilter() {
		super();
	}
	
	public ValidateCodeFilter(SecurityProperties securityProperties) {
		super();
		this.securityProperties = securityProperties;
	}

	public SecurityProperties getSecurityProperties() {
		return securityProperties;
	}

	public void setSecurityProperties(SecurityProperties securityProperties) {
		this.securityProperties = securityProperties;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		boolean actionImg = false;
		boolean actionSms = false;
		
		List<String> smsUrls = securityProperties.getValidateCodeSecurityProperties().getSmsCodeValidateUrls();
		List<String> imgUrls = securityProperties.getValidateCodeSecurityProperties().getImageCodeValidateUrls();
		
		for (String imgUrl : imgUrls) {
			if(pathMatcher.match(imgUrl, request.getRequestURI()) ) {
				actionImg = true;
				break;
			}
		}
		for (String smsUrl : smsUrls) {
			if(pathMatcher.match(smsUrl, request.getRequestURI()) ) {
				actionSms = true;
				break;
			}
		}
		try {
			if(actionImg) {
				validate(request, "img");
			}
			if(actionSms) {
				validate(request, "sms");
			}
		}
		catch(ValidationException e) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write("{\"code\":" + SecurityConstant.VALIDATE_CODE_EXCEPTION_CODE + ",\"msg\":\"" + e.getMessage() + "\"}");
			response.flushBuffer();
			return;
		}
		
		filterChain.doFilter(request, response);
	}
	
	private void validate(HttpServletRequest request,String which) throws ServletRequestBindingException, ValidationException {
		String sessionKey = "";
		String codeInRequest = "";
		
		if("img".equals(which)) {
			codeInRequest = ServletRequestUtils.getRequiredStringParameter(request,"imgCode" );
			sessionKey = SecurityConstant.IMAGE_SESSION_KEY;
		}
		if("sms".equals(which)) {
			codeInRequest = ServletRequestUtils.getRequiredStringParameter(request,"smsCode" );
			sessionKey = SecurityConstant.IMAGE_SESSION_KEY;
		}
		
		ValidateCode codeInSession = (ValidateCode) sessionStrategy.getAttribute(new ServletWebRequest(request), sessionKey);
		if(StringUtils.isEmpty(codeInRequest)) {
			throw new ValidationException("验证码不能为空");
		}
		
		if(codeInSession == null) {
			throw new ValidationException("验证码不存在");
		}
		
		if(codeInSession.isExpire()) {
			sessionStrategy.removeAttribute(new ServletWebRequest(request), sessionKey);
			throw new ValidationException("验证码已过期");
		}
		
		if(!codeInSession.getCode().equals(codeInRequest)) {
			throw new ValidationException("验证码错误");
		}
		
		sessionStrategy.removeAttribute(new ServletWebRequest(request), sessionKey);		
	}

}
