package com.nongyou.security.core.properties;

public interface SecurityConstant {
	String SMS_SSESSION_KEY = "SESSION_KEY_SMS_CODE";
	String IMAGE_SESSION_KEY = "SESSION_KEY_IMAGE_CODE";
	int VALIDATE_CODE_EXCEPTION_CODE = 10000;
	int UNAUTHENTICATED_EXCEPTION_CODE = 10001;
	int SUCCESS_CODE = 0;
	String SMS_LOGIN_PROCESSING_URL = "/login/sms";
	String SMS_SEND_URL = "/code/sms";
	String IMG_SEND_URL= "/code/image";
}
