package com.exam.examcsm.config;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

	public static String md5(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			str = buf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	public static byte[] getMd5Bytes(String str){
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		md.update("abc".getBytes());
		return md.digest();
	}

	public static void main(String[] args) throws NoSuchAlgorithmException {
//		System.out.println(md5("11"));
//		System.out.println(md5("22"));

//		System.out.println(md5("abc"));
//
//
//		byte b[] = getMd5Bytes("abc");
//		for (int i = 0; i < b.length ; i ++){
//			System.out.print(b[i]+",");
//		}

		System.out.println(md5("222222"));

	}
}
