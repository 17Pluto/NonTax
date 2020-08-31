package com.xcmis.feefax.modules.dao;


import com.xcmis.feefax.modules.entity.BillPutSale;
import com.xcmis.feefax.modules.entity.BillPutSaleList;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface BillPutSaleListDao extends IBaseDao<BillPutSaleList> {

    public long getBillPutSaleListListTotal(BillPutSaleList billPutSaleList);

    public List<BillPutSaleList> getBillPutSaleListListByCondition(Map<String, Object> map);

    public List<BillPutSaleList> getBillPutSaleListByUntaxBillnameId(BillPutSaleList billPutSaleList);

}
