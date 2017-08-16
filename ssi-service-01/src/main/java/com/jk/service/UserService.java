package com.jk.service;

import java.util.List;
import java.util.Map;

import com.jk.model.menu.MenuRequest;
import com.jk.model.menu.MenuResponse;
import com.jk.model.roles.RolesRequest;
import com.jk.model.roles.RolesResponse;
import com.jk.model.user.User;
import com.jk.model.user.UserRequest;
import com.jk.model.user.UserResponse;

public interface UserService {

	

	public abstract void insertUser(User user);

	

	
	

	
	
	
	public abstract List<User> selectUserHuiList();

	public abstract void restoreByUser(User user);

	public abstract Map<String, Object> loginUser(UserRequest userRequest);

	
	
	
	
	
	
	List<RolesResponse> selectUserRoleListJson(RolesRequest rolesRequest);

	void insertUserRoleList(List<RolesRequest> rolesRequestList);

	int selectUserCount(UserRequest userRequest);

	List<UserResponse> selectUserList(UserRequest userRequest);

	List<Map<String, Object>> selectTreeListJson(MenuRequest menuRequest);

	List<MenuResponse> selectUserMenuListJson(MenuRequest menuRequest);

	void deleteByUser(User user);

	UserRequest selectUserById(Integer userId);

	void updateUser(UserRequest userRequest);


	








	


	



}
