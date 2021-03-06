package com.jk.service.role;

import java.util.List;

import com.jk.model.menu.MenuRequest;
import com.jk.model.menu.MenuResponse;
import com.jk.model.roles.RolesRequest;
import com.jk.model.roles.RolesResponse;

public interface RoleService {

	int selectRoleCount(RolesRequest rolesRequest);

	List<RolesResponse> selectRoleList(RolesRequest rolesRequest);

	List<MenuResponse> selectRoleMenuListJson(MenuRequest menuRequest);

	void insertRoleMenuList(List <MenuRequest> menuRequestList);

	void addRoleInfo(RolesRequest rolesRequest);

	void deleteByRolse(RolesRequest rolesRequest);

}
