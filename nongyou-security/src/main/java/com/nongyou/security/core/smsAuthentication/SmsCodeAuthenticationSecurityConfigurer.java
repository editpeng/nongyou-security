package com.nongyou.security.core.smsAuthentication;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SmsCodeAuthenticationSecurityConfigurer
		extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
	private AuthenticationSuccessHandler successHandler;
	private AuthenticationFailureHandler failureHandler;
	private UserDetailsService userDetailsService;

	@Override
	public void configure(HttpSecurity builder) throws Exception {
		SmsCodeAuthenticationFilter smsCodeAuthenticationFilter = new SmsCodeAuthenticationFilter();
		smsCodeAuthenticationFilter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class));
		smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(successHandler);
		smsCodeAuthenticationFilter.setAuthenticationFailureHandler(failureHandler);

		SmsCodeAuthenticationProvider authenticationProvider = new SmsCodeAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		builder.authenticationProvider(authenticationProvider).addFilterAfter(smsCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
