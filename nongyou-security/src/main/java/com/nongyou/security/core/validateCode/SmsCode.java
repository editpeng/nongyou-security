package com.nongyou.security.core.validateCode;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class SmsCode extends ValidateCode {
	private String mobile;
	
	public SmsCode(String code, int expiredInSeconds, String mobile) {
		super(code, expiredInSeconds);
		this.mobile = mobile;
	}	
}
