package com.jk.controller.upload;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.jk.model.upload.Upload;
import com.jk.upload.UploadService;

import common.utils.DateUtil;
import common.utils.FTPUtil;
import common.utils.FileUtil;

@Controller
@RequestMapping("upload")
public class UploadController {
	
	@Resource
	private UploadService uploadService;
	

	@RequestMapping("uploadFile")
	public void uploadFile(MultipartFile img , HttpServletResponse response){
		Upload upload = new Upload();
		//等等
		String fileAddr = "";
		
		try {
			InputStream inputStream = img.getInputStream();
			//调用fileUtil工具类
			String md5 = FileUtil.getMD5(inputStream, "md5");
			upload.setMdFive(md5);
			//从数据库判断这个指纹存在
			System.out.println("文件指纹是：" + md5);
			
			Upload returnUpload	= uploadService.selectFileByMD5(upload);
			//如果存在，直接把地址返回给用户
			if(returnUpload!=null){
				fileAddr = returnUpload.getFileAddr();
			}else{
			//如果不存在，保存这个文件到FTP服务器，并且把保存的路径以及文件指纹存到数据库
			//并把地址返回给用户
			//文件名
				String originalFilename = img.getOriginalFilename();
				//截取文件后缀名
				String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
				//改造完后的文件新的名字
				String fileName = UUID.randomUUID().toString() + suffix;
				//存放在1702A下的 ，系统当前月日 为命名的文件下
				String path = "1702A/" + DateUtil.formatDateToString(new Date(), "yyyy/MM/dd");
				//如果返回true上传成功，false上失败
				boolean boo = FTPUtil.uploadFile(img.getInputStream(), fileName, path);
				if (boo) {
					fileAddr = path + "/" + fileName;
					upload.setFileAddr(fileAddr);
					uploadService.insertUploadFile(upload);
					System.out.println("文件上传成功，保存在：》》" + path + "/" + fileName);
				}	
			}		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	
	
}
