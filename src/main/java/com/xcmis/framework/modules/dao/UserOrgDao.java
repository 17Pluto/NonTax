package com.xcmis.framework.modules.dao;

import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;
import com.xcmis.framework.modules.entity.UserOrg;

import java.util.List;
import java.util.Map;


@MyBatisDao
public interface UserOrgDao extends IBaseDao<UserOrg> {

}
