package com.jk.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.jk.dao.UserDao;
import com.jk.model.menu.MenuRequest;
import com.jk.model.menu.MenuResponse;
import com.jk.model.roles.RolesRequest;
import com.jk.model.roles.RolesResponse;
import com.jk.model.user.User;
import com.jk.model.user.UserRequest;
import com.jk.model.user.UserResponse;
@Repository
public class UserDaoImpl extends SqlMapClientDaoSupport implements UserDao  {


	
	
	
	
	@Override
	public void insertUser(User user) {
		
		getSqlMapClientTemplate().insert("user.insertUser", user);
	}

	@Override
	public void deleteByUser(User user) {
	
		getSqlMapClientTemplate().update("user.deleteByUser",user);
	}


	

	

	@Override
	public List<User> selectUserHuiList() {
		
		return getSqlMapClientTemplate().queryForList("user.selectUserHuiList");
	}

	@Override
	public void restoreByUser(User user) {
		
		getSqlMapClientTemplate().update("user.restoreByUser",user);
	}

	@Override
	public UserResponse loginUser(UserRequest userRequest) {
		
		return (UserResponse) this.getSqlMapClientTemplate().queryForObject("user.loginUser", userRequest);
	}

	@Override
	public void updateLoginFailNum(UserRequest userRequest) {
		
		this.getSqlMapClientTemplate().update("user.updateLoginFailNum",userRequest);
	}



	
	
	
	
	
	
	
	
	

	@Override
	public List<RolesResponse> selectUserRoleListJson(RolesRequest rolesRequest) {
		
		return this.getSqlMapClientTemplate().queryForList("user.selectUserRoleListJson", rolesRequest);
	}

	//删除原来选中的节点
	@Override
	public void deleteAllRolesByUserID(RolesRequest rolesRequest) {
		
		this.getSqlMapClientTemplate().delete("user.deleteAllRolesByUserID",rolesRequest);
	}

	//添加被选中的节点 
	@Override
	public void insertUserRoleList(final List<RolesRequest> rolesRequestList) {
		
			this.getSqlMapClientTemplate().execute(new SqlMapClientCallback<Object>() {

				@Override
				public Object doInSqlMapClient(SqlMapExecutor sqlMap) throws SQLException {
					//开启批量
					sqlMap.startBatch();
					for (RolesRequest rolesRequest : rolesRequestList) {
						sqlMap.insert("user.insertUserRoleList",rolesRequest);
					}
					//执行批量操作
					sqlMap.executeBatch();
					return null;
				}
			});
	}



	//查询总条数
	@Override
	public int selectUserCount(UserRequest userRequest) {
		
		return (Integer) this.getSqlMapClientTemplate().queryForObject("user.selectUserCount",userRequest);
	}

	@Override
	public List<UserResponse> selectUserList(UserRequest userRequest) {
		
		return this.getSqlMapClientTemplate().queryForList("user.selectUserList",userRequest);
				
	}

	@Override
	public List<Map<String, Object>> selectTreeListJson(MenuRequest menuRequest) {
		
		return this.getSqlMapClientTemplate().queryForList("user.selectTreeListJson",menuRequest);
	}

	@Override
	public List<MenuResponse> selectUserMenuListJson(MenuRequest menuRequest) {
	
		return this.getSqlMapClientTemplate().queryForList("user.selectUserMenuListJson",menuRequest);
	}

	@Override
	public UserRequest selectUserById(Integer userId) {
		
		return (UserRequest) getSqlMapClientTemplate().queryForList("user.selectUserById",userId).get(0);
	}

	@Override
	public void updateUser(UserRequest userRequest){
		
		getSqlMapClientTemplate().update("user.updateUser",userRequest);
	}



	
	
	
	


	
}
