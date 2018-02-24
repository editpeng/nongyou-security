package com.nongyou.security.core.properties;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ValidateCodeSecurityProperties {
	private Integer imageHeight = 30;
	private Integer imageWidth = 40;
	private Integer imageLetters = 4;
	private Integer expireInSeconds = 120;
	private Integer smsLetters = 6;
	private List<String> imageCodeValidateUrls = new ArrayList<>();
	private List<String> smsCodeValidateUrls = new ArrayList<>();
}
