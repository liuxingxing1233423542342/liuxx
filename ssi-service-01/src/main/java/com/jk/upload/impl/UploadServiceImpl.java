package com.jk.upload.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jk.dao.upload.UploadDao;
import com.jk.model.upload.Upload;
import com.jk.upload.UploadService;
@Service
public class UploadServiceImpl implements UploadService {
	
	@Resource
	private UploadDao uploadDao;

	@Override
	public Upload selectFileByMD5(Upload upload) {
		
		return uploadDao.selectFileByMD5(upload);
	}

	@Override
	public void insertUploadFile(Upload upload) {
		
		uploadDao.insertUploadFile(upload);
	}
	
	
	

}
