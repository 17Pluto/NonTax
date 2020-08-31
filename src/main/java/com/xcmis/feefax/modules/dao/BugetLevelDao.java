package com.xcmis.feefax.modules.dao;

import com.xcmis.feefax.modules.entity.BugetLevel;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;


@MyBatisDao
public interface BugetLevelDao extends IBaseDao<BugetLevel> {
	
	public long getBugetLevelListTotal(BugetLevel bugetLevel);
	
	public List<BugetLevel> getBugetLevelListByCondition(Map<String, Object> map);

}
