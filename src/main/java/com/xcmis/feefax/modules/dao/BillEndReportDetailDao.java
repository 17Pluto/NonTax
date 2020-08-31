package com.xcmis.feefax.modules.dao;

import com.xcmis.feefax.modules.entity.BillEndReportDetail;
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
public interface BillEndReportDetailDao extends IBaseDao<BillEndReportDetail> {
    public long getBillEndReportDetailListTotal(BillEndReportDetail billEndReportDetail);

    public List<BillEndReportDetail> getBillEndReportDetailListByCondition(Map<String, Object> map);
}
