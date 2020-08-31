package com.xcmis.feefax.modules.dao;


import com.xcmis.feefax.modules.entity.BillReDisList;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface BillReDisListDao extends IBaseDao<BillReDisList> {

    public long getBillReDisListListTotal(BillReDisList billReDisList);

    public List<BillReDisList> getBillReDisListListByCondition(Map<String, Object> map);

    public List<BillReDisList> getUntaxBillnameIdListByBilldistributer(BillReDisList billReDisList);

}
