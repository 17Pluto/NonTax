package com.xcmis.feefax.modules.dao;

import com.xcmis.feefax.modules.entity.IncomeBankAccount;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;


@MyBatisDao
public interface IncomeBankAccountDao extends IBaseDao<IncomeBankAccount> {
	
	public long getIncomeBankAccountListTotal(IncomeBankAccount incomeBankAccount);
	
	public List<IncomeBankAccount> getIncomeBankAccountListByCondition(Map<String, Object> map);

}
