package com.xcmis.feefax.modules.dao;



import com.xcmis.feefax.modules.entity.BillRecovery;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface BillRecoveryDao extends IBaseDao<BillRecovery> {
    public long getBillRecoveryListTotal(BillRecovery billRecovery);
    public List<BillRecovery> getBillRecoveryListByCondition(Map<String, Object> map);

    public String getMaxNo(BillRecovery billRecovery);
}
