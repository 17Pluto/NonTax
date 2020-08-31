package com.xcmis.framework.modules.dao;

import java.util.List;
import java.util.Map;

import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;
import com.xcmis.framework.modules.entity.Role;


@MyBatisDao
public interface RoleDao extends IBaseDao<Role> {
	
	public long getRoleListTotal(Role role);
	
	public List<Role> getRoleListByCondition(Map<String, Object> map);

}
