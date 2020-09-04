package com.xcmis.feefax.modules.dao;

/**
 * @author fangzhe
 * @date 2020-04-01
 *  dao
 */
import com.xcmis.feefax.modules.entity.Collections;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface CollectionsDao extends IBaseDao<Collections> {

    public long getCollectionsListTotal(Collections collections);

    public List<Collections> getCollectionsListByCondition(Map<String, Object> map);

    public int cancelCollections(Collections collections);

    public Collections getCollectionsForPrint(Collections collections);

    public Collections getStuCollectionsForPrint(Collections collections);

    public int updatePrintflag(Collections collections);

    public int updateRedRushFlag(Collections collections);

    public int updatePayflag(Collections collections);

    public int updateLateflag(Collections collections);

    public int updateCheckflag(Collections collections);

    public String getMaxReqBillNo(Collections collections);

    public int insertForGather(Collections collections);

    public int updateIsSend(Collections collections);


    /**
     * 核收数据管理api
     */
    public List<Collections> getCollectionsByCondition(Map<String, Object> map);

    public long getCollectionsTotal(Map<String, Object> map);

    /**
     * 更新核收状态和核收时间
     * @param collections
     * @return
     */
    public int updateCheckEarne(Collections collections);


    public long getCollectionsForPayTotal(Collections collections);

    public List<Collections> getCollectionsForPay(Map<String, Object> map);


    public List<Collections> testFindAllList(Collections collections);
}
