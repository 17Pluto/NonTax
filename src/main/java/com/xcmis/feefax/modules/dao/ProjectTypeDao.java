package com.xcmis.feefax.modules.dao;

import com.xcmis.feefax.modules.entity.ProjectType;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;


@MyBatisDao
public interface ProjectTypeDao extends IBaseDao<ProjectType> {
	
	public long getProjectTypeListTotal(ProjectType projectType);
	
	public List<ProjectType> getProjectTypeListByCondition(Map<String, Object> map);

}
