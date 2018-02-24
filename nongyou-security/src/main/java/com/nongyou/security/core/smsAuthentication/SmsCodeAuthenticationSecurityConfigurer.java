package com.nongyou.security.core.smsAuthentication;

import java.util.ArrayList;
import java.util.List;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SmsCodeAuthenticationSecurityConfigurer{
	private AuthenticationSuccessHandler successHandler;
	private AuthenticationFailureHandler failureHandler;
	private UserDetailsService userDetailsService;

	public void configureSmsAuth(HttpSecurity builder) throws Exception {
		SmsCodeAuthenticationFilter smsCodeAuthenticationFilter = new SmsCodeAuthenticationFilter();
		smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(successHandler);
		smsCodeAuthenticationFilter.setAuthenticationFailureHandler(failureHandler);
		
		List<AuthenticationProvider> providers= new ArrayList<>();
		SmsCodeAuthenticationProvider authenticationProvider = new SmsCodeAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		providers.add(authenticationProvider);
		smsCodeAuthenticationFilter.setAuthenticationManager(new ProviderManager(providers));

		
		builder.addFilterAfter(smsCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
