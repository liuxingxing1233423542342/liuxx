<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- treegrid -->
	<script type="text/javascript" src="js/jquery-treegrid/js/jquery.treegrid.min.js"></script>
	<script type="text/javascript" src="js/jquery-treegrid/js/jquery.treegrid.bootstrap3.js"></script>
	<script type="text/javascript" src="js/jquery-treegrid/extension/jquery.treegrid.extension.js"></script>
<!-- treegrid -->
<link rel="stylesheet" href="<%=request.getContextPath() %>/js/jquery-treegrid/css/jquery.treegrid.css">
</head>
<body>
	<!-- 工具栏 -->		
	<div id="toolbar">
		<button class="btn btn-primary" type="button" onclick="add_menu_dialog()"><i class="glyphicon glyphicon-plus"></i>添加</button>		
	</div>		
	<!-- 数据表格 -->
	<table id="menu_dg"></table>
	
<script type="text/javascript">
			
		$(function() {
			init_menu_list();
		}); 
		
	 	//初始化菜单列表
		function init_menu_list() {
			$('#menu_dg').treegridData({
                id: 'id',
                parentColumn: 'pid',
                type: "GET", //请求数据的ajax类型
                url: '<%=request.getContextPath() %>/menu/selectMenuListJson.action',   //请求数据的ajax的url
                ajaxParams: {}, //请求数据的ajax的data属性
                expandColumn: null,//在哪一列上面显示展开按钮
                striped: true,   //是否各行渐变色
                bordered: true,  //是否显示边框
                //expandAll: false,  //是否全部展开
                columns: [   
						{field:'id',title:'菜单ID',align:'center',width:100},    
						{field:'name',title:'菜单名称',align:'center',width:100},    
						{field:'url',title:'菜单链接',align:'center',width:100},
						{field:'pid',title:'菜单父ID',align:'center',width:100},
						{title:'操作',width:150,align:'center',formatter:function(value,row,index){	
							
  						return "<input class=\"btn btn-success\" type=\"button\" value='编辑' onclick=\"edit_user_role('"+value.id+"')\"/>"
  						+"&nbsp;&nbsp"
  						+"<input class=\"btn btn-danger\" type=\"button\" value='删除' onclick=\"delete_menu_role('"+value.id+"')\"/>";
					}},  
                   ]               
				});
			} 
		
		
		//添加菜单
		var res;
		function createAddContent(url){
			$.ajax({
				url:url,
				async:false,
				success:function(data){
					res = data;
				}
			});
			return res;
		}

		function add_menu_dialog(){
			bootbox.dialog({
				title:'添加',
				size:'large',
				message:createAddContent('<%=request.getContextPath()%>/menu/toAddMenuPage.action'),
				buttons:{
					success:{
						'label':"<i class='icon-ok'></i> 保存",
						'className':'btn-sm btn-success',
						'callback':function(){
							$.ajax({
				                type: "post",
				                dataType:'json',
				                url:'<%=request.getContextPath()%>/menu/insertMenu.action',
				                data:$('#add_menu_form').serialize(),// 你的添加页面form的Id
				                success: function(data) {
										//清空表单信息
			                			$('#menu_dg').html("");
				                		//重新调用查询方法
			                			init_menu_list();
										bootbox.alert('添加成功');
				                }
				            });
						}
					},
					cancel:{
						'label':"<i class='icon-info'></i> 取消",
						'className':'btn-sm btn-danger',
						'callback':function(){
							
						}
					}
				}
			});		
		} 
		//删除权限
		function delete_menu_role(id){
			 
			 $.ajax({
					url:"<%=request.getContextPath()%>/menu/deleteByMenu.action",
					type:'post',
					data:{menuId:id},
					dataType:'json',
					success:function(data){
						if(data){
								bootbox.alert('删除成功');
								$('#menu_dg').bootstrapTable('refresh');		
						}else{
						
						}
					}
				});
		
		
		}
		
		

		
		
		
</script>
</body>
</html>