package com.xcmis.feefax.modules.dao;


import com.xcmis.feefax.modules.entity.BillDetail;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface BillDetailDao extends IBaseDao<BillDetail> {
    public BillDetail getBillDetailMinNo(BillDetail billDetail);
    public BillDetail isvalidBillNo(BillDetail billDetail);
    public List<BillDetail> findListByCondition(BillDetail billDetail);

    public int updateIsEndReport(BillDetail billDetail);
    public List<BillDetail> findUntaxBill(BillDetail billDetail);
}
