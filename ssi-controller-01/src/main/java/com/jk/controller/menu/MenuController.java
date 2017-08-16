package com.jk.controller.menu;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jk.model.menu.MenuRequest;
import com.jk.model.menu.MenuResponse;
import com.jk.service.menu.MenuService;

@Controller
@RequestMapping("/menu")
public class MenuController {
	
	@Resource
	private MenuService menuServcie;

	//跳转menu页面
	@RequestMapping("toMenuListPage")
	public String toMenuListPage(){
		//---
		return "menu/menu_list";		
	}
	
	@RequestMapping("selectMenuListJson")
	@ResponseBody
	public List<MenuResponse> selectMenuListJson(MenuRequest menuRequest){
		List<MenuResponse> menuList = menuServcie.selectMenuListJson(menuRequest);
		return menuList;
	}
	
	//跳转menu添加页面
	@RequestMapping("toAddMenuPage")
	public String toAddMenuPage(Model m){
		//查询一级菜单列表，展示到添加页面
		List<MenuResponse> menuList =  menuServcie.selectFirstMenuList(new MenuRequest());
		m.addAttribute("menuList",menuList);
		return "menu/add_menu";		
	}
	//添加menu信息
	@RequestMapping("insertMenu")
	@ResponseBody
	public String insertMenu(MenuRequest menuRequest){
		menuServcie.insertMenu(menuRequest);
		return "{}";		
	}
	
	//删除一个权限
	@RequestMapping("deleteByMenu")
	public String deleteByMenu(MenuRequest menuRequest){
		menuServcie.deleteByMenu(menuRequest);
		return "redirect:selectMenuListJson.action";		
	}
	
	
	
	
}
