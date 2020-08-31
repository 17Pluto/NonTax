package com.xcmis.feefax.modules.dao;

/**
 * @author fangzhe
 * @date 2020-05-19
 *  dao
 */
import com.xcmis.feefax.modules.entity.UntaxNosourceCollection;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface UntaxNosourceCollectionDao extends IBaseDao<UntaxNosourceCollection> {

    public long getUntaxNosourceCollectionListTotal(UntaxNosourceCollection untaxNosourceCollection);

    public List<UntaxNosourceCollection> getUntaxNosourceCollectionListByCondition(Map<String, Object> map);

    public int insertIds(UntaxNosourceCollection untaxNosourceCollection);
}
