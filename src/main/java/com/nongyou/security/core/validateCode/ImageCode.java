package com.nongyou.security.core.validateCode;

import java.awt.image.BufferedImage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class ImageCode extends ValidateCode {
	private BufferedImage bufferedImage;

	public ImageCode(String code, int expiredInSeconds, BufferedImage bufferedImage) {
		super(code, expiredInSeconds);
		this.bufferedImage = bufferedImage;
	}	
}
