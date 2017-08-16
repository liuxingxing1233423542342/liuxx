<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/common.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- 工具栏 -->		
	<div id="toolbar">
		<button class="btn btn-primary" type="button" onclick="openAdd()"><i class="glyphicon glyphicon-plus"></i>添加</button>
		<button class="btn btn-danger" type="button" onclick="deleteBook()"><i class="glyphicon glyphicon-trash"></i>删除</button>
		<button class="btn btn-info" type="button" onclick="editHero()"><i class="glyphicon glyphicon-pencil"></i>修改</button>
	</div>	
	<!--列表展示区  -->
	<div id="myTable"></div>



  <center>
<h1>用户列表</h1>
<input type="button" value="添加" onclick="addUser()">
<input type="button" value="回收站" onclick="huiShouZhan()">

<input type="button" value="退出登陆" 
onclick="location.href='<%=request.getContextPath() %>/logout.action'">
</center>
<form action="">
		
	<table align="center" border="1">
			
			<tr>
				<td>编号</td>
				<td>姓名</td>
				<td>密码</td>
				<td>年龄</td>
				<td>性别</td>
				<td>头像</td>
				<td>操作</td>
			</tr>
			<c:forEach items="${list}" var="user" >
			
			<tr>
			
				<td>${user.userId}</td>
				<td>${user.userName}</td>
				<td>${user.userPwd}</td>
				<td>${user.userAge}</td>
				<td>${user.userSex==1?"男":"女"}</td>
				<td>
					<img alt="暂无图片" src="${user.userImg}" height="50px" width="50px">
					
				</td>
				<td>
					<input type="button" value="删除" onclick="deleteUser(${user.userId})">
      				<input type="button" value="修改" onclick="updateUser(${user.userId})">
				</td>
			</tr>
			</c:forEach>
		</table>

</form> 
<script type="text/javascript">
<%--  //列表展示
$('#myTable').bootstrapTable({
	url:"<%=request.getContextPath()%>/selectUserList.action",
		dataType:"json",
	 pagination:true,//是否展示分页
	 pageList:[5, 10, 20, 50],//分页组件  
	 pageNumber:1,  
	 pageSize:5,//默认每页条数  
	 clickToSelect: true, //是否启用点击选中行
	 sidePagination:'server',//分页方式：client客户端分页，server服务端分页（* 
	 striped:true,//斑马线
	 queryParams:function(){
		 //var bookName = $("#bookName").val();
		
		  return {    
			  page: this.pageNumber,    
			  rows: this.pageSize, //默认每页条数  
			 'bookBean.bookName':bookName,
			  
			  	} 
			  },  
			  columns:[[                          
			    {checkbox:true},    
			    {field:'userId',title:'编号',align:'center',width:100},    
			    {field:'userName',title:'姓名',align:'center',width:100},    
			    {field:'userPwd',title:'密码',align:'center',width:100},
			    {field:'userImg',title:'头像',align:'center',width:150,formatter:function(data){     
			    	return "<img src="+data+" width='50' height='50'>"}},
			    	
		    	{field:'userAge',title:'年龄',width:100,align:'center'},  
			    
			    {field:'userSex',title:'性别',width:100,width:100,align:'center',formatter:function(value){     
		    		if(value=='1'){
		    			return "男"
		    		}
		    		if(value=='2'){
		    			return "女"
		    		}
		    		
	    		}},    
			    

			    ]] ,    

		});  --%>
 
	 //初始化数据表格
		$("#myTable").bootstrapTable({
			url:"<%=request.getContextPath()%>/selectUserList.action",
			dataType:"json",
			//请求方式
			method:"post",
			//必须的 不然会造成中文乱码
			contentType: "application/x-www-form-urlencoded",
			//斑马线
			striped:true,
			//设置分页
			/* pagination:true,
			paginationLoop:true,
			pageNumber:1,
			pageSize:5,
			pageList:[3,5,8,10], */
			//工具条
			/* toolbar:"#book_tb", */
			//设置后台分页
			/* sidePagination:"server", */
			//开启搜索框
			/* search:true, */
			//显示刷新按钮
			showRefresh:true,
		    columns: [{
		    	{checkbox:true},
		    	{
			        field: 'userId',
			        title: '主键'
			    }, {
			        field: 'userName',
			        title: '音乐名称'
			    }, {
			        field: 'userPwd',
			        title: '音乐语言'
			    },  {
			        field: 'userAge',
			        title: '音乐风格'
			    }, {
			        field: 'userSex',
			        title: '销售地区'
			    },
		}); 
	
		
		
		
		
		
		
		
		
	//逻辑删除
	function deleteUser(userId){
		 location="<%=request.getContextPath()%>/deleteByUser.action?userId="+userId;
	}
	
	//修改
	function updateUser(userId){
		location="<%=request.getContextPath()%>/selectUserById.action?userId="+userId;
	}
	
	//回收站
	function huiShouZhan(){
		 location="<%=request.getContextPath()%>/selectUserHuiList.action";
	}
	
	//添加
	function addUser(){
		 location="<%=request.getContextPath()%>/addUser.action";
	}
	
	


</script>
		
</body>
</html>