package com.xcmis.framework.modules.dao;

import java.util.List;
import java.util.Map;

import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;
import com.xcmis.framework.modules.entity.BaseCode;


@MyBatisDao
public interface BaseCodeDao extends IBaseDao<BaseCode> {
	public List<BaseCode> getBaseCodeListByBaseClass(String baseClassCode);

	public List<BaseCode> getBaseCodeListByBaseClassAndDepartmentId(BaseCode baseCode);
	
	public long getBaseCodeListTotal(BaseCode baseCode);
	
	public List<BaseCode> getBaseCodeListByCondition(Map<String, Object> map);
	
	
}
