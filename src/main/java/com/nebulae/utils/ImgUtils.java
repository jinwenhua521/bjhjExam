package com.nebulae.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


import com.alibaba.druid.util.StringUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class ImgUtils  {
	
	 //图片转化成base64字符串
	public static String generateImageBase64Str(InputStream in) throws IOException{
		//将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		String result=null;
		if(in.available()!=0){
			byte[] data=null;
			try{
				data=new byte[in.available()];
				in.read(data);
				in.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			BASE64Encoder encoder=new  BASE64Encoder();
			result=encoder.encode(data);
		}
		return result;
	}
	
	//base64字符串转化成图片  
	public static boolean generateBase64InputStream(String base64,String path){
		if(!StringUtils.isEmpty(base64)){
			BASE64Decoder decoder=new BASE64Decoder();
			try {
				byte[] b=decoder.decodeBuffer(base64);
				for(int i=0;i<b.length;++i){
					if(b[i]<0){
						b[i]+=256;
					}
				}
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
				//生成图片
				String imgFilePath=path+"\\"+df.format(new Date())+".png";
			    OutputStream out=new FileOutputStream(imgFilePath);
			    out.write(b);
			    out.flush();
			    out.close();
			    return true;
			} catch (Exception e) {
				return false;
			}
		}else{
			return false;
		}
	}
	
	//base64字符串转化成byte
	public static byte[] generateBase64Byte(String base64){
		byte[] b=null;
		if(!StringUtils.isEmpty(base64)){
			BASE64Decoder decoder=new BASE64Decoder();
			try {
				b=decoder.decodeBuffer(base64);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return b;
	}
	
}
