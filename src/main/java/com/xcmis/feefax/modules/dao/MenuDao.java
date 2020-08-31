package com.xcmis.feefax.modules.dao;

import com.xcmis.feefax.modules.entity.Menu;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;


@MyBatisDao
public interface MenuDao extends IBaseDao<Menu> {
    public List<Menu> getMenuListByUserId(Menu menu);
}
