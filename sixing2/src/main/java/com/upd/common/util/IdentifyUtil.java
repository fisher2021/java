package com.upd.common.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;
/**
 * 生成验证码
 */
public class IdentifyUtil  {

	private int width = 60; 
	private int height = 20;
	public IdentifyUtil(int width, int height) { 
	 this.width = width > 0 ? width : this.width; 
	 this.height = height > 0 ? height : this.height; 
	 }
	public BufferedImage createImage(String randomString) {
		BufferedImage image = new BufferedImage(width, height, 
				BufferedImage.TYPE_INT_RGB); 
		// 获取图形上下文
     Graphics g = image.getGraphics(); 
      // 设定背景色 
     g.setColor(getRandColor(200, 250)); 
     g.fillRect(0, 0, width, height); 
     // 设定字体 
   g.setFont(new Font("楷体", Font.BOLD, 20));
      Random random = new Random(); 
     // 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到  
    g.setColor(getRandColor(160, 200)); 
     for (int i = 0; i < 155; i++){ 
         int x = random.nextInt(width); 
          int y = random.nextInt(height);
        int xl = random.nextInt(12); 
     int yl = random.nextInt(12); 
        g.drawLine(x, y, x + xl, y + yl); 
      } 
    char[] rondomChars=randomString.toCharArray();
      for (int i = 0; i < rondomChars.length; i++){
        g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
          g.drawChars(rondomChars, i, 1, 20* i + 8,24); 
     } 
        // 图象生效    
          g.dispose();  
       return image ; 
	}

	public Color getRandColor(int fc, int bc) {
		// 给定范围获得随机颜色
      Random random = new Random(); 
      if (fc > 255) 
      fc = 255; 
      if (bc > 255) 
      bc = 255;
      int r = fc + random.nextInt(bc - fc);
     int g = fc + random.nextInt(bc - fc); 
      int b = fc + random.nextInt(bc - fc); 
       return new Color(r, g, b); 
	}

	public String getRandomString() {
		Random random = new Random(); // 实例化一个Random对象
		String sRand = "";
		int itmp = 0;
		for (int i = 0; i < 4; i++){
				itmp = random.nextInt(10) + 48; // 生成0~9的数字
			   char ctmp = (char) itmp;
			sRand += String.valueOf(ctmp);
		}
		return sRand;
	}

}
