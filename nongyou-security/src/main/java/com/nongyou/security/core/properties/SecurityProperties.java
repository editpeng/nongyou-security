package com.nongyou.security.core.properties;

import lombok.Data;

@Data
public class SecurityProperties {
  private UsernamePasswordSecurityProperties usernamePasswordSecurityProperties = new UsernamePasswordSecurityProperties();
  private ValidateCodeSecurityProperties validateCodeSecurityProperties = new ValidateCodeSecurityProperties();
}
