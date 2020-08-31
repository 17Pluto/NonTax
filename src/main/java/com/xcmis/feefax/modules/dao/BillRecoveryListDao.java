package com.xcmis.feefax.modules.dao;

import com.xcmis.feefax.modules.entity.BillRecoveryList;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface BillRecoveryListDao extends IBaseDao<BillRecoveryList> {

    public long getBillRecoveryListListTotal(BillRecoveryList billRecoveryList);

    public List<BillRecoveryList> getBillRecoveryListListByCondition(Map<String, Object> map);

    public List<BillRecoveryList> getBillRecoveryListByUntaxBillnameId(BillRecoveryList billRecoveryList);
}
