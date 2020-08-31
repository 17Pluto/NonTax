package com.xcmis.framework.modules.dao;

import java.util.List;
import java.util.Map;

import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;
import com.xcmis.framework.modules.entity.Permission;


@MyBatisDao
public interface PermissionDao extends IBaseDao<Permission> {
	
	public long getPermissionListTotal(Permission permission);
	
	public List<Permission> getPermissionListByCondition(Map<String, Object> map);
	
}
