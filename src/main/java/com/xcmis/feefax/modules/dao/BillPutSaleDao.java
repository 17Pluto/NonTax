package com.xcmis.feefax.modules.dao;


import com.xcmis.feefax.modules.entity.BillPutSale;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface BillPutSaleDao extends IBaseDao<BillPutSale> {

    public long getBillPutSaleListTotal(BillPutSale billPutSale);

    public List<BillPutSale> getBillPutSaleListByCondition(Map<String, Object> map);

    public String getMaxNo(BillPutSale billPutSale);

    public int checkBillPutSale(BillPutSale billPutSale);

}
