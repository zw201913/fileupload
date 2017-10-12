package com.github.zw201913.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import com.github.zw201913.common.UtilException;
import com.github.zw201913.enumeration.ExceptionType;

public final class EncryDigestUtil {

	private static String encodingCharset = "UTF-8";

	private EncryDigestUtil() {

	}

	/**
	 * 生成签名消息
	 * 
	 * @param aValue
	 *            要签名的字符串
	 * @param aKey
	 *            签名密钥
	 * @return
	 */
	public static String hmacSign(String aValue, String aKey) {
		byte k_ipad[] = new byte[64];
		byte k_opad[] = new byte[64];
		byte keyb[];
		byte value[];
		try {
			keyb = aKey.getBytes(encodingCharset);
			value = aValue.getBytes(encodingCharset);
		} catch (UnsupportedEncodingException e) {
			keyb = aKey.getBytes();
			value = aValue.getBytes();
		}

		Arrays.fill(k_ipad, keyb.length, 64, (byte) 54);
		Arrays.fill(k_opad, keyb.length, 64, (byte) 92);
		for (int i = 0; i < keyb.length; i++) {
			k_ipad[i] = (byte) (keyb[i] ^ 0x36);
			k_opad[i] = (byte) (keyb[i] ^ 0x5c);
		}

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {

			return null;
		}
		md.update(k_ipad);
		md.update(value);
		byte dg[] = md.digest();
		md.reset();
		md.update(k_opad);
		md.update(dg, 0, 16);
		dg = md.digest();
		return toHex(dg);
	}

	public static String toHex(byte input[]) {
		if (input == null)
			return null;
		StringBuilder output = new StringBuilder(input.length * 2);
		for (int i = 0; i < input.length; i++) {
			int current = input[i] & 0xff;
			if (current < 16)
				output.append("0");
			output.append(Integer.toString(current, 16));
		}

		return output.toString();
	}

	/**
	 * 
	 * @param args
	 * @param key
	 * @return
	 */
	public static String getHmac(String[] args, String key) {
		if (args == null || args.length == 0) {
			return (null);
		}
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < args.length; i++) {
			str.append(args[i]);
		}
		return hmacSign(str.toString(), key);
	}

	/**
	 * SHA加密
	 * 
	 * @param aValue
	 * @return
	 */
	public static String digest(String aValue) {
		aValue = aValue.trim();
		byte value[];
		try {
			value = aValue.getBytes(encodingCharset);
		} catch (UnsupportedEncodingException e) {
			value = aValue.getBytes();
		}
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		return toHex(md.digest(value));
	}

	public static String md5File(InputStream stream) throws UtilException {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			DigestInputStream dis = new DigestInputStream(stream, messageDigest);
			byte[] buf = new byte[1024];
			while ((dis.read(buf)) != -1)
				;
			dis.close();
			byte[] digest2 = messageDigest.digest();
			// 当流读取完毕，即将文件读完了， 这时的摘要 才与 写入时的 一样
			return toHex(digest2);
		} catch (Exception e) {
			throw new UtilException(ExceptionType.MD5_EXCEPTION);
		}
	}

	public static String md5File(String filePath) throws UtilException {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			DigestInputStream dis = new DigestInputStream(new FileInputStream(filePath), messageDigest);
			byte[] buf = new byte[1024];
			while ((dis.read(buf)) != -1);
			dis.close();
			byte[] digest2 = messageDigest.digest();
			// 当流读取完毕，即将文件读完了， 这时的摘要 才与 写入时的 一样
			return toHex(digest2);
		} catch (Exception e) {
			throw new UtilException(ExceptionType.MD5_EXCEPTION);
		}
	}

	public static void main(String[] args) throws Exception {
		System.out.println(md5File("/Users/zouwei/Desktop/test.txt"));
	}
}
