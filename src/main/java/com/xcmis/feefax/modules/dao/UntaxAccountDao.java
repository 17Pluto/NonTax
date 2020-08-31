package com.xcmis.feefax.modules.dao;
/**
 * @author fangzhe
 * @date 2020-04-16
 */

import com.xcmis.feefax.modules.entity.UntaxAccount;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface UntaxAccountDao extends IBaseDao<UntaxAccount> {

    public long getUntaxAccountListTotal(UntaxAccount untaxAccount);

    public List<UntaxAccount> getUntaxAccountListByCondition(Map<String, Object> map);

}
