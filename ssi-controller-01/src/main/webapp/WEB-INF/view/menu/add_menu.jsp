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
	<form id="add_menu_form" class="form-inline">
		
		 <div class="form-group">
			    <label for="exampleInputPassword1">所属模块:</label>
			    <select  name="pid" class="form-control">
				      <option value="0">新增模块</option>
					  <c:forEach items="${menuList }" var="menu">
						<option value="${menu.id }">${menu.name }</option>
					</c:forEach>
				</select>
					<br>
					<br>
				<div class="form-group">
			    <label for="menuName">菜单名称:</label>
			    <input type="text" class="form-control" name="menuName" placeholder="菜单名称">
			    </div>
				    <br>
				    <br>
			    <div class="form-group">
			    <label for="url">菜单链接:</label>
			    <input type="text" class="form-control" name="url" placeholder="菜单链接">
			    </div>
			    <br>
			    <br>
			    <div class="form-group">
			    <label for="type">菜单类型:</label>
			      		<div class="radio">
			      		 <label>
						    <input type="radio" name="menuType"  value="0" checked="checked">常规功能
						 </label>
			      		 <label>
						    <input type="radio" name="menuType"  value="1">ajax请求
						 </label>
			      		 <label>
						    <input type="radio" name="menuType"  value="2">基本功能
						 </label>
			      		 <label>
						    <input type="radio" name="menuType"  value="3">按钮功能
						 </label>
						 </div>
		      	</div>

			    
			    
			  </div>
		
		
	</form>
</body>
</html>