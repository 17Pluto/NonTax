package com.xcmis.feefax.modules.dao;

import com.xcmis.feefax.modules.entity.IncomeBank;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;


@MyBatisDao
public interface IncomeBankDao extends IBaseDao<IncomeBank> {
	
	public long getIncomeBankListTotal(IncomeBank incomeBank);
	
	public List<IncomeBank> getIncomeBankListByCondition(Map<String, Object> map);

	public List<IncomeBank> getIncomeBankListByChrId(IncomeBank incomeBank);
}
