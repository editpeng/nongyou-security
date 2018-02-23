package com.nongyou.security.core.validateCode;

import javax.servlet.http.HttpServletRequest;

public interface ValidateCodeGenerator {
	ValidateCode create(HttpServletRequest request);
}
