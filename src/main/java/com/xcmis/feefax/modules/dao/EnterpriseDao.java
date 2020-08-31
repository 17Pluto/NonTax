package com.xcmis.feefax.modules.dao;

import com.xcmis.feefax.modules.entity.Enterprise;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;


@MyBatisDao
public interface EnterpriseDao extends IBaseDao<Enterprise> {
	public long getEnterpriseListTotal(Enterprise enterprise);

	public List<Enterprise> getEnterpriseListByCondition(Map<String, Object> map);

	public List<Enterprise> getEnterpriseListByChrId(Enterprise enterprise);


}
