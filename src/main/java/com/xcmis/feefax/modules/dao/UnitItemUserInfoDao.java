package com.xcmis.feefax.modules.dao;

import com.xcmis.feefax.modules.entity.UnitItemUserInfo;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface UnitItemUserInfoDao extends IBaseDao<UnitItemUserInfo> {

    public long getUnitItemUserInfoListTotal(UnitItemUserInfo unitItemUserInfo);

    public List<UnitItemUserInfo> getUnitItemUserInfoListByCondition(Map<String, Object> map);



}
