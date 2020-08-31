package com.xcmis.feefax.modules.dao;

import com.xcmis.feefax.modules.entity.ItemSort;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;


@MyBatisDao
public interface ItemSortDao extends IBaseDao<ItemSort> {
	
	public long getItemSortListTotal(ItemSort itemSort);
	
	public List<ItemSort> getItemSortListByCondition(Map<String, Object> map);

}
