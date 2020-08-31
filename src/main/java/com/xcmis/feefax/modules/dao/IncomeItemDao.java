package com.xcmis.feefax.modules.dao;

import com.xcmis.feefax.modules.entity.IncomeItem;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;


@MyBatisDao
public interface IncomeItemDao extends IBaseDao<IncomeItem> {
	
	public long getIncomeItemListTotal(IncomeItem incomeItem);
	
	public List<IncomeItem> getIncomeItemListByCondition(Map<String, Object> map);

	public List<IncomeItem> getIncomeItemListByChrId(IncomeItem incomeItem);

	public IncomeItem showIncomeItem(IncomeItem incomeItem);
}
