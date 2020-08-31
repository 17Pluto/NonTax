package com.xcmis.feefax.modules.dao;


import com.xcmis.feefax.modules.entity.PrintTable;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface PrintTableDao extends IBaseDao<PrintTable> {

    public long getPrintTableListTotal(PrintTable printTable);

    public List<PrintTable> getPrintTableListByCondition(Map<String, Object> map);

}
