package com.nongyou.security.core.validateCode;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.RandomUtils;
import com.nongyou.security.core.properties.SecurityProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmsCodeGenerator implements ValidateCodeGenerator {
	private SecurityProperties securityProperties;
	
	@Override
	public ValidateCode create(HttpServletRequest request) {
		if(securityProperties == null) {
			throw new RuntimeException("A dependency of SecurityProperties instance required!");
		}
		String mobile = request.getParameter("mobile");
		Integer random = RandomUtils.nextInt(100000, 1000000);		
		return new SmsCode(random.toString(), securityProperties.getValidateCodeSecurityProperties().getExpireInSeconds(), mobile);
	}

}
