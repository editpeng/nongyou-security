package com.nongyou.security.core.properties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Data;

@Data
@ConfigurationProperties(prefix="nongyou.security")
public class SecurityProperties {
  private UsernamePasswordSecurityProperties usernamePasswordSecurityProperties = new UsernamePasswordSecurityProperties();
  private ValidateCodeSecurityProperties validateCodeSecurityProperties = new ValidateCodeSecurityProperties();
  private List<String> permitUrls = new ArrayList<>();
}
