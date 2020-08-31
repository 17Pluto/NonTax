package com.xcmis.feefax.modules.dao;


import com.xcmis.feefax.modules.entity.BillReDis;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface BillReDisDao extends IBaseDao<BillReDis> {

    public long getBillReDisListTotal(BillReDis billReDis);

    public List<BillReDis> getBillReDisListByCondition(Map<String, Object> map);

    public String getMaxNo(BillReDis billReDis);

    public int checkBillReDis(BillReDis billReDis);

}
