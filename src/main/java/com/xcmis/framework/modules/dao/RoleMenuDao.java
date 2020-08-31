package com.xcmis.framework.modules.dao;

import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;
import com.xcmis.framework.modules.entity.RoleMenu;

import java.util.List;
import java.util.Map;


@MyBatisDao
public interface RoleMenuDao extends IBaseDao<RoleMenu> {

}
