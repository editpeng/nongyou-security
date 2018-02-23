package com.nongyou.security.core.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import com.nongyou.security.core.properties.SecurityProperties;
import com.nongyou.security.core.username.CustomAuthenticationFailureHandler;
import com.nongyou.security.core.username.CustomAuthenticationSuccessHandler;
import com.nongyou.security.core.username.CustomUnAuthenticatedHandler;
import com.nongyou.security.core.username.CustomUserDetailsServiceImpl;
import com.nongyou.security.core.username.CustomUserService;
import com.nongyou.security.core.username.CustomUserServiceImpl;

@Configuration
public class UsernamePasswordAuthenticationSecurityConfig {

	@Bean
	@ConditionalOnMissingBean(PasswordEncoder.class)
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@ConditionalOnMissingBean(CustomUserService.class)
	public CustomUserService customUserService(SecurityProperties securityProperties,PasswordEncoder passwordEncoder) {
		return new CustomUserServiceImpl(securityProperties,passwordEncoder);
	}

	@Bean
	@ConditionalOnMissingBean(UserDetailsService.class)
	public UserDetailsService userDetailsService(CustomUserService customUserService) {
		return new CustomUserDetailsServiceImpl(customUserService);
	}

	@Bean
	@ConditionalOnMissingBean(AuthenticationSuccessHandler.class)
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		return new CustomAuthenticationSuccessHandler();
	}

	@Bean
	@ConditionalOnMissingBean(AuthenticationFailureHandler.class)
	public AuthenticationFailureHandler authenticationFailureHandler() {
		return new CustomAuthenticationFailureHandler();
	}

	@Bean
	public ServletRegistrationBean authenticateServletRegistration(SecurityProperties securityProperties) {
		ServletRegistrationBean registration = new ServletRegistrationBean(new CustomUnAuthenticatedHandler());
		registration.addUrlMappings(
				securityProperties.getUsernamePasswordSecurityProperties().getUnAuthenticatedRedirectUrl());
		return registration;
	}	
}
