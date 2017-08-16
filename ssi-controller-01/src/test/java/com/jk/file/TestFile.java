package com.jk.file;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

import org.junit.Test;

public class TestFile {
	
	@Test
	public void getMD5(){
		String result = null;		
		try {
			InputStream is = new FileInputStream("D:/work-box/ftp/File Server/2017.jpg");
			String type = "md5";
			 MessageDigest md = MessageDigest.getInstance(type);
			 byte[] buffer = new byte[8192];
			 int length = -1;
			 while ((length = is.read(buffer)) != -1) {
			     md.update(buffer, 0, length);
			 }
			 byte[] digest = md.digest();
			 StringBuffer strBuffer = new StringBuffer();
			  for (int i = 0; i < digest.length; i++) {
			   strBuffer.append(Integer.toHexString(0xff & digest[i]));
			  }
			  result = strBuffer.toString();
			  System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
	}
	
	
}
