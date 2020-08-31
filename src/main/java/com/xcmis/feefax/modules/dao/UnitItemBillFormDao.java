package com.xcmis.feefax.modules.dao;

import com.xcmis.feefax.modules.entity.UnitItemBillForm;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface UnitItemBillFormDao extends IBaseDao<UnitItemBillForm> {

    public long getUnitItemBillFormListTotal(UnitItemBillForm unitItemBillForm);

    public List<UnitItemBillForm> getUnitItemBillFormListByCondition(Map<String, Object> map);



}
