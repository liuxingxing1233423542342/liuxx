<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<center>
<h1>回收站</h1>
</center>
<body>
	<!-- 工具栏 -->		
	<div id="toolbar">
		<button class="btn btn-primary" type="button" onclick="returnToTheUserList()"><i class="glyphicon glyphicon-plus"></i>返回列表</button>
		
	</div>	
	<!--列表展示区  -->
	<div id="myTable"></div>

<script type="text/javascript">


/* ---------列表展示开始 -----------  */
//初始化数据表格
$("#myTable").bootstrapTable({
	url:"<%=request.getContextPath()%>/selectUserHuiList.action",
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
    columns:[
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
		      {field:'userId',title:'操作',width:100,align:'center',formatter:function(value,row,index){
		    	  var userId=row.userId;
			    	return "<input class=\"btn btn-primary\" type=\"button\" value='还原' onclick=\"restoreUser('"+userId+"')\"/>";
			    }},  
			]
});
/* ---------列表展示结束 -----------  */

		function returnToTheUserList(){
			location="<%=request.getContextPath()%>/navigation.action";
			
		}
		
		//还原
		function restoreUser(userId){
			$.ajax({
				url:"<%=request.getContextPath()%>/restoreByUser.action",
				type:'post',
				data:{userId:userId},
				dataType:'json',
				success:function(data){
					if(data){
						bootbox.alert('还原成功');
						$('#myTable').bootstrapTable('refresh');
						
					}else{
					
					}
				}
			})
		}

		
</script>
</body>
</html>