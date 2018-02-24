package com.nongyou.security.core.username;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import com.nongyou.security.core.properties.SecurityProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class UsernamePasswordAuthenticationSecurityConfigurer{
	private SecurityProperties securityProperties;
	private AuthenticationSuccessHandler successHandler;
	private AuthenticationFailureHandler failureHandler;

	public void configureForm(HttpSecurity builder) throws Exception {
		if (securityProperties == null) {
			throw new RuntimeException("A dependency of SecurityProperties instance required!");
		}
		if (successHandler == null) {
			throw new RuntimeException("A dependency of AuthenticationSuccessHandler instance required!");
		}
		if (failureHandler == null) {
			throw new RuntimeException("A dependency of AuthenticationFailureHandler instance required!");
		}
		builder.formLogin()
				.loginPage(securityProperties.getUsernamePasswordSecurityProperties().getUnAuthenticatedRedirectUrl())
				.loginProcessingUrl(securityProperties.getUsernamePasswordSecurityProperties().getLoginProcessUrl())
				.successHandler(successHandler).failureHandler(failureHandler);
	}
}
