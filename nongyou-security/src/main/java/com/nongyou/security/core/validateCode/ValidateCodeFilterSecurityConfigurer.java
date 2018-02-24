package com.nongyou.security.core.validateCode;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
public class ValidateCodeFilterSecurityConfigurer{
	private SecurityProperties securityProperties;

	public void configureValidateCode(HttpSecurity builder) throws Exception {
		if (securityProperties == null) {
			throw new RuntimeException("A dependency of SecurityProperties instance required!");
		}
		
		builder.addFilterBefore(new ValidateCodeFilter(securityProperties), UsernamePasswordAuthenticationFilter.class);
	}
}
