package com.xcmis.feefax.modules.dao;

import com.xcmis.feefax.modules.entity.DivKind;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;


@MyBatisDao
public interface DivKindDao extends IBaseDao<DivKind> {
	
	public long getDivKindListTotal(DivKind divKind);
	
	public List<DivKind> getDivKindListByCondition(Map<String, Object> map);

}
