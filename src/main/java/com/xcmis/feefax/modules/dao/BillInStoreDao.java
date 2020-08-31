package com.xcmis.feefax.modules.dao;


import com.xcmis.feefax.modules.entity.BillInStore;
import com.xcmis.feefax.modules.entity.IncomeItem;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface BillInStoreDao extends IBaseDao<BillInStore> {

    public long getBillInStoreListTotal(BillInStore billInStore);

    public List<BillInStore> getBillInStoreListByCondition(Map<String, Object> map);

    public String getMaxNo(BillInStore billInStore);

    public int checkBillInStore(BillInStore billInStore);

}
