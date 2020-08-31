package com.xcmis.feefax.modules.dao;

import com.xcmis.feefax.modules.entity.BillKind;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;


@MyBatisDao
public interface BillKindDao extends IBaseDao<BillKind> {
	
	public long getBillKindListTotal(BillKind billKind);
	
	public List<BillKind> getBillKindListByCondition(Map<String, Object> map);

}
