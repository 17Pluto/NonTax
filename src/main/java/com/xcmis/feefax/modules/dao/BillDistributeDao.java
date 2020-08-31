package com.xcmis.feefax.modules.dao;


import com.xcmis.feefax.modules.entity.BillDistribute;
import com.xcmis.feefax.modules.entity.BillPutSale;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface BillDistributeDao extends IBaseDao<BillDistribute> {

    public long getBillDistributeListTotal(BillDistribute billDistribute);

    public List<BillDistribute> getBillDistributeListByCondition(Map<String, Object> map);

    public String getMaxNo(BillDistribute billDistribute);

    public int checkBillDistribute(BillDistribute billDistribute);

    public List<BillDistribute> testFindAllList(BillDistribute billDistribute);

}
