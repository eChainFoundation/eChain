package com.echain.util;
 
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;



/**
 * RSA 工具类。提供加密，解密，生成密钥对等方法。
 * 需要到http://www.bouncycastle.org下载bcprov-jdk14-123.jar
 * 方法使用说明：
 * 1.使用默认的私钥加密字符串，返回一个可以查看的加密后的字符串（非16进制数据）：encryptStringByDefaultPriateKey(String text)
 * 2.使用默认的私钥解密给定的字符串：decryptStringByDefaultPriateKey(String encrypttext)
 */
public class RSAUtil {
	//设置生成的密钥对的存放位置 
	private static String RSAKeyStore = ResourceConfig.getInstance().getValue("RSAKeyStore");
	static{
		checkRSAFile();
	}
	
	private static void checkRSAFile() {
		File rsaFile = new File(RSAKeyStore);
		if(!rsaFile.isAbsolute()) {	// 不是绝对路径的话从class path中获取
			RSAKeyStore = RSAUtil.class.getClassLoader().getResource(RSAKeyStore).getFile();
		}
	}
	
	
	/**
	 * * 生成密钥对 *
	 * 
	 * @return KeyPair *
	 * @throws EncryptException
	 */
	public static KeyPair generateKeyPair() throws Exception {
		try {
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA",
					new org.bouncycastle.jce.provider.BouncyCastleProvider());
			final int KEY_SIZE = 1024;// 这个值关系到块加密的大小，可以更改，但是不要太大，否则效率会低
			keyPairGen.initialize(KEY_SIZE, new SecureRandom());
			KeyPair keyPair = keyPairGen.generateKeyPair();
			
			saveKeyPair(keyPair);
			return keyPair;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public static KeyPair getKeyPair() throws Exception {
		FileInputStream fis = new FileInputStream(RSAKeyStore.trim());
		ObjectInputStream oos = new ObjectInputStream(fis);
		KeyPair kp = (KeyPair) oos.readObject();
		oos.close();
		fis.close();
		return kp;
	}

	public static void saveKeyPair(KeyPair kp) throws Exception {

		FileOutputStream fos = new FileOutputStream(RSAKeyStore);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		// 生成密钥
		oos.writeObject(kp);
		oos.close();
		fos.close();
	}

	/**
	 * * 生成公钥 *
	 * 
	 * @param modulus *
	 * @param publicExponent *
	 * @return RSAPublicKey *
	 * @throws Exception
	 */
	public static RSAPublicKey generateRSAPublicKey(byte[] modulus,
			byte[] publicExponent) throws Exception {
		KeyFactory keyFac = null;
		try {
			keyFac = KeyFactory.getInstance("RSA",
					new org.bouncycastle.jce.provider.BouncyCastleProvider());
		} catch (NoSuchAlgorithmException ex) {
			throw new Exception(ex.getMessage());
		}

		RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(new BigInteger(
				modulus), new BigInteger(publicExponent));
		try {
			return (RSAPublicKey) keyFac.generatePublic(pubKeySpec);
		} catch (InvalidKeySpecException ex) {
			throw new Exception(ex.getMessage());
		}
	}

	/**
	 * * 生成私钥 *
	 * 
	 * @param modulus *
	 * @param privateExponent *
	 * @return RSAPrivateKey *
	 * @throws Exception
	 */
	public static RSAPrivateKey generateRSAPrivateKey(byte[] modulus,
			byte[] privateExponent) throws Exception {
		KeyFactory keyFac = null;
		try {
			keyFac = KeyFactory.getInstance("RSA",
					new org.bouncycastle.jce.provider.BouncyCastleProvider());
		} catch (NoSuchAlgorithmException ex) {
			throw new Exception(ex.getMessage());
		}

		RSAPrivateKeySpec priKeySpec = new RSAPrivateKeySpec(new BigInteger(
				modulus), new BigInteger(privateExponent));
		try {
			return (RSAPrivateKey) keyFac.generatePrivate(priKeySpec);
		} catch (InvalidKeySpecException ex) {
			throw new Exception(ex.getMessage());
		}
	}

	/**
	 * * 加密 *
	 * 
	 * @param key
	 *            加密的密钥 *
	 * @param data
	 *            待加密的明文数据 *
	 * @return 加密后的数据 *
	 * @throws Exception
	 */
	public static byte[] encrypt(PublicKey pk, byte[] data) throws Exception {
		try {
			Cipher cipher = Cipher.getInstance("RSA",
					new org.bouncycastle.jce.provider.BouncyCastleProvider());
			cipher.init(Cipher.ENCRYPT_MODE, pk);
			int blockSize = cipher.getBlockSize();// 获得加密块大小，如：加密前数据为128个byte，而key_size=1024
			// 加密块大小为127
			// byte,加密后为128个byte;因此共有2个加密块，第一个127
			// byte第二个为1个byte
			int outputSize = cipher.getOutputSize(data.length);// 获得加密块加密后块大小
			int leavedSize = data.length % blockSize;
			int blocksSize = leavedSize != 0 ? data.length / blockSize + 1
					: data.length / blockSize;
			byte[] raw = new byte[outputSize * blocksSize];
			int i = 0;
			while (data.length - i * blockSize > 0) {
				if (data.length - i * blockSize > blockSize)
					cipher.doFinal(data, i * blockSize, blockSize, raw, i
							* outputSize);
				else
					cipher.doFinal(data, i * blockSize, data.length - i
							* blockSize, raw, i * outputSize);
				// 这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把byte[]放到
				// ByteArrayOutputStream中，而最后doFinal的时候才将所有的byte[]进行加密，可是到了此时加密块大小很可能已经超出了
				// OutputSize所以只好用dofinal方法。

				i++;
			}
			return raw;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * * 解密 *
	 * 
	 * @param key
	 *            解密的密钥 *
	 * @param raw
	 *            已经加密的数据 *
	 * @return 解密后的明文 *
	 * @throws Exception
	 */
	public static byte[] decrypt(PrivateKey pk, byte[] raw) throws Exception {
		try {
			Cipher cipher = Cipher.getInstance("RSA",
					new org.bouncycastle.jce.provider.BouncyCastleProvider());
			cipher.init(cipher.DECRYPT_MODE, pk);
			int blockSize = cipher.getBlockSize();
			ByteArrayOutputStream bout = new ByteArrayOutputStream(64);
			int j = 0;

			while (raw.length - j * blockSize > 0) {
				bout.write(cipher.doFinal(raw, j * blockSize, blockSize));
				j++;
			}
			return bout.toByteArray();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	  /**
     * 使用默认的私钥解密给定的字符串。
     * <p />
     * 若{@code encrypttext} 为 {@code null}或空字符串则返回 {@code null}。
     * 私钥不匹配时，返回 {@code null}。
     * 
     * @param encrypttext 密文。
     * @return 原文字符串。
     */
    public static String decryptString(String encrypttext) throws Exception{
        try {
            byte[] en_data = Hex.decodeHex(encrypttext.toCharArray());

            byte[] data = decrypt((RSAPrivateKey)getKeyPair().getPrivate(), en_data);
            return new String(data);

        } catch (Exception e) {
        	throw new Exception(e);
        }
    }

    /**
     * 使用默认的私钥解密由JS加密（使用此类提供的公钥加密）的字符串。
     * 
     * @param encrypttext 密文。
     * @return {@code encrypttext} 的原文字符串。
     */
    public static String decryptStringByDefaultPrivateKey(String encrypttext) throws Exception{

        String text = decryptString(encrypttext);

        if(text == null) {
            return null;
        }
		StringBuffer sb = new StringBuffer();
		sb.append(new String(text));
		text = sb.reverse().toString();
		text = URLDecoder.decode(text,"UTF-8"); 
		return text;
    }
    
    /**
     * 使用默认的公钥加密字符串。
     * 
     * @param encrypttext 密文。
     * @return {@code encrypttext} 的原文字符串。
     */
    public static String encryptString(String text) throws Exception{

    	byte[] en_test = encrypt(getKeyPair().getPublic(), StringUtils.reverse(text).getBytes());
    	return Hex.encodeHexString(en_test);
    }
    
    /**
     * 使用默认的公钥加密字符串。
     * 
     * @param encrypttext 密文。
     * @return {@code encrypttext} 的原文字符串。
     */
    public static String encryptString(String text,  PublicKey publicKey) throws Exception{

    	byte[] en_test = encrypt(publicKey, StringUtils.reverse(text).getBytes());
    	return Hex.encodeHexString(en_test);
    }
    
	/**
	 * 获得公钥的modulus和exponent
	 * @throws Exception 
	 * */
	public static Map<String,String> getModulusAndExponentOfPublicKey() throws Exception{
		PublicKey publicKey =  getKeyPair().getPublic();
		String publicKeyStr =publicKey.toString();
		Map<String,String> map = new HashMap<String,String>();
		String[] arr = publicKeyStr.split(FileUtil.LINE_SEPARATOR);
		 String modulus = arr[1].substring(21);
		String exponent = arr[2].substring(21);  
	/*	JCERSAPublicKey  publicKey = ( JCERSAPublicKey)getKeyPair().getPublic();
	    String a = publicKey.getAlgorithm();
		String b = publicKey.getFormat();
		String modulus = publicKey.getModulus().toString();
		String exponent =publicKey.getPublicExponent().toString();*/
		map.put("modulus", modulus);
		map.put("exponent", exponent);
		
		return map;
	}
	
	/**
	 * 得到公钥
	 * @param modulus
	 * @param exponent
	 * @return
	 * @throws Exception 
	 */
    public static RSAPublicKey getPublicKey(String modulus, String exponent) throws Exception {
        try {
            BigInteger b1 = new BigInteger(modulus,16);
            BigInteger b2 = new BigInteger(exponent,16);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    	//return generateRSAPublicKey(modulus.getBytes(),exponent.getBytes()); 
    }
    
	/**
	 * 用modulus 和exponent 得到私钥
	 * @param modulus
	 * @param exponent
	 * @return
	 * @throws Exception 
	 */
    public static String encryptString(String str,String modulus, String exponent) throws Exception {
        try {
        	PublicKey publicKey =    getPublicKey(modulus,exponent);
            String encrypStr =RSAUtil.encryptString(str,publicKey);
            return encrypStr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } 
    }

    
	/**
	 * * *
	 * @param args *
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
 	  
		//RSAUtil.generateKeyPair();f5855801d01d94d875b86cee431799bb
//		882c066b329afbe91f995c510c00b6051c872e4021106563f375cbe33ccb25d5b7658c269c52e0a59a321ff5ffb56fa02c8e12b36fa1d08a657006b36555c98db46b9a1d2c5ba7ad07fcafc8ee3771947ccb42ad44e99447cdd42263e131ab5f7bc4ccab323d5db21310aa053dca300b06f5ec4efd372403e39b90f8eac86a54
//		80138f8c6e3ee315cb8c3aed4fc8ce2e6d2c4c63049377766169559a4366d7ce1d203a908c87acacc3ec44d89dfc6a094ba6721bbb1a4607e68da71dd1b89047a35ebd90d91df1f932608e8099946ea2a7935b1b453cf1d67344ec323c5740dd40117895fb17412c1de27f447118f4cc38bba4a8b25d6c998a299f5fa243b1da
		System.out.println(encryptString("123456"));
		
		System.out.println(Md5Util.encode("882c066b329afbe91f995c510c00b6051c872e4021106563f375cbe33ccb25d5b7658c269c52e0a59a321ff5ffb56fa02c8e12b36fa1d08a657006b36555c98db46b9a1d2c5ba7ad07fcafc8ee3771947ccb42ad44e99447cdd42263e131ab5f7bc4ccab323d5db21310aa053dca300b06f5ec4efd372403e39b90f8eac86a54"));
	}

}
