<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<input type="button" value="退出登陆"onclick="logout()">

			<!-- 导航条 -->
			<nav class="navbar navbar-inverse navbar-static-top">
				<a class="navbar-brand" href="#">金科教育</a>
				
			</nav>
			
			<!-- 100%布局容器 -->
			<div class="container-fluid">
				<div class="row">
					<!-- 左边功能区 -->
					<div class="col-md-2">
						<div class="list-group">
							<div id="tree"></div>
						</div>
					</div>
					
					<!-- 右边功能区 -->
					<div class="col-md-10">
					
						<div id="content-div">
							<!-- 选项卡 -->
							<div id="tabs">
								<!--  -->
								<ul class="nav nav-tabs" role="tablist">
									<li role="presentation" class="active">
									<a href="#home" aria-controls="home" role="tab">主页</a>
								</ul>
								
								<div class="tab-content">
								
								</div>								
							</div>					
						</div>
					</div>
					
					
						
				</div>
				</div>
<script type="text/javascript">
 <%-- var tree_data = [
              	{text:"用户管理",
              		href:"<%=request.getContextPath() %>/selectUser.action",
              		state: {
              			selected: true
              			},},
              	{text:"角色管理",
              		href:"<%=request.getContextPath() %>/role/selectRole.action",
              		},
              	{text:"品牌管理",
              		href:"sdsds",},
              	{text:"员工管理",
              		href:"sdsds",},
              	{text:"部门管理",
              		href:"sdsds",},
              ]; --%>

	
 
 
 
 
 	
	 
		//初始化树
		$('#tree').treeview({
			data:getTreeData(),
			onNodeSelected:function(event, node) {
				 if (null != node.href && "" != node.href) { 
					//发送ajax请求
		    		$.ajax({
		    			url:"<%=request.getContextPath() %>" + node.href,
		    			success:function(data) {
		    				$.addtabs.add({id:node.text,title:node.text,content:data});
		    			}
		    		});
				}
			}
		});
	
	
		
		//获取菜单数据
		function getTreeData(){
			var tree_data =[];
			//发送ajax请求
			$.ajax({
    			async:false,//请求为同步
    			url:"<%=request.getContextPath() %>/selectTreeListJson.action",
    			data:{userId:"${userInfo.userId}"},
    			dataType:"json",
    			success:function(data) {
    				tree_data = data;
    			}
    		});
			return tree_data
		}
		
		function logout(){
			location="<%=request.getContextPath() %>/logout.action";
		}


</script>		
</body>
</html>