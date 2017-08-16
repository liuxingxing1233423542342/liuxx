package com.jk.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jk.model.menu.MenuRequest;
import com.jk.model.menu.MenuResponse;
import com.jk.model.roles.RolesRequest;
import com.jk.model.roles.RolesResponse;
import com.jk.model.user.User;
import com.jk.model.user.UserRequest;
import com.jk.model.user.UserResponse;
import com.jk.service.UserService;


import common.base.MySessionContext;
import common.utils.DateUtil;
import common.utils.FTPUtil;
import common.utils.FileUtil;
import common.utils.JedisUtil;
import common.utils.JsonUtil;


@Controller

public class UserConreoller {
	
	@Resource
	private UserService userService;
	

	//根据ID回显一条信息
	@RequestMapping("selectUserById")
	@ResponseBody
	public UserRequest  selectUserById(UserRequest userRequest){
		
		if(userRequest!=null && !userRequest.getUserId().equals("")){
			userRequest = userService.selectUserById(userRequest.getUserId());			
		}
		return userRequest;
		
	}
	//修改
	@RequestMapping("updateUser")
	public String updateUser(UserRequest userRequest) {
	
		this.userService.updateUser(userRequest);
		return "redirect:selectUserList.action";
		
	}
	
	// * deleteByUser(逻辑 删除一条用户信息)   
	@RequestMapping("deleteByUser")
	public String deleteByUser(User user){
		this.userService.deleteByUser(user);
		return "redirect:selectUserList.action";
		
	}

	//导航首页
	@RequestMapping("UserTreePage")
	public String UserTreePage(){
		
		return "user/tree";	
	}

	//跳转查询页面
	@RequestMapping("selectUser")
	public String selectUser(){
		return "user/list";
		
	}
	//查询所有用户
	@RequestMapping("selectUserList")
	@ResponseBody
	public Map<String, Object> selectUserList(String pageNumber,UserRequest userRequest){
		//查询总条数
		int totalCount = userService.selectUserCount(userRequest);
		userRequest.setTotalCount(totalCount);
		if (null == pageNumber || "".equals(pageNumber.trim())) {
			pageNumber = "1";
		}
		userRequest.setPageIndex(Integer.valueOf(pageNumber));
		//计算分页信息
		userRequest.calculate();
		//查询列表
		List<UserResponse> userList = userService.selectUserList(userRequest);
		//封装结果
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", totalCount);
		map.put("rows", userList);
		return map;
	}
		
	//用户登录
	@RequestMapping("loginUser")
	@ResponseBody
	public Map<String, Object> loginUser(UserRequest userRequest, HttpServletRequest request ){
		HttpSession session = request.getSession();
		Object codeObj = session.getAttribute("imageCode");
		if(codeObj == null){
			codeObj = "";
		}
		String code = codeObj.toString();
		userRequest.setSysImgCode(code);
		Map<String, Object> map = userService.loginUser(userRequest);
		Object userInfo = map.get("userInfo");
		if(userInfo!=null){
			UserResponse userResponse = (UserResponse) userInfo;
			//用户信息放进session中
			session.setAttribute("userInfo", userInfo);
			//设置session过期时间(单位：秒)
			session.setMaxInactiveInterval(600);
			
			String userId = userResponse.getUserId() + "";
			MySessionContext.removeSession(userId,session);
			MySessionContext.addSession(userId, session);
		
			//查询出用户的权限树，放到redis中去
			MenuRequest menuRequest = new MenuRequest();	
			menuRequest.setUserId(userResponse.getUserId());
			List<MenuResponse> treeList = userService.selectUserMenuListJson(menuRequest);
			//放到redis中
			JedisUtil.setString(userId + "#tree_list", JsonUtil.toJsonString(treeList), -1);
		}
		return map;
		
	}
	
	
	//退出登录
	@RequestMapping("logout")
	public String logout(HttpServletRequest request){
		request.getSession().invalidate();
		return "redirect:index.jsp";	
	}
	
	//跳转用户 角色 页面
	@RequestMapping("toUserRolePage")
    public String toUserRolePage(ModelMap mm, UserRequest userRequest){
		mm.addAttribute("userId", userRequest.getUserId());
    	return "user/user_role";    	
    }
	//查询用户角色集合
	@RequestMapping("selectUserRoleListJson")
	@ResponseBody
    public List<RolesResponse> selectUserRoleListJson(RolesRequest rolesRequest){
		List<RolesResponse> roleList = userService.selectUserRoleListJson(rolesRequest);
		return roleList;
    	
    }
    
    //添加用户角色
	@RequestMapping("insertUserRoleList")
	@ResponseBody
	public String insertUserRoleList(@RequestBody List<RolesRequest> rolesRequestList){
		userService.insertUserRoleList(rolesRequestList);
		return "{}";
	}
	
	//活树
	@RequestMapping("selectTreeListJson")
	@ResponseBody
	public List<Map<String, Object>> selectTreeListJson(MenuRequest menuRequest){
		List<Map<String, Object>> treeList = userService.selectTreeListJson(menuRequest);
		return treeList;
			
	}

	//跳转添加页面
	@RequestMapping("queryAddPage")
	public String queryAddPage(){
		
		return "user/add";			
	}
	//添加用户
	@RequestMapping("insertUser")
	@ResponseBody
	 public String insertUser(User user){
		this.userService.insertUser(user);
	    return "{}";
	  }
	
	//测试图片上传
	@RequestMapping("uploadUserPhoto")
	public void uploadUserPhoto(MultipartFile userPhoto){
		try {
			InputStream inputStream = userPhoto.getInputStream();
			String md5 = FileUtil.getMD5(inputStream, "md5");
			
			//从数据库判断这个指纹存在
			System.out.println("文件指纹是：" + md5);
			//如果存在，直接把地址返回给用户
			
			//如果不存在，保存这个文件到FTP服务器，并且把保存的路径以及文件指纹存到数据库
			//文件名
			String originalFilename = userPhoto.getOriginalFilename();
			//截取文件后缀名
			String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
			//改造完后的文件新的名字
			String fileName = UUID.randomUUID().toString() + suffix;
			//存放在1702A下的 ，系统当前月日 为命名的文件下
			String path = "1702A/" + DateUtil.formatDateToString(new Date(), "yyyy/MM/dd");
			//如果返回true上传成功，false上失败
			boolean boo = FTPUtil.uploadFile(userPhoto.getInputStream(), fileName, path);
			if (boo) {
				System.out.println("文件上传成功，保存在：》》" + path + "/" + fileName);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println();
	}
		

}
