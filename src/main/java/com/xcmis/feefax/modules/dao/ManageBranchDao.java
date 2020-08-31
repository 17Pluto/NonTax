package com.xcmis.feefax.modules.dao;

import com.xcmis.feefax.modules.entity.ManageBranch;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;


@MyBatisDao
public interface ManageBranchDao extends IBaseDao<ManageBranch> {
	
	public long getManageBranchListTotal(ManageBranch manageBranch);
	
	public List<ManageBranch> getManageBranchListByCondition(Map<String, Object> map);

	public List<ManageBranch> getManageBranchListByChrId(ManageBranch manageBranch);


}
