package com.nongyou.security.core.validateCode;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.nongyou.security.core.properties.SecurityProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class ValidateCodeFilterSecurityConfigurer
		extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
	private SecurityProperties securityProperties;

	@Override
	public void configure(HttpSecurity builder) throws Exception {
		if (securityProperties == null) {
			throw new RuntimeException("A dependency of SecurityProperties instance required!");
		}
		
		builder.addFilterBefore(new ValidateCodeFilter(securityProperties), UsernamePasswordAuthenticationFilter.class);
	}
}
