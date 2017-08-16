package com.jk.dao.role.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.jk.dao.role.RoleDao;
import com.jk.model.menu.MenuRequest;
import com.jk.model.menu.MenuResponse;
import com.jk.model.roles.RolesRequest;
import com.jk.model.roles.RolesResponse;
@Repository
public class RoleDaoImpl extends SqlMapClientDaoSupport implements RoleDao {

	@Override
	public int selectRoleCount(RolesRequest rolesRequest) {
	
		return (Integer) this.getSqlMapClientTemplate().queryForObject("role.selectRoleCount",rolesRequest);
	}

	@Override
	public List<RolesResponse> selectRoleList(RolesRequest rolesRequest) {

		return this.getSqlMapClientTemplate().queryForList("role.selectRoleList",rolesRequest);
	}

	@Override
	public List<MenuResponse> selectRoleMenuListJson(MenuRequest menuRequest) {
		
		return this.getSqlMapClientTemplate().queryForList("role.selectRoleMenuListJson",menuRequest);
	}

	@Override
	public void deleteAllRoleMenuById(MenuRequest menuRequest) {
		
		this.getSqlMapClientTemplate().delete("role.deleteAllRoleMenuById",menuRequest);
	}

	@Override
	public void insertRoleMenuList(final List<MenuRequest> menuRequestList) {
		
		this.getSqlMapClientTemplate().execute(new SqlMapClientCallback<Object>() {

			@Override
			public Object doInSqlMapClient(SqlMapExecutor sqlMap) throws SQLException {
				//开启
				sqlMap.startBatch();
				for (MenuRequest menuRequest : menuRequestList) {
					sqlMap.insert("role.insertRoleMenu",menuRequest);
				}
				sqlMap.executeBatch();
				return null;
			}
		});
	}

	@Override
	public void addRoleInfo(RolesRequest rolesRequest) {
		
		this.getSqlMapClientTemplate().insert("role.addRoleInfo",rolesRequest);
	}

	@Override
	public void deleteByRolse(RolesRequest rolesRequest) {
		
		this.getSqlMapClientTemplate().update("role.deleteByRolse",rolesRequest);
	}

	

	

}
