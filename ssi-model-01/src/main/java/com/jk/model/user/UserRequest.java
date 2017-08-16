package com.jk.model.user;

public class UserRequest extends User {

	
	//验证码
	private String userImgCode;
	
	//系统验证码
	private String sysImgCode;

	
	
	public String getUserImgCode() {
		return userImgCode;
	}

	public void setUserImgCode(String userImgCode) {
		this.userImgCode = userImgCode;
	}

	public String getSysImgCode() {
		return sysImgCode;
	}

	public void setSysImgCode(String sysImgCode) {
		this.sysImgCode = sysImgCode;
	}

	
	
	
	
}
