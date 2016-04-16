package com.yabe.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {
	
	public static boolean isEmpty(String string){
		return string == null || string.length() == 0;
	}
	private static MessageDigest md;
	
	public static String encodePassword(String password) {
		try{
			md = MessageDigest.getInstance("MD5");
			byte[] passBytes = password.getBytes();
			md.reset();
			byte[] digested = md.digest(passBytes);
			StringBuffer sb = new StringBuffer();
			for(int i=0; i< digested.length;i++){
				sb.append(Integer.toHexString(0xff & digested[i]));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}finally{
			
		}
		return null;
	}
	
}
