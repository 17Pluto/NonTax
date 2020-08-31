package com.xcmis.framework.modules.dao;

import java.util.List;
import java.util.Map;


import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;
import com.xcmis.framework.modules.entity.User;


@MyBatisDao
public interface UserDao extends IBaseDao<User> {
	
	public User get(String id);

	public User findByUsername(String username);
	
	public int editPassword(User user);
	
	public List<User> getUserListByDepartmentId(String departmentId);
	
	public long getUserListTotal(User user);
	
	public List<User> getUserListByCondition(Map<String, Object> map);

	public List<User> getBelongOrgList(User user);




}
