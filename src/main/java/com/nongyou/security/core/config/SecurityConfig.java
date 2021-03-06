package com.nongyou.security.core.config;

import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import com.nongyou.security.core.properties.SecurityProperties;
import com.nongyou.security.core.smsAuthentication.SmsCodeAuthenticationSecurityConfigurer;
import com.nongyou.security.core.username.UsernamePasswordAuthenticationSecurityConfigurer;
import com.nongyou.security.core.username.rememberMe.RememberMeSecurityConfigurer;
import com.nongyou.security.core.validateCode.ValidateCodeFilterSecurityConfigurer;

@Configuration
@AutoConfigureAfter({UsernamePasswordAuthenticationSecurityConfig.class,ValidateCodeSecurityConfig.class})
public class SecurityConfig {
	@Bean
	public ValidateCodeFilterSecurityConfigurer validateCodeFilterSecurityConfigurer(
			SecurityProperties securityProperties) {
		return new ValidateCodeFilterSecurityConfigurer(securityProperties);
	}

	@Bean
	public UsernamePasswordAuthenticationSecurityConfigurer usernamePasswordAuthenticationSecurityConfigurer(
			SecurityProperties securityProperties, AuthenticationSuccessHandler authenticationSuccessHandler,
			AuthenticationFailureHandler authenticationFailureHandler) {
		return new UsernamePasswordAuthenticationSecurityConfigurer(securityProperties, authenticationSuccessHandler,
				authenticationFailureHandler);
	}

	@Bean
	@ConditionalOnProperty(havingValue = "true", name = "usernamePasswordSecurityProperties.rememberMe", prefix = "nongyou.security")
	public RememberMeSecurityConfigurer rememberMeSecurityConfigurer(DataSource dataSource,
			SecurityProperties securityProperties) {
		return new RememberMeSecurityConfigurer(dataSource, securityProperties);
	}

	@Bean
	public SmsCodeAuthenticationSecurityConfigurer smsCodeAuthenticationSecurityConfigurer(
			AuthenticationSuccessHandler successHandler, AuthenticationFailureHandler failureHandler,
			UserDetailsService userDetailsService) {
		return new SmsCodeAuthenticationSecurityConfigurer(successHandler, failureHandler, userDetailsService);
	}
	
	@Bean
	public WebSecurityConfigurerAdapter webSecurityConfigurerAdapter(UsernamePasswordAuthenticationSecurityConfigurer usernamePasswordAuthenticationSecurityConfigurer,
			ValidateCodeFilterSecurityConfigurer validateCodeFilterSecurityConfigurer, SmsCodeAuthenticationSecurityConfigurer smsCodeAuthenticationSecurityConfigurer,
			SecurityProperties securityProperties,DataSource dataSource) {
		return new WebSecurityConfigurerAdapter() {
			@Override
			protected void configure(HttpSecurity builder) throws Exception {
				usernamePasswordAuthenticationSecurityConfigurer.configureForm(builder);
				validateCodeFilterSecurityConfigurer.configureValidateCode(builder);
				smsCodeAuthenticationSecurityConfigurer.configureSmsAuth(builder);
				if(securityProperties.getUsernamePasswordSecurityProperties().isRememberMe()) {
					rememberMeSecurityConfigurer(dataSource,securityProperties).configureRememberMe(builder);;
				}
				
				String [] permitUrls = new String[securityProperties.getPermitUrls().size()];
				permitUrls = securityProperties.getPermitUrls().toArray(permitUrls);
				
				builder.authorizeRequests()
				.antMatchers(permitUrls)
				.permitAll().anyRequest().authenticated().and().csrf().disable();
			}
		};
	}
}
