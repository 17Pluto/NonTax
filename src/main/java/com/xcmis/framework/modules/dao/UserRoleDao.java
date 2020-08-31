package com.xcmis.framework.modules.dao;

import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;
import com.xcmis.framework.modules.entity.UserRole;


@MyBatisDao
public interface UserRoleDao extends IBaseDao<UserRole> {
	
}
