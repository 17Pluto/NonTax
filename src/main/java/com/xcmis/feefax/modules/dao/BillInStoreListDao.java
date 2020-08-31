package com.xcmis.feefax.modules.dao;


import com.xcmis.feefax.modules.entity.BillInStore;
import com.xcmis.feefax.modules.entity.BillInStoreList;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface BillInStoreListDao extends IBaseDao<BillInStoreList> {

    public long getBillInStoreListListTotal(BillInStoreList billInStoreList);

    public List<BillInStoreList> getBillInStoreListListByCondition(Map<String, Object> map);

    public List<BillInStoreList> getBillInStoreListByUntaxBillnameId(BillInStoreList billInStoreList);

}
