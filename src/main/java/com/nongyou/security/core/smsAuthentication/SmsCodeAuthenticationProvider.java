package com.nongyou.security.core.smsAuthentication;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import lombok.Data;

@Data
public class SmsCodeAuthenticationProvider implements AuthenticationProvider{
	private UserDetailsService userDetailsService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		UserDetails user =userDetailsService.loadUserByUsername((String)authentication.getPrincipal());
		if(user == null) {
			throw new InternalAuthenticationServiceException("无法获取用户信息");
		}
		SmsCodeAuthenticationToken authenticationResult = new SmsCodeAuthenticationToken(user, user.getAuthorities());
		authenticationResult.setDetails(authentication.getDetails());
		return authenticationResult;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
	}


}
