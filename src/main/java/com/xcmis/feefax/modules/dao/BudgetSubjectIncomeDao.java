package com.xcmis.feefax.modules.dao;

import com.xcmis.feefax.modules.entity.BudgetSubjectIncome;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;


@MyBatisDao
public interface BudgetSubjectIncomeDao extends IBaseDao<BudgetSubjectIncome> {
	
	public long getBudgetSubjectIncomeListTotal(BudgetSubjectIncome budgetSubjectIncome);
	
	public List<BudgetSubjectIncome> getBudgetSubjectIncomeListByCondition(Map<String, Object> map);

	public List<BudgetSubjectIncome> getBudgetSubjectIncomeListByChrId(BudgetSubjectIncome budgetSubjectIncome);

}
