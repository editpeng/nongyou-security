package com.nongyou.security.core.validateCode;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import com.nongyou.security.core.properties.SecurityProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageCodeGenerator implements ValidateCodeGenerator {
	private SecurityProperties securityProperties;
	
	@Override
	public ValidateCode create(HttpServletRequest request) {
		if(securityProperties == null) {
			throw new RuntimeException("A dependency of SecurityProperties instance required!");
		}
        int width = securityProperties.getValidateCodeSecurityProperties().getImageWidth();  
        int height = securityProperties.getValidateCodeSecurityProperties().getImageHeight();  
        int letters = securityProperties.getValidateCodeSecurityProperties().getImageLetters();
        
        if(request.getAttribute("height") != null) {
        	height = (Integer)request.getAttribute("height");
        }
        
        if(request.getAttribute("width") != null) {
        	width = (Integer)request.getAttribute("width");
        }
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);  
  
        Graphics g = img.getGraphics();  
  
        // 设置背景色  
        g.setColor(Color.WHITE);  
        g.fillRect(0, 0, width, height);  
  
        // 设置字体  
        g.setFont(new Font("Times New Roman", Font.BOLD, 20));  
  
        // 随机数字  
        Random r = new Random(new Date().getTime());
        String code="";
        for (int i = 0; i < letters; i++) {  
            int a = r.nextInt(10);
            code += a;
            int y = 10 + r.nextInt(height-20);
  
            Color c = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));  
            g.setColor(c);  
  
            g.drawString("" + a, 5 + i * width / letters, y);  
        }  
  
        // 干扰线  
        for (int i = 0; i < 15; i++) {  
            Color c = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));  
            g.setColor(c);  
            g.drawLine(r.nextInt(width), r.nextInt(height), r.nextInt(width), r.nextInt(height));  
        }  
  
        g.dispose();// 类似于流中的close()带动flush()---把数据刷到img对象当中 
        return new ImageCode(code, securityProperties.getValidateCodeSecurityProperties().getExpireInSeconds(), img);  
	}

}
