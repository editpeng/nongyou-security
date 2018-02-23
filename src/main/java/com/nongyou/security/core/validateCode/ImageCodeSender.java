package com.nongyou.security.core.validateCode;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

public class ImageCodeSender implements ValidateCodeSender {

	@Override
	public void send(ValidateCode code, HttpServletResponse response) throws IOException {
		ImageCode imgCode = (ImageCode)code;
		ImageIO.write(imgCode.getBufferedImage(), "JPEG", response.getOutputStream());
	}

}
