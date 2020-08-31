package com.xcmis.feefax.modules.dao;

import com.xcmis.feefax.modules.entity.ChargeKind;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;


@MyBatisDao
public interface ChargeKindDao extends IBaseDao<ChargeKind> {
	
	public long getChargeKindListTotal(ChargeKind chargeKind);
	
	public List<ChargeKind> getChargeKindListByCondition(Map<String, Object> map);

}
