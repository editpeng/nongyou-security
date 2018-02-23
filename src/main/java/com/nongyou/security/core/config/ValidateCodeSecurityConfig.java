package com.nongyou.security.core.config;

import java.util.Map;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.nongyou.security.core.properties.SecurityConstant;
import com.nongyou.security.core.validateCode.ImageCodeGenerator;
import com.nongyou.security.core.validateCode.ImageCodeSender;
import com.nongyou.security.core.validateCode.SmsCodeGenerator;
import com.nongyou.security.core.validateCode.SmsCodeSender;
import com.nongyou.security.core.validateCode.ValidateCodeGenerator;
import com.nongyou.security.core.validateCode.ValidateCodeSender;
import com.nongyou.security.core.validateCode.ValidateCodeServlet;

@Configuration
public class ValidateCodeSecurityConfig {
	@Bean
	@ConditionalOnMissingBean(name="smsCodeGenerator")
	public ValidateCodeGenerator smsCodeGenerator() {
		return new SmsCodeGenerator();
	}
	
	@Bean
	@ConditionalOnMissingBean(name="imageCodeGenerator")
	public ValidateCodeGenerator imageCodeGenerator() {
		return new ImageCodeGenerator();
	}
	
	@Bean
	@ConditionalOnMissingBean(name="smsCodeSender")
	public ValidateCodeSender smsCodeSender() {
		return new SmsCodeSender();
	}
	
	@Bean
	@ConditionalOnMissingBean(name="imageCodeSender")
	public ValidateCodeSender imageCodeSender() {
		return new ImageCodeSender();
	}
	
	@Bean
	public ServletRegistrationBean validateCodeServletRegistration(Map<String,ValidateCodeGenerator> validateCodeGenerators,Map<String,ValidateCodeSender> validateCodeSenders) {
		ServletRegistrationBean registration = new ServletRegistrationBean(new ValidateCodeServlet(validateCodeGenerators,validateCodeSenders));
		registration.addUrlMappings(SecurityConstant.IMG_SEND_URL);
		registration.addUrlMappings(SecurityConstant.SMS_SEND_URL);
		return registration;
	}
}
