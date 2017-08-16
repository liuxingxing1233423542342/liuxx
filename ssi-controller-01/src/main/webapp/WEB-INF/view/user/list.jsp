<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="js/ajaxSetUp.js"></script>
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
	<div id="toolbar">
		<button class="btn btn-primary" type="button" onclick="openAdd()"><i class="glyphicon glyphicon-plus"></i>添加</button>
		<button class="btn btn-danger" type="button" onclick="deleteUser()"><i class="glyphicon glyphicon-trash"></i>删除</button>
		<button class="btn btn-info" type="button" onclick="editUser()"><i class="glyphicon glyphicon-pencil"></i>修改</button>
	</div>	
	<!--列表展示区  -->
	<table id="myUserTable"></table>

<script type="text/javascript">
	
	/* ---------列表展示开始 -----------  */
	
	$(function(){
		user_list_init();
	})

		//初始化数据表格
		function user_list_init(){
		$("#myUserTable").bootstrapTable({
			url:"<%=request.getContextPath()%>/selectUserList.action",
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
			toolbar:"#toolbar",
			//设置后台分页
			sidePagination:"server", 
			//开启搜索框
			/* search:true, */
			//显示刷新按钮
			showRefresh:true,
			//拼接查询参数
			queryParams:function(params) {
				 params.userAccount = $("#query_user_account").val(); 
				console.log(params);
				return params;
			},
			queryParamsType:"",
		    columns: [
		              {checkbox:true},    
					  {field:'userId',title:'编号',align:'center',width:100},    
					  {field:'userName',title:'姓名',align:'center',width:100},    
					  {field:'userImg',title:'头像',align:'center',
						width:100,formatter:function(value,row,index){  //value 当前字段值  row当前行的数据  index当前行
						return "<img width='50' height='50' src='ftp://root:root@192.168.1.122:21/"+value+"'/>";
						}},    
					  {field:'userPwd',title:'密码',align:'center',width:100},				 
				      {field:'userId',title:'操作',width:100,align:'center',formatter:function(value,row,index){
				    	  var userId=row.userId;
					    	return "<input class=\"btn btn-success\" type=\"button\" value='角色操作' onclick=\"edit_user_role('"+userId+"')\"/>";
				      }},  
					]
			});
	}
		/* ---------列表展示结束 -----------  */
		
		//条件查询
		function search_user() {
			
			$("#myUserTable").bootstrapTable('destroy');//先要将table销毁，否则会保留上次加载的内容
			
		}
		
		
		//编辑用户角色
		function edit_user_role(userId){
			bootbox.dialog({
				title:" 用户管理>>用户赋角色",
				message: $('<div></div>').load('<%=request.getContextPath() %>/toUserRolePage.action?userId=' + userId),
				buttons: [{
					'label':"<i class='icon-ok'></i> 保存",
					'className':'btn-sm btn-success',
					'callback':function(){
		                	var role_json_array = get_selection_tree_nodes();
		                	//使用ajax保存结果
		                	$.ajax({
		                		url:"<%=request.getContextPath() %>/insertUserRoleList.action",
		                		data:JSON.stringify(role_json_array),
		                		dataType:"json",
		                		type:"post",
		                		success:function(data) {
		                			bootbox.alert('操作成功');
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
	
		
	 //逻辑删除
	function deleteUser(){
		var rows = $('#myUserTable').bootstrapTable('getSelections');
		if(rows == null || rows.length <= 0){
			bootbox.alert('请选择需要删除的数据');
		}else{
			bootbox.confirm('你确定要删除吗？',function(r){
				if(r){
					var ids = "";
					for (var i = 0; i < rows.length; i++) {
						if(ids == ""){
							ids = rows[i].userId;
						}else{
							ids += ","+rows[i].userId;
						}
					}
					 $.ajax({
							url:"<%=request.getContextPath()%>/deleteByUser.action",
							type:'post',
							data:{userId:ids},
							dataType:'json',
							success:function(data){
								bootbox.alert('删除成功');
								$('#myUserTable').bootstrapTable('refresh');		
							}
						});
					}
			});
		}
	}

	//添加用户
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
			size:'large',											//1跳转添加用户页面路径
			message:createAddContent('<%=request.getContextPath()%>/queryAddPage.action'),
			buttons:{
				success:{
					'label':"<i class='icon-ok'></i> 保存",
					'className':'btn-sm btn-success',
					'callback':function(){
						$.ajax({
			                type: "post",
			                dataType:'json',					//2保存用户路径
			                url:'<%=request.getContextPath()%>/insertUser.action',
			                data:$('#myAddForm').serialize(),// 你的添加页面form的Id
			                success:function(data){
								bootbox.alert('添加成功');
								user_list_init();		
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
		
		//图片上传   初始化上传插件
		$('#goodsImg').fileinput({
			uploadUrl:'<%=request.getContextPath()%>/upload/uploadFile.action',//上传地址
			language: 'zh', //设置语言 默认英文
			allowedFileExtensions:['jpg', 'gif', 'png'],//接受文件的后缀名
			showUpload: true, //是否显示上传按钮
			browseClass: "btn btn-primary", //按钮样式 
			enctype: 'multipart/form-data',
		    showRemove : false, //显示移除按钮,跟随文本框的那个
		    maxFileCount: 1, //表示允许同时上传的最大文件个数
		    validateInitialCount:true,
		    previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
			msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
		    layoutTemplates:{
		    	actionDelete:'',
		    	actionUpload:'',
		    	actionPreview:''
		    }

		}).on('fileuploaded', function(event, data, previewId, index) {
			var imgval = "http://<%= request.getServerName()%>:<%=request.getServerPort()%><%=request.getContextPath()%>/img/"+data.response.img;
			$('#testimg').val(imgval);
		});
		
		
		
		
		
	}
	
	//修改
	function editUser(){		
		var rows = $('#myUserTable').bootstrapTable('getSelections');		
		if(rows == null || rows.length <= 0){
			bootbox.alert('请选择需要修改的数据');
		}else if(rows.length > 1){
			bootbox.alert('修改时只能选择一条数据');
		}else{
			bootbox.dialog({
				title:'修改',
				size:'large',
				message:createAddContent('<%=request.getContextPath()%>/queryAddPage.action'),
				buttons:{
					success:{
						'label':"<i class='icon-ok'></i> 保存",
						'className':'btn-sm btn-success',
						'callback':function(){
							$.ajax({
				                type: "post",
				                dataType:'json',
				                url:'<%=request.getContextPath()%>/updateUser.action',
				                data:$('#myAddForm').serialize(), // 你的formid
				                success: function(data) {
				                		//刷新页面
				                		bootbox.alert('修改成功');
				                		user_list_init();
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
			//获取回填数据
			$.ajax({
				url:'<%=request.getContextPath()%>/selectUserById.action',
				type:'post',
				data:{userId:rows[0].userId},
				dataType:'json',
				success:function(data){
					$('#usersId').val(data.userId);
					$('#usersName').val(data.userName);
					$('#usersPwd').val(data.userPwd);					
				}
			});
		}
	}
	
	
	
	
	
	</script>
</body>
</html>