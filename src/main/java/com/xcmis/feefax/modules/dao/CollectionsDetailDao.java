package com.xcmis.feefax.modules.dao;

/**
 * @author fangzhe
 * @date 2020-04-01
 *  dao
 */
import com.xcmis.feefax.modules.entity.CollectionsDetail;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface CollectionsDetailDao extends IBaseDao<CollectionsDetail> {

    public long getCollectionsDetailListTotal(CollectionsDetail collectionDetail);

    public List<CollectionsDetail> getCollectionsDetailListByCondition(Map<String, Object> map);

    public int updateIncitemName(CollectionsDetail collectionDetail);

    /**
     * 核收detail
     * @param collectionsDetail
     * @return
     */
    public List<CollectionsDetail> findAllListGroupByCollectId(CollectionsDetail collectionsDetail);

}
