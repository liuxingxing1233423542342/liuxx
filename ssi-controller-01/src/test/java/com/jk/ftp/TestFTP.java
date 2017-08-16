package com.jk.ftp;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

public class TestFTP {

	@Test
	public void connFTP(){
		//1.实例化客户端
		FTPClient ftp = new FTPClient();
		try {
			//2.连接ftp客户端
			ftp.connect("192.168.1.128",21);

			boolean login = ftp.login("root", "root");
			if(login){
				//4.切换到根路径(/)下
				ftp.changeWorkingDirectory("/");
				//4.切换到根目录的  文件夹下
				boolean bool = ftp.changeWorkingDirectory("img/headPortrait");
				//如果没有该文件夹，则创建一个
				if(!bool){
					ftp.makeDirectory("img/headPortrait");
				}
				
				InputStream f = new FileInputStream("D:/work-box/ftp/File Server/脱.gif");
				//如果上传媒体文件，需要设置二进制
				ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
				//5、向ftp上传文件
				boolean storeFile = ftp.storeFile(new String("脱了.gif".getBytes("GBK"), "ISO-8859-1"), f);				
				System.out.println(storeFile);
			}
			
		} catch (SocketException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	
	
}
