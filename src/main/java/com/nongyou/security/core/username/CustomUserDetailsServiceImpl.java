package com.nongyou.security.core.username;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomUserDetailsServiceImpl implements UserDetailsService {
	private CustomUserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if(StringUtils.isEmpty(username)) {
			return null;
		}
		
		if(userService == null) {
			throw new RuntimeException("A dependency of CustomUserService instance required!");
		}
		
		if(username.matches("^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$")) {
			return userService.findByMobile(username);
		}
		return userService.findByUsername(username);
	}
}
