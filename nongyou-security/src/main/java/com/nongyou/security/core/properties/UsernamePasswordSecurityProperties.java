package com.nongyou.security.core.properties;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;

@Data
public class UsernamePasswordSecurityProperties {
	private Map<String,String> accounts=new HashMap<>();
	private String unAuthenticatedRedirectUrl = SecurityConstant.FORM_LOGIN_PAGE;
	private String loginProcessUrl = SecurityConstant.LOGIN_PROCESS_URL;
	private Integer rememberMeInSeconds = 60*60*24*7;
	private boolean rememberMe = false;
}
