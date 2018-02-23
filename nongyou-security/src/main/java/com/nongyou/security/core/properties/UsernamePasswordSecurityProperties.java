package com.nongyou.security.core.properties;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class UsernamePasswordSecurityProperties {
	private Map<String,String> accounts=new HashMap<>();
	private String unAuthenticatedRedirectUrl = "/unAuthenticated/username";
	private String loginProcessUrl = "/login/username";
	private List<String> permitUrls = Arrays.asList(unAuthenticatedRedirectUrl);
	private Integer rememberMeInSeconds = 60*60*24*7;
	private boolean rememberMe = false;
}
