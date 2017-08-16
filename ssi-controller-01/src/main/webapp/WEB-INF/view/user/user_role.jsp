<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>


</head>
<body>
	
	<div id="treeDemo" class="ztree"></div>
	
	
	<script type="text/javascript">
	
		//定义一个ztree对象
		var zTreeObj;
		// zTree 的参数配置
		var setting = {
				check: {
					//chkStyle: "check",
					enable: true
				},
				data: {
					simpleData: {
						enable: true,
						idKey: "id",
						pIdKey: "pid",
						rootPId: 0
					}
				}
	   };
		// zTree 的数据属性
	   var zNodes = [
	                	{id:1,name:"用户管理",open:true},
	                	{id:2,name:"查询用户",pid:1},
	                	{id:3,name:"修改用户",pid:1,checked:true},
	                	{id:4,name:"部门管理",open:true},
	                	{id:5,name:"部门查询",pid:4},
	                	{id:6,name:"部门维护",pid:4},
	                	{id:7,name:"部门查询",pid:6},
	                	{id:8,name:"部门维护",pid:6},
	                ];
	 
		//页面加载完成进行ztree初始化
	   $(function() {
		   //ajax请求从后台获取树
		   $.ajax({
			   url:"<%=request.getContextPath() %>/selectUserRoleListJson.action",
			   data:{userId:"${userId}"},
			   dataType:"json",
			   type:"post",
			   success:function(data) {
				   zNodes = data;
				   
				   zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
			   }
		   });
	   })
		
	 //获取被选中的树节点
	   function get_selection_tree_nodes(){
		   var role_json_array = [];
		   var nodes = zTreeObj.getCheckedNodes(true);
		   for (var i = 0; i < nodes.length; i++) {
			   var role_obj = {userId:"${userId}", rolesId:nodes[i].id};
			  
			   role_json_array.push(role_obj);
			  alert(nodes[i].name + ">>" + nodes[i].id);
		   }
		   return role_json_array;
	   }
	</script>
	
</body>
</html>