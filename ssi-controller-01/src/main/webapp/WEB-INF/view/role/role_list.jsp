<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- 搜索面板 -->
    <div class="tab-content" style="padding: 4px">
	    <form id="search_user_form">
	    	<label>用户账号：</label>
			<input id="query_user_account" class="form-control-sm" placeholder="请输入用户账号">
			<div align="center">
				<!-- Single button -->
				<div class="btn-group">
				  <button type="button" class="btn btn-success glyphicon glyphicon-search" onclick="search_user()">搜索</button>
				</div>
				<div class="btn-group">
				  <button type="button" class="btn btn-danger glyphicon glyphicon-repeat" onclick="reset_search_user_form()">重置</button>
				</div>
			</div>
		</form>
    </div>


	<!-- 工具栏 -->		
	<div id="rolebar">
		<button class="btn btn-primary" type="button" onclick="openAdd()"><i class="glyphicon glyphicon-plus"></i>添加</button>
		<button class="btn btn-danger" type="button" onclick="deleteRoles()"><i class="glyphicon glyphicon-trash"></i>删除</button>
		<button class="btn btn-info" type="button" onclick="editHero()"><i class="glyphicon glyphicon-pencil"></i>修改</button>
	</div>	
	<!--列表展示区  -->
	<div id="myRoleTable"></div>
	
	
<script type="text/javascript">

	//初始化数据表格
	$("#myRoleTable").bootstrapTable({
		url:"<%=request.getContextPath()%>/role/selectRoleList.action",
		dataType:"json",
		//请求方式
		method:"post",
		//必须的！不然会造成中文乱码
		contentType: "application/x-www-form-urlencoded",
		//斑马线
		striped:true,
		//设置分页
		pagination:true,
		paginationLoop:true,
		pageNumber:1,
		pageSize:5,
		pageList:[3,5,8,10],
		//工具条
		toolbar:"#rolebar",
		//设置后台分页
		sidePagination:"server", 
		//开启搜索框
		/* search:true, */
		//显示刷新按钮
		showRefresh:true,
		//拼接查询参数
		queryParams:function(params) {
			 //params.userAccount = $("#query_user_account").val(); 
			console.log(params);
			return params;
		},
		queryParamsType:"",
	    columns: [
	              {checkbox:true},    
				  {field:'rolesId',title:'编号',align:'center',width:100},    
				  {field:'rolesName',title:'角色',align:'center',width:100},    
				  {field:'rolesDesc',title:'描述',align:'center',width:100},
				 
			      {field:'rolesId',title:'操作',width:100,align:'center',formatter:function(value,row,index){
			    	  var rolesId=row.rolesId;
			    	  
				    	return "<input class=\"btn btn-success\" type=\"button\" value='权限操作' onclick=\"edit_role_menu('"+rolesId+"')\"/>";
			      }},  
				]
	});
	/* ---------列表展示结束 -----------  */

///////////编辑用户角色/////////////////////////////////////////////
		function edit_role_menu(rolesId){
			bootbox.dialog({
				title:"角色管理>>角色赋权限",
				message: $('<div></div>').load('<%=request.getContextPath() %>/role/toRoleMenuPage.action?rolesId='+rolesId),
				buttons: [{
					'label':"<i class='icon-ok'></i> 保存",
					'className':'btn-sm btn-success',
					'callback':function(){
		                	var role_json_array = get_selection_tree_nodes();
		                	//使用ajax保存结果
		                	$.ajax({
		                		url:"<%=request.getContextPath() %>/role/insertRoleMenuList.action",
		                		data:JSON.stringify(role_json_array),
		                		dataType:"json",
		                		type:"post",
		                		success:function(data) {
		                			bootbox.alert('操作成功');
		                			//关闭对话框
		                			dialogItself.close();
		                		},
		                		contentType:"application/json"
		                	});
		                }
		            }, {
	                label: '取消',
	                cssClass:"btn btn-danger",
	                action: function(dialogItself){
	                	dialogItself.close();
	                }
	            }]
			});
		}
	
	
		//添加角色
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
		function openAdd(){
			bootbox.dialog({
				title:'添加',
				size:'large',											//1跳转添加角色页面路径
				message:createAddContent('<%=request.getContextPath()%>/role/queryAddRolePage.action'),
				buttons:{
					success:{
						'label':"<i class='icon-ok'></i> 保存",
						'className':'btn-sm btn-success',
						'callback':function(){
							$.ajax({
				                type: "post",
				                dataType:'json',					// 2保存角色路径
				                url:'<%=request.getContextPath()%>/role/addRoleInfo.action',
				                data:$('#myAddRoleForm').serialize(),// 你的添加页面form的Id
				                success: function(data) {
				                	
				                	if(data){
				                		//刷新表格
			                			$('#myRoleTable').bootstrapTable("refresh");
										bootbox.alert('添加成功');
												
								}else{
								
								}
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
		
	//删除角色信息
	function deleteRoles(){
		var rows = $('#myRoleTable').bootstrapTable('getSelections');
		if(rows == null || rows.length <= 0){
			bootbox.alert('请选择需要删除的数据');
		}else{
			bootbox.confirm('你确定要删除吗？',function(r){
				if(r){
					var ids = "";
					for (var i = 0; i < rows.length; i++) {
						if(ids == ""){
							ids = rows[i].rolesId;
						}else{
							ids += ","+rows[i].rolesId;
						}
					}					
					 $.ajax({
							url:"<%=request.getContextPath()%>/role/deleteByRolse.action",
							type:'post',
							data:{rolesId:ids},
							dataType:'json',
							success:function(data){
								if(data){
										bootbox.alert('删除成功');
										$('#myRoleTable').bootstrapTable('refresh');		
								}else{
								
								}
							}
						});
				}
			});
		}
	}

	
	
		
////////////////////////////////////////////////////////////////////	






</script>
</body>
</html>