package com.xcmis.framework.modules.dao;

import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;
import com.xcmis.framework.modules.entity.Resource;

import java.util.List;
import java.util.Map;


@MyBatisDao
public interface ResourceDao extends IBaseDao<Resource> {

	
	public List<Resource> getResourceListByUserName(Resource resource);

}
