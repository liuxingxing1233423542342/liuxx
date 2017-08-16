package com.jk.dao.upload;

import com.jk.model.upload.Upload;

public interface UploadDao {

	Upload selectFileByMD5(Upload upload);

	void insertUploadFile(Upload upload);

}
