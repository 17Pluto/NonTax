package com.xcmis.feefax.modules.dao;


import com.xcmis.feefax.modules.entity.RgUser;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface RgUserDao extends IBaseDao<RgUser> {
    public long getRgUserListTotal(RgUser rgUser);

    public List<RgUser> getRgUserListByCondition(Map<String, Object> map);

}
