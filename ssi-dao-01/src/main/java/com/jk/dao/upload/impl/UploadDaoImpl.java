package com.jk.dao.upload.impl;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.jk.dao.upload.UploadDao;
import com.jk.model.upload.Upload;
@Repository
public class UploadDaoImpl extends SqlMapClientDaoSupport implements UploadDao  {

	
	//查询MD5指纹
	@Override
	public Upload selectFileByMD5(Upload upload) {
		
		return (Upload) getSqlMapClientTemplate().queryForList("upload.selectFileByMD5",upload);
	}
	
	//添加图片
	@Override
	public void insertUploadFile(Upload upload) {
		
		getSqlMapClientTemplate().insert("upload.insertUploadFile",upload);
	}

}
