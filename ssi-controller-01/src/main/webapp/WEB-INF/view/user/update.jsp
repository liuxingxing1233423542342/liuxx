<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>


</head>
<body>
<form action="updateUser.action" method="post">
	<table border="1">
		<tr>
			<td>姓名</td>
			<td>
				<input type="hidden" value="${user.userId}" name="userId">
				<input type="text" name="userName" value="${user.userName}">
			</td>
		</tr>
		<tr>
			<td>性别</td>
			<td>
				<input type="radio" name="userSex" <c:if test="${user.userSex== 1 }">checked</c:if> value="1">男
				<input type="radio" name="userSex" <c:if test="${user.userSex== 2 }">checked</c:if> value="2">女
			</td>
		</tr>
		<tr>
			<td>年龄</td>
			<td>
				<input type="text" name="userAge" value="${user.userAge}">
			</td>
		</tr>
		<tr>
   			<td>头像：</td>
   			<td>
   				<input type="file" name="file">
   				<input type="hidden" value="${user.userImg}" name="userImg">
   				<img alt="" src="${user.userImg}" height="50px" width="50">
			</td>
    	</tr>
		<tr>
			<td colspan="2">			
				<input type="submit" value="修改" >
			</td>
		</tr>
	</table>
</form>
</body>
</html>