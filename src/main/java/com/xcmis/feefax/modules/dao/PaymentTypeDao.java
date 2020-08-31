package com.xcmis.feefax.modules.dao;

import com.xcmis.feefax.modules.entity.PaymentType;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;


@MyBatisDao
public interface PaymentTypeDao extends IBaseDao<PaymentType> {
	
	public long getPaymentTypeListTotal(PaymentType paymentType);
	
	public List<PaymentType> getPaymentTypeListByCondition(Map<String, Object> map);

}
