package com.xcmis.feefax.modules.dao;


import com.xcmis.feefax.modules.entity.BillEndReport;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;

/**
 * 功能
 *
 * @author
 * @see
 */
@MyBatisDao
public interface BillEndReportDao extends IBaseDao<BillEndReport> {
    public String getMaxNo(BillEndReport billEndReport);

    public long getBillEndReportListTotal(BillEndReport billEndReport);

    public List<BillEndReport> getBillEndReportListByCondition(Map<String, Object> map);

    public int checkBillEndReport(BillEndReport billEndReport);
}
