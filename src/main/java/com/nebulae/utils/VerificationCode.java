package com.nebulae.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class VerificationCode {
	
	public static Map<String,Object> getGraphics(int width,int height,int number){
		BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		//产生image类的Graphics用于绘制操作
		Graphics g=image.getGraphics();
		//设置Graphice类样式
		g.setColor(VerificationCode.getRandColor(200, 250));
		g.setFont(new Font("宋体", Font.BOLD, 25));
		g.fillRect(0, 0, width, height);
		Random random=new Random();
		String hash=Integer.toHexString(random.nextInt());
		//生成缓冲区image类
		//绘制干扰线
		for(int i=0;i<50;i++){
			g.setColor(VerificationCode.getRandColor(130, 200));
			int x1=random.nextInt(width);
			int y1=random.nextInt(height);
			int x2=random.nextInt(width);
			int y2=random.nextInt(height);
			g.drawLine(x1, y1, x2, y2);
					
		}
		//绘制字符
		String strCode=hash.substring(0,number);
		g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));
		g.drawString(strCode, 8, 24);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("image", image);
		map.put("validateCode", strCode);
		g.dispose();
		return map;
	}
	
	
	
	public static Color getRandColor(int s,int e){  
        Random random=new Random ();  
        if(s>255) s=255;  
        if(e>255) e=255;  
        int r,g,b;  
        r=s+random.nextInt(e-s);    //随机生成RGB颜色中的r值  
        g=s+random.nextInt(e-s);    //随机生成RGB颜色中的g值  
        b=s+random.nextInt(e-s);    //随机生成RGB颜色中的b值  
        return new Color(r,g,b);  
    } 

}
