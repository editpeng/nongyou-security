package com.nongyou.security.core.username;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.nongyou.security.core.properties.SecurityProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomUserServiceImpl implements CustomUserService {
	private SecurityProperties securityProperties;
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails findByUsername(String username) {
		if (securityProperties == null) {
			throw new RuntimeException("A dependency of SecurityProperties instance required!");
		}
		if (passwordEncoder == null) {
			throw new RuntimeException("A dependency of PasswordEncoder instance required!");
		}
		Map<String, String> accounts = securityProperties.getUsernamePasswordSecurityProperties().getAccounts();
		String password = accounts.get(username);
		
		if (StringUtils.isEmpty(password)) {
			return null;
		}

		return new User(username, passwordEncoder.encode(password), AuthorityUtils.createAuthorityList("admin"));
	}

	@Override
	public UserDetails findByMobile(String mobile) {
		// TODO Auto-generated method stub
		return null;
	}

}
