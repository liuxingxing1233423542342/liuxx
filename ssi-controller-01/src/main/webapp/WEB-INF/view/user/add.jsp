<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp" %> 

<form id="myAddForm" class="form-inline">
	 
		<div class="form-group">
	    <label for="bookName">用户名</label>
	    <input id="usersId"  type="text" name="userId">
	    <input type="text" class="form-control" id="usersName" name="userName" placeholder="请输入用户名">
		</div>
			<br>
			<br>
		<div class="form-group">
	    <label for="exampleInputFile">密&nbsp;&nbsp;&nbsp;&nbsp;码</label>
	    <input type="password" class="form-control" id="usersPwd" name="userPwd" placeholder="请输入密码" />				
	  	</div>
			<br>
		<div class="form-group">
			<label class="col-sm-2 control-label">头&nbsp;&nbsp;&nbsp;&nbsp;像</label>	    
	    	<input type="hidden" id="testimg" name="userImg" />
		    <input type="file" id="goodsImg" name="img" class="projectfile form-control">		
		</div>		  
</form>




