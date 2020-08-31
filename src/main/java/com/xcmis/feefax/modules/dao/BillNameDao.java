package com.xcmis.feefax.modules.dao;

import com.xcmis.feefax.modules.entity.BillName;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;


@MyBatisDao
public interface BillNameDao extends IBaseDao<BillName> {
	
	public long getBillNameListTotal(BillName billName);
	
	public List<BillName> getBillNameListByCondition(Map<String, Object> map);

	public List<BillName> getBillNameListByMainId(BillName billName);

}
