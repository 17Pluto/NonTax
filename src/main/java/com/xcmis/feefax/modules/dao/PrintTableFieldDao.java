package com.xcmis.feefax.modules.dao;


import com.xcmis.feefax.modules.entity.PrintTableField;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface PrintTableFieldDao extends IBaseDao<PrintTableField> {

    public long getPrintTableFieldListTotal(PrintTableField printTableField);

    public List<PrintTableField> getPrintTableFieldListByCondition(Map<String, Object> map);

}
