package com.nebulae.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * MD5加密类
 * @author lb 375718726@qq.com 2017/08/21
 * @version 1.0
 * @since 1.0
 */
public class MD5Util {

	private static final String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };


	private static final String SALT = "yufei";

	private static final String ALGORITH_NAME = "md5";

	private static final int HASH_ITERATIONS = 2;

	public static String encode(String origin) {
		String resultString = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(origin.getBytes()));
		} catch (Exception localException) {
		}

		return resultString;
	}

	private static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString().toUpperCase();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

    /**
     * 对字节流进行单向MD5加密
     * @param bytes 字节数组
     * @return String类型返回值
     */
    private static String MD5EncodeUtf8(byte[] bytes) {
        //用于加密的字符
        char md5String[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            //信息摘要是安全的单向哈希函数，它接受任意大小的数据，并输出固定长度的哈希值
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            //MessageDigest 对象通过使用update方法处理数据，使用指定的byte数组更新摘要
            messageDigest.update(bytes);
            //摘要更新之后。通过调用digest()计算哈希值
            byte[] digest = messageDigest.digest();
            // 把密文转换成十六进制的字符串形式
            int j = digest.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (byte byte0 : digest) {   //  i = 0
                str[k++] = md5String[byte0 >>> 4 & 0xf];    //    5
                str[k++] = md5String[byte0 & 0xf];   //   F
            }
            return new String(str);
        } catch (Exception e) {
            //发生异常的时候返回为空
            return null;
        }
    }
    /**
     * 对字符串进行Md5加密
     * @param string 传入的字符串参数
     * @return String型返回结果
     */
    public static String MD5EncodeStrUtf8(String string){
        byte[] bytes = new byte[0];
        try {
            bytes = string.getBytes("utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return MD5EncodeUtf8(bytes);
    }

    public static String encrypt(String pswd) {
		String newPassword = new SimpleHash(ALGORITH_NAME, pswd, ByteSource.Util.bytes(SALT), HASH_ITERATIONS).toHex();
		return newPassword;
	}

	public static String encrypt(String username, String pswd) {
		String newPassword = new SimpleHash(ALGORITH_NAME, pswd, ByteSource.Util.bytes(username + SALT),
				HASH_ITERATIONS).toHex();
		return newPassword;
	}


	public static void main(String[] args) {
		String s=MD5EncodeStrUtf8("123456");
		System.out.println(s);
	}
}
