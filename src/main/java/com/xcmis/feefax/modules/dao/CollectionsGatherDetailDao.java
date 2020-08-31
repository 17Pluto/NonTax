package com.xcmis.feefax.modules.dao;

/**
 * @author fangzhe
 * @date 2020-04-26
 *  dao
 */
import com.xcmis.feefax.modules.entity.CollectionsGatherDetail;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface CollectionsGatherDetailDao extends IBaseDao<CollectionsGatherDetail> {

    public long getCollectionsGatherDetailListTotal(CollectionsGatherDetail collectionsGatherDetail);

    public List<CollectionsGatherDetail> getCollectionsGatherDetailListByCondition(Map<String, Object> map);

    public List<CollectionsGatherDetail> findAllListGroupByCollectId(CollectionsGatherDetail collectionsGatherDetail);

    public int updateIncitemName(CollectionsGatherDetail collectionsGatherDetail);

}
