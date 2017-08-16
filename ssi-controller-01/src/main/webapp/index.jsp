<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>基于Bootstrap的jQuery登录表单DEMO演示</title>

<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/signin.css" rel="stylesheet">


</head>
<body>
	<!-- 页面特效  必须在body内有效 -->
	<script src="js/canvas-nest.js" count="200" zindex="-2" opacity="0.5" color="47,135,193" type="text/javascript"></script>
	
	<div class="signin">
	<div class="signin-head"><img src="images/test/team011.jpg" width="100px" height="100px" alt="" class="img-circle"></div>
	
	<form class="form-signin" id="myForm" >
		
		<input type="text" class="form-control" name="userName" placeholder="用户名" required autofocus />
		
		<input type="password" class="form-control" name="userPwd" placeholder="密码" required />
		
		<input type="text" class="form-control" name="userImgCode" placeholder="验证码" required />
		
		<span onclick="change_imgcode()">
					<img id="imgcode_src_node" src="<%=request.getContextPath() %>/imgcode">
					<font color="red">看不清,点击换一张</font>
		</span>	
		<button class="btn btn-lg btn-warning btn-block" type="button" onclick="user_login()">登录</button>
		
		<label class="checkbox">
			<input type="checkbox" value="remember-me"> 记住我
		</label>
	</form>
	
</div>
<script type="text/javascript">
	//登录
	function user_login(){
		$.ajax({
			url:"<%=request.getContextPath()%>/loginUser.action",
			data:$("#myForm").serialize(),
			type:"post",
			dataType:"json",
			success:function(data){
				console.log(data);
				if (1 == data.flag) {
					//登录成功后
					location.href = "<%=request.getContextPath()%>/UserTreePage.action";
					return;
				}
				if (2 == data.flag) {
					alert("用户不存在");
					return;
				}
				if (3 == data.flag) {
					alert("密码错误" + data.loginfailnum);
					return;
				}
				if (4 == data.flag) {
					alert("验证码错误");
					return;
				}
				if (5 == data.flag) {
					alert("验证码为空");
					return;
				}
				if (6 == data.flag) {
					alert("账号锁定");
					return;
				}
			}
			
		})
	}
	//重新加载一下切换验证码
	$(function(){
		change_imgcode;
	})
	//切换验证码
	function change_imgcode() {
		$("#imgcode_src_node").attr("src", "<%=request.getContextPath() %>/imgcode?time=" + new Date().getTime());
	}
	
	


</script>
</body>
</html>