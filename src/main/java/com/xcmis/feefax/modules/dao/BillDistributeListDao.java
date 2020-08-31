package com.xcmis.feefax.modules.dao;


import com.xcmis.feefax.modules.entity.BillDistribute;
import com.xcmis.feefax.modules.entity.BillDistributeList;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface BillDistributeListDao extends IBaseDao<BillDistributeList> {

    public long getBillDistributeListListTotal(BillDistributeList billDistributeList);

    public List<BillDistributeList> getBillDistributeListListByCondition(Map<String, Object> map);

    public List<BillDistributeList> getUntaxBillnameIdListByBilldistributer(BillDistributeList billDistributeList);

}
