package com.jk.service.menu.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jk.dao.menu.MenuDao;
import com.jk.model.menu.MenuRequest;
import com.jk.model.menu.MenuResponse;
import com.jk.service.menu.MenuService;

@Service
public class MenuServiceImpl implements MenuService {
	
	@Resource
	private MenuDao menuDao;

	@Override
	public List<MenuResponse> selectMenuListJson(MenuRequest menuRequest) {
		
		return menuDao.selectMenuListJson(menuRequest);
	}

	@Override
	public List<MenuResponse> selectFirstMenuList(MenuRequest menuRequest) {
		
		return menuDao.selectFirstMenuList(menuRequest);
	}

	@Override
	public void insertMenu(MenuRequest menuRequest) {
			//判断是否是模块
			if (0 == menuRequest.getPid()) {
				menuRequest.setParent(true);
			}
			menuDao.insertMenu(menuRequest);
	}

	@Override
	public void deleteByMenu(MenuRequest menuRequest) {
		
		menuDao.deleteByMenu(menuRequest);
	}
	
	

}
