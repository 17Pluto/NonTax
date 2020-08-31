package com.xcmis.feefax.modules.dao;

import com.xcmis.feefax.modules.entity.UntaxIncomeBank;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;


@MyBatisDao
public interface UntaxIncomeBankDao extends IBaseDao<UntaxIncomeBank> {
	
	public long getUntaxIncomeBankListTotal(UntaxIncomeBank untaxIncomeBank);
	
	public List<UntaxIncomeBank> getUntaxIncomeBankListByCondition(Map<String, Object> map);

}
