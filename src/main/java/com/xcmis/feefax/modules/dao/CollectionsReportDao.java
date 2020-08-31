package com.xcmis.feefax.modules.dao;

import com.xcmis.feefax.modules.entity.Collections;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;

/**
 * @Author fangzhe
 * @Date 2020/6/8 9:26 上午
 * @Version 1.0
 */
@MyBatisDao
public interface CollectionsReportDao extends IBaseDao<Collections> {
    public List<Collections> getCollectionsReportListByCondition(Map<String, Object> map);
}
