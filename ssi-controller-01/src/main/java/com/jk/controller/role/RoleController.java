package com.jk.controller.role;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jk.model.menu.MenuRequest;
import com.jk.model.menu.MenuResponse;
import com.jk.model.roles.RolesRequest;
import com.jk.model.roles.RolesResponse;

import com.jk.service.role.RoleService;
@Controller
@RequestMapping("/role")
public class RoleController {

	@Resource
	private RoleService roleService;
	
	//跳转角色列表
	@RequestMapping("selectRole")
	public String selectRole(){
		
		return "role/role_list";		
	}
	
	//查询角色列表 +分页
	@RequestMapping("selectRoleList")
	@ResponseBody
	public Map<String, Object> selectRoleList(String pageNumber,RolesRequest rolesRequest){
		//查询总条数
				int totalCount = roleService.selectRoleCount(rolesRequest);
				rolesRequest.setTotalCount(totalCount);
				if (null == pageNumber || "".equals(pageNumber.trim())) {
					pageNumber = "1";
				}
				rolesRequest.setPageIndex(Integer.valueOf(pageNumber));
				//计算分页信息
				rolesRequest.calculate();
				//查询列表
				List<RolesResponse> roleList = roleService.selectRoleList(rolesRequest);
				//封装结果
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("total", totalCount);
				map.put("rows", roleList);
				return map;
		
	}
	
	//跳转角色权限页面
	@RequestMapping("toRoleMenuPage")
	public String toRoleMenuPage(Model m, RolesRequest rolesRequest){
		m.addAttribute("rolesId", rolesRequest.getRolesId());
		return "role/role_tree_list";		
	}
	
	//查询角色权限
	@RequestMapping("selectRoleMenuListJson")
	@ResponseBody
	public List<MenuResponse> selectRoleMenuListJson(MenuRequest menuRequest ){
		List<MenuResponse> menuList = roleService.selectRoleMenuListJson(menuRequest);
		return menuList;		
	}
	
	//添加角色权限
	@RequestMapping("insertRoleMenuList")
	@ResponseBody
	public String insertRoleMenuList(@RequestBody List <MenuRequest> menuRequestList){
		roleService.insertRoleMenuList(menuRequestList);
		return "{}";		
	}
	
	//跳转角色添加页面
	@RequestMapping("queryAddRolePage")
	public String queryAddRolePage(){
		
		return "role/role_add";		
	}
	//添加角色
	@RequestMapping("addRoleInfo")
	@ResponseBody
	public String addRoleInfo(RolesRequest rolesRequest){
		roleService.addRoleInfo(rolesRequest);
		return "{}";
		
	}
	//删除角色信息
	@RequestMapping("deleteByRolse")
	public String deleteByRolse(RolesRequest rolesRequest){
		roleService.deleteByRolse(rolesRequest);
		return "redirect:selectRoleList.action";
		
	}
	
}
