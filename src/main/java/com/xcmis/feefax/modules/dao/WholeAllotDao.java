package com.xcmis.feefax.modules.dao;

import com.xcmis.feefax.modules.entity.WholeAllot;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;


@MyBatisDao
public interface WholeAllotDao extends IBaseDao<WholeAllot> {
	
	public long getWholeAllotListTotal(WholeAllot wholeAllot);
	
	public List<WholeAllot> getWholeAllotListByCondition(Map<String, Object> map);

}
