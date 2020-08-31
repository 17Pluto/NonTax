package com.xcmis.feefax.modules.dao;

import com.xcmis.feefax.modules.entity.IncomeEnterprise;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface IncomeEnterpriseDao extends IBaseDao<IncomeEnterprise> {
    public long getIncomeEnterpriseListTotal(IncomeEnterprise incomeEnterprise);

    public List<IncomeEnterprise> getIncomeEnterpriseListByCondition(Map<String, Object> map);


    public List<IncomeEnterprise> getIncomeEnterpriseByChrId(IncomeEnterprise incomeEnterprise);

}
