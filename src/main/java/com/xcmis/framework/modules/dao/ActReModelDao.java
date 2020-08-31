package com.xcmis.framework.modules.dao;

import java.util.List;
import java.util.Map;

import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;
import com.xcmis.framework.modules.entity.ActReModel;


@MyBatisDao
public interface ActReModelDao extends IBaseDao<ActReModel> {
	
	public long getActReModelListTotal(ActReModel actReModel);
	
	public List<ActReModel> getActReModelListByCondition(Map<String, Object> map);

}
