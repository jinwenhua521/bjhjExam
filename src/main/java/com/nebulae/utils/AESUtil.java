package com.nebulae.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * AES加解密工具类
 * @version 1.0
 * @since 1.0
 */
public class AESUtil {
  /**
   * 编码规则
   */
  private static final String encodeRules = "li";
  
  
  public static final String DEFAULT_CODING = "utf-8";
  
  
  
  /**
	 * 解密
	 * @author lmiky
	 * @date 2014-2-25
	 * @param encrypted
	 * @param seed
	 * @return
	 * @throws Exception
	 */
  public static String decrypt(String encrypted){
	    
		byte[] keyb;
		byte[] clearbyte=null;
		try {
			keyb = encodeRules.getBytes(DEFAULT_CODING);
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] thedigest = md.digest(keyb);
			SecretKeySpec skey = new SecretKeySpec(thedigest, "AES");
			Cipher dcipher = Cipher.getInstance("AES");
			dcipher.init(Cipher.DECRYPT_MODE, skey);
			clearbyte = dcipher.doFinal(toByte(encrypted));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new String(clearbyte);
	}
	
	/**
	 * 加密
	 * @author lmiky
	 * @date 2014-2-25
	 * @param content
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String content){
		byte[] cipherText=null;
		try {
			byte[] input= content.getBytes(DEFAULT_CODING);
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] thedigest = md.digest(encodeRules.getBytes(DEFAULT_CODING));
			SecretKeySpec skc = new SecretKeySpec(thedigest, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, skc);
			cipherText = new byte[cipher.getOutputSize(input.length)];
			int ctLength = cipher.update(input, 0, input.length, cipherText, 0);
			ctLength += cipher.doFinal(cipherText, ctLength);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return parseByte2HexStr(cipherText);
	}
	
	
	/**
	 * 字符串转字节数组
	 * @author lmiky
	 * @date 2014-2-25
	 * @param hexString
	 * @return
	 */
	private static byte[] toByte(String hexString) {
		int len = hexString.length() / 2;
		byte[] result = new byte[len];
		for (int i = 0; i < len; i++) {
			result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
		}
		return result;
	}

	
	/**
	 * 字节转16进制数组
	 * @author lmiky
	 * @date 2014-2-25
	 * @param buf
	 * @return
	 */
	private static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex);
		}
		return sb.toString();
	}
  
  

    /**
     * 对字符串进行加密
     * @param content 需要加密的字符串
     * @return AES_encode 加密后的字符串
     */
    public static String AESEncode(String content){
        try{
            //1.构造密钥生成器，指定为AES算法，不区分大小写
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            //2.根据encodeRules规则初始化密钥生成器，生成一个128位的随机源，
            //根据传入的字节数组
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(encodeRules.getBytes());
            keyGenerator.init(128,secureRandom);
            //3.产生原始对称密钥
            SecretKey original_secretKey = keyGenerator.generateKey();
            //4.获取原始对称密钥的字节数组
            byte[] encoded = original_secretKey.getEncoded();
            //5.根据字节数组生成AES密钥
            SecretKey key = new SecretKeySpec(encoded, "AES");
            //6.根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance("AES");
            //7.初始化密码器第一个参数为加密或者解密操作，第二个参数为使用的key
            cipher.init(Cipher.ENCRYPT_MODE,key);
            //8.获取加密内容的字节数组，要设置编码为utf8不然内容中如果有中文的英文的混合中文就会解密乱码
            byte[] byte_encode = content.getBytes("utf8");
            //9.根据密码器的初始化方式--加密：将数据加密
            byte[] bytes_AES = cipher.doFinal(byte_encode);
            //10.将加密后的数据转换为字符串
            //这里的Base64Encoder中会找不到包
            //解决方法
            //在项目的Build path 中县一簇jre System Library ,再添加哭JRE System Library，重新变异后就一切正常了
            String AES_encode = new String(new BASE64Encoder().encode(bytes_AES));
            //11.将字符串返回
            return AES_encode;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        //如果有错误就返回null
        return null;
    }

    /**
     * 对字符串进行解密
     * @param content 需要解密的字符串
     * @return AES_decode 解密后的字符串
     */
    public static String AESDecode(String content){
        try {
            //1.构造密钥生成器，指定为AES算法，不区分大小写
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            //2.根据encodeRules规则初始化密钥生成器
            //生成一个128位的随机数，根据传入的字节数组
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(encodeRules.getBytes("utf-8"));
            keyGenerator.init(128,secureRandom);
            //3.产生原始对称密钥
            SecretKey original_secretKey = keyGenerator.generateKey();
            //4.获取原始对称密钥的字节数组
            byte[] encoded = original_secretKey.getEncoded();
            //5.根据字节数组生成AES密钥
            SecretKey aes = new SecretKeySpec(encoded, "AES");
            //6.根据指定算法AES自称密码器
            Cipher cipher = Cipher.getInstance("AES");
            //7.初始化密码器，第一个参数为加密或者是解密操作，第二个参数为使用的key
            cipher.init(Cipher.DECRYPT_MODE,aes);
            //8.将加密并编码后的内容解密成字节数组
            byte[] byte_content = new BASE64Decoder().decodeBuffer(content);
            //9.解密
            byte[] bytes = cipher.doFinal(byte_content);
            String AES_decode = new String(bytes,"utf-8");
            return AES_decode;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        //如果有错误返回的是null
        return null;
    }

}
