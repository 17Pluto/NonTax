package com.xcmis.feefax.modules.dao;

import com.xcmis.feefax.modules.entity.PayMode;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;


@MyBatisDao
public interface PayModeDao extends IBaseDao<PayMode> {
	
	public long getPayModeListTotal(PayMode payMode);
	
	public List<PayMode> getPayModeListByCondition(Map<String, Object> map);

}
