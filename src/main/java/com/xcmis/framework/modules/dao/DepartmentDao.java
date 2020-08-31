package com.xcmis.framework.modules.dao;

import java.util.List;
import java.util.Map;

import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;
import com.xcmis.feefax.modules.entity.Enterprise;


@MyBatisDao
public interface DepartmentDao extends IBaseDao<Enterprise>{
	public long getDepartmentListTotal(Enterprise department);
	
	public List<Enterprise> getDepartmentListByCondition(Map<String, Object> map);
	
	public List<Enterprise> getDepartmentListByUser(String userId);
	
}
