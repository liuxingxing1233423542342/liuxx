package com.jk.upload;

import com.jk.model.upload.Upload;

public interface UploadService {

	Upload selectFileByMD5(Upload upload);

	void insertUploadFile(Upload upload);

}
