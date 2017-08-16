package com.jk.model.user;

public class UserResponse extends User {
	
	

	
	//密码错误5分钟后解锁
	private long loginFailDate;
	

	
	public long getLoginFailDate() {
		return loginFailDate;
	}

	public void setLoginFailDate(long loginFailDate) {
		this.loginFailDate = loginFailDate;
	}

	
	
	

}
