package com.jk.service.role.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;


import com.jk.dao.role.RoleDao;
import com.jk.model.menu.MenuRequest;
import com.jk.model.menu.MenuResponse;
import com.jk.model.roles.RolesRequest;
import com.jk.model.roles.RolesResponse;
import com.jk.service.role.RoleService;
@Service
public class RoleServiceImpl implements RoleService {
	@Resource
	private RoleDao roleDao;

	@Override
	public int selectRoleCount(RolesRequest rolesRequest) {
		
		return roleDao.selectRoleCount(rolesRequest);
	}


	@Override
	public List<RolesResponse> selectRoleList(RolesRequest rolesRequest) {
	
		return roleDao.selectRoleList(rolesRequest);
	}


	@Override
	public List<MenuResponse> selectRoleMenuListJson(MenuRequest menuRequest) {
		
		return roleDao.selectRoleMenuListJson(menuRequest);
	}


	@Override
	public void insertRoleMenuList(List <MenuRequest> menuRequestList) {
		//新增前先删除角色原有的权限
		roleDao.deleteAllRoleMenuById(menuRequestList.get(0));
		//然后给角色新增权限
		roleDao.insertRoleMenuList(menuRequestList);
		
		
	}


	@Override
	public void addRoleInfo(RolesRequest rolesRequest) {
		
		roleDao.addRoleInfo(rolesRequest);
	}


	@Override
	public void deleteByRolse(RolesRequest rolesRequest) {
		
		roleDao.deleteByRolse(rolesRequest);
	}


	
}
