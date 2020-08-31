package com.xcmis.feefax.modules.dao;

import com.xcmis.feefax.modules.entity.EduBatchManagement;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;

/**
 * @Author fangzhe
 * @Date 2020/6/18 1:48 下午
 * @Version 1.0
 */
@MyBatisDao
public interface EduBatchManagementDao extends IBaseDao<EduBatchManagement> {

    public List<EduBatchManagement> getEduBatchManagementListByCondition(Map<String, Object> map);

    public List<EduBatchManagement> getEduBatchManagement(Map<String, Object> map);

    public long getEduBatchManagementListTotal(EduBatchManagement eduBatchManagement);

    public int deleteBatchDo(EduBatchManagement eduBatchManagement);

    public int editEnableStatus(String chrId);

    public List<EduBatchManagement> checkEnableStatus(Map<String, Object> map);

    public int updateEnable(EduBatchManagement eduBatchManagement);

    public long checkBatchIsUsed(EduBatchManagement eduBatchManagement);
}
