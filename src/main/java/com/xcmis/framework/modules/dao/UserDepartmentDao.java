package com.xcmis.framework.modules.dao;

import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;
import com.xcmis.framework.modules.entity.UserDepartment;



@MyBatisDao
public interface UserDepartmentDao extends IBaseDao<UserDepartment> {
	
}
