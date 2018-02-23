package com.nongyou.security.core.username;

import org.springframework.security.core.userdetails.UserDetails;

public interface CustomUserService {
	UserDetails findByUsername(String username);
	UserDetails findByMobile(String mobile);
}
