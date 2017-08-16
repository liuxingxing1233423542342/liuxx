package com.jk.dao.menu.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.jk.dao.menu.MenuDao;
import com.jk.model.menu.MenuRequest;
import com.jk.model.menu.MenuResponse;
@Repository
public class MenuDaoImpl extends SqlMapClientDaoSupport implements MenuDao {

	@Override
	public List<MenuResponse> selectMenuListJson(MenuRequest menuRequest) {
		
		return this.getSqlMapClientTemplate().queryForList("menu.selectMenuListJson", menuRequest);
		
	}

	@Override
	public List<MenuResponse> selectFirstMenuList(MenuRequest menuRequest) {
		
		return this.getSqlMapClientTemplate().queryForList("menu.selectFirstMenuList",menuRequest);
	}

	@Override
	public void insertMenu(MenuRequest menuRequest) {
		
		this.getSqlMapClientTemplate().insert("menu.insertMenu",menuRequest);
	}

	@Override
	public void deleteByMenu(MenuRequest menuRequest) {
		
		this.getSqlMapClientTemplate().update("menu.deleteByMenu",menuRequest);
	}

}
