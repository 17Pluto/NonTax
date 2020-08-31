package com.xcmis.feefax.modules.dao;

import com.xcmis.feefax.modules.entity.ReceiverAccount;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;


@MyBatisDao
public interface ReceiverAccountDao extends IBaseDao<ReceiverAccount> {
	
	public long getReceiverAccountListTotal(ReceiverAccount receiverAccount);
	
	public List<ReceiverAccount> getReceiverAccountListByCondition(Map<String, Object> map);

}
