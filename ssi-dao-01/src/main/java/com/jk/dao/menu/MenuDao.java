package com.jk.dao.menu;

import java.util.List;

import com.jk.model.menu.MenuRequest;
import com.jk.model.menu.MenuResponse;

public interface MenuDao {

	List<MenuResponse> selectMenuListJson(MenuRequest menuRequest);

	List<MenuResponse> selectFirstMenuList(MenuRequest menuRequest);

	void insertMenu(MenuRequest menuRequest);

	void deleteByMenu(MenuRequest menuRequest);

}
