package com.nongyou.security.core.validateCode;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ValidateCode {
	private String code;
	private LocalDateTime expired;
	
	public ValidateCode(String code, int expiredInSeconds) {
		super();
		this.code = code;
		this.expired = LocalDateTime.now().plusSeconds(expiredInSeconds);
	}

	public boolean isExpire() {
		// TODO Auto-generated method stub
		return LocalDateTime.now().isAfter(expired);
	}
}
