package com.nongyou.security.core.username.rememberMe;

import javax.sql.DataSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import com.nongyou.security.core.properties.SecurityProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RememberMeSecurityConfigurer{
	private DataSource dataSource;
	private SecurityProperties securityProperties;

	public void configureRememberMe(HttpSecurity builder) throws Exception {
		if (dataSource == null) {
			throw new RuntimeException("A dependency of DataSource instance required!");
		}
		if (securityProperties == null) {
			throw new RuntimeException("A dependency of SecurityProperties instance required!");
		}
		JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
		repo.setDataSource(dataSource);
		repo.setCreateTableOnStartup(true);
		builder.rememberMe().tokenRepository(repo).tokenValiditySeconds(
				securityProperties.getUsernamePasswordSecurityProperties().getRememberMeInSeconds());
	}
}
