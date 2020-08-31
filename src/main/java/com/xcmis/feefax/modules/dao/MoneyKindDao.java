package com.xcmis.feefax.modules.dao;

import com.xcmis.feefax.modules.entity.MoneyKind;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;


@MyBatisDao
public interface MoneyKindDao extends IBaseDao<MoneyKind> {
	
	public long getMoneyKindListTotal(MoneyKind moneyKind);
	
	public List<MoneyKind> getMoneyKindListByCondition(Map<String, Object> map);

}
