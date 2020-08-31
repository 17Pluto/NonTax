package com.xcmis.feefax.modules.dao;

import com.xcmis.feefax.modules.entity.UpdownAccount;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;


@MyBatisDao
public interface UpdownAccountDao extends IBaseDao<UpdownAccount> {
	
	public long getUpdownAccountListTotal(UpdownAccount updownAccount);
	
	public List<UpdownAccount> getUpdownAccountListByCondition(Map<String, Object> map);

}
