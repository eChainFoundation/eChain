package com.echain.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException;
 

public class Md5Util {
	
	private static MessageDigest messagedigest = null;
	
	private static char hexDigits1[] = { '0', '1', '2', '3', '4', '5', '6',  '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };  
	
	/**
     * 加密文件 2015年7月1日 上午12:06:37
     */
    public static String getMd5ByFile(File file) {
        String value = null;
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        try {
            MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
            BigInteger bi = new BigInteger(1, md5.digest());
            value = bi.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }

    private static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			resultSb.append(byteToHexString(b[i]));

		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}
	
	public   static String encode(String origin){
		return encode(origin,"UTF-8");
	}

	public  static  String encode(String origin, String charsetname) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetname == null || "".equals(charsetname))
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes()));
			else
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes(charsetname)));
		} catch (Exception exception) {
		}
		return resultString;
	}
	
	
	
	 /**
     * MessageDigest初始化
     * 
     * @author 高焕杰
     */
    static {
            try {
                    messagedigest = MessageDigest.getInstance("MD5");  
            } catch (NoSuchAlgorithmException e) {  
                    System.err.println("MD5FileUtil messagedigest初始化失败");  
                    e.printStackTrace();
            }  
    }
	
	 /**
     * 对byte类型的数组进行MD5加密
     * 
     */
    public static String getMD5String(byte[] bytes) {
            messagedigest.update(bytes);  
            return bufferToHex(messagedigest.digest());  
    }

    private static String bufferToHex(byte bytes[]) {
            return bufferToHex(bytes, 0, bytes.length);  
    }

    private static String bufferToHex(byte bytes[], int m, int n) {
            StringBuffer stringbuffer = new StringBuffer(2 * n);  
            int k = m + n;  
            for (int l = m; l < k; l++) {  
                    char c0 = hexDigits1[(bytes[l] & 0xf0) >> 4];
                    char c1 = hexDigits1[bytes[l] & 0xf];
                    stringbuffer.append(c0);  
                    stringbuffer.append(c1);  
            }  
            return stringbuffer.toString();  
    }

	
	
	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
		"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
	
	public static void main(String[] args) throws Exception{
		System.out.println(Md5Util.encode("http://yingchao.juhangye.com/201707/weixin_5082673.html"));
		System.out.println(Md5Util.encode("5535275","UTF-8"));
		
		String md5 = Md5Util.encode("System.out.println(Md5Util.encode(\"5535275\",\"UTF-8\"));","UTF-8");
		System.out.println(Md5Util.encode("System.out.println(Md5Util.encode(\"5535275\",\"UTF-8\"));","UTF-8"));
		
		String encode = Md5Util.encode("http://www.friok.com/37659.html ");
		System.out.println(encode);
	}
}
