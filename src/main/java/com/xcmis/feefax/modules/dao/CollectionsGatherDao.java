package com.xcmis.feefax.modules.dao;

/**
 * @author fangzhe
 * @date 2020-04-01
 *  dao
 */
import com.xcmis.feefax.modules.entity.CollectionsGather;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface CollectionsGatherDao extends IBaseDao<CollectionsGather> {

    public long getCollectionsGatherListTotal(CollectionsGather collectionsGather);

    public List<CollectionsGather> getCollectionsGatherListByCondition(Map<String, Object> map);

    public long getCollectionsGatherListTotalNew(CollectionsGather collectionsGather);

    public List<CollectionsGather> getCollectionsGatherListByConditionNew(Map<String, Object> map);

    public int cancelCollectionsGather(CollectionsGather collectionsGather);

    public CollectionsGather getCollectionsGatherForPrint(CollectionsGather collectionsGather);

    public int updatePrintflag(CollectionsGather collectionsGather);

    public int updateCollectflag(CollectionsGather collectionsGather);

    public int updateReturnflag(CollectionsGather collectionsGather);
}
