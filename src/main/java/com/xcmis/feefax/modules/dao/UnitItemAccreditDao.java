package com.xcmis.feefax.modules.dao;

import com.xcmis.feefax.modules.entity.UnitItemAccredit;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;


@MyBatisDao
public interface UnitItemAccreditDao extends IBaseDao<UnitItemAccredit> {
	
	public long getAccreditListTotal(UnitItemAccredit accredit);
	
	public List<UnitItemAccredit> getAccreditListByCondition(Map<String, Object> map);

}
