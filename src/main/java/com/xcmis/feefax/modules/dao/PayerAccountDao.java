package com.xcmis.feefax.modules.dao;

import com.xcmis.feefax.modules.entity.PayerAccount;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;


@MyBatisDao
public interface PayerAccountDao extends IBaseDao<PayerAccount> {
	
	public long getPayerAccountListTotal(PayerAccount payerAccount);
	
	public List<PayerAccount> getPayerAccountListByCondition(Map<String, Object> map);

}
