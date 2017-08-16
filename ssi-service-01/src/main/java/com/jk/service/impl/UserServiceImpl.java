package com.jk.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jk.dao.UserDao;
import com.jk.model.menu.MenuRequest;
import com.jk.model.menu.MenuResponse;
import com.jk.model.roles.RolesRequest;
import com.jk.model.roles.RolesResponse;
import com.jk.model.user.User;
import com.jk.model.user.UserRequest;
import com.jk.model.user.UserResponse;
import com.jk.service.UserService;

import common.constant.Constant;
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired	
	private UserDao userDao;

	

	public void insertUser(User user) {
		
		this.userDao.insertUser(user);
	}

	public void deleteByUser(User user) {
		
		this.userDao.deleteByUser(user);
		
	}

	

	
	
	
	
	
	
	public List<User> selectUserHuiList() {
		
		return this.userDao.selectUserHuiList();
	}

	public void restoreByUser(User user) {
		
		this.userDao.restoreByUser(user);
	}

	public Map<String, Object> loginUser(UserRequest userRequest) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		//默认设置密码错误
		map.put("flag", Constant.LOGIN_PWD_ERROR);
		map.put("userInfo", null);
		
		//判断验证码
		if(userRequest.getUserImgCode() == null ||"".equals(userRequest.getUserImgCode())){
			//验证码为空
			map.put("flag", Constant.LOGIN_CODE_NULL);
		} else if (userRequest.getUserImgCode().equals(userRequest.getSysImgCode())) {
			 //连接数据库，查询用户信息
			UserResponse userResponse = userDao.loginUser(userRequest);
	 
					if(userResponse == null){
						//用户不存在
						map.put("flag", Constant.LOGIN_ACCOUNT_ERROR);
							//判断是否被锁定（小于连续3次失败并且距离最近一次失败大于5分钟）
					} else if(userResponse!= null 
							&& (0 == userResponse.getLoginFailNum()
							|| 0 < (userResponse.getLoginFailNum() % 3)
							|| userResponse.getLoginFailDate() > 300000)){
				
								 if (userResponse.getUserPwd().equals(userRequest.getUserPwd())) {
									//登陆 成功
									
									map.put("flag", Constant.LOGIN_SUCCESS);
									//用户信息放入map中
									map.put("userInfo", userResponse);
									//登录成功后把次数归零
									userRequest.setLoginFailNum(0);
									
								} else {
									//密码错误
									//连接数据库，修改登陆失败的次数
									int loginFailNum = 1;
									if(userResponse.getLoginFailDate() > 300000){
										userRequest.setLoginFailNum(1);
									}else {
										userRequest.setLoginFailNum(userResponse.getLoginFailNum() + 1);
										loginFailNum = userResponse.getLoginFailNum() + 1;
									}
									
									map.put("loginfailnum", loginFailNum);
								}
								 //登录失败次数
								 userDao.updateLoginFailNum(userRequest);
						
					} else {
						//账号锁定
						map.put("flag", Constant.ACCOUNT_LOCKED);
					}
			
		} else {
			////验证码错误
			map.put("flag", Constant.LOGIN_CODE_ERROR);
		}
		return map;
	}




	

	
	
	
	
	@Override
	public List<RolesResponse> selectUserRoleListJson(RolesRequest rolesRequest) {
		
		return userDao.selectUserRoleListJson(rolesRequest);
	}



	@Override
	public void insertUserRoleList(List<RolesRequest> rolesRequestList) {
		 // 1、删除用户之前的所有角色（mid） get(0)获取第一个元素
		 userDao.deleteAllRolesByUserID(rolesRequestList.get(0));
		// 2、添加用户勾选的所有角色（mid）
		 userDao.insertUserRoleList(rolesRequestList);
	}



	@Override
	public int selectUserCount(UserRequest userRequest) {
		
		return userDao.selectUserCount(userRequest);
	}

	@Override
	public List<UserResponse> selectUserList(UserRequest userRequest) {
		
		return userDao.selectUserList(userRequest);
	}

	@Override
	public List<Map<String, Object>> selectTreeListJson(MenuRequest menuRequest) {
		
		
		List<Map<String, Object>> treeList = userDao.selectTreeListJson(menuRequest);
		
		if(treeList!=null && treeList.size() > 0 ){
			//开始调用递归
			treeList = queryTreeListNodes(treeList, menuRequest);
		}else{
			treeList = new ArrayList<Map<String,Object>>();
		}
		
		
		
		return treeList;
	}

	
	private List<Map<String, Object>> queryTreeListNodes(List<Map<String, Object>> treeList, MenuRequest menuRequest){
		for(Map<String, Object> map:treeList){
			if("0".equals(map.get("pid").toString())){
				//取出ID作为下次查询的pid
				int pid = Integer.valueOf(map.get("id").toString());
				menuRequest.setPid(pid);
				List<Map<String, Object>> queryTreeListNodes = queryTreeListNodes(userDao.selectTreeListJson(menuRequest),menuRequest);
				map.put("nodes", queryTreeListNodes);
			}
		}
		
		return treeList;
		
	}

	@Override
	public List<MenuResponse> selectUserMenuListJson(MenuRequest menuRequest) {
		
		return userDao.selectUserMenuListJson(menuRequest);
	}
	
	@Override
	public UserRequest selectUserById(Integer userId) {
		
		return this.userDao.selectUserById(userId);
	}
	
	@Override
	public void updateUser(UserRequest userRequest) {
		
		this.userDao.updateUser(userRequest);
	}


	
	
}
