package com.xcmis.feefax.modules.dao;

import com.xcmis.feefax.modules.entity.EduImportStuInfo;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;

/**
 * @Author fangzhe
 * @Date 2020/6/21 11:10 上午
 * @Version 1.0
 */
@MyBatisDao
public interface EduImportStuInfoDao extends IBaseDao<EduImportStuInfo> {

    public long updateIsCollection(String chrId);

    public List<EduImportStuInfo> getEduStuInfoListByCondition(Map<String, Object> map);

    public List<EduImportStuInfo> getEduStuInfoListByConditionForExport(Map<String, Object> map);

    public long getEduStuInfoListTotal(EduImportStuInfo eduImportStuInfo);

    public EduImportStuInfo getEduStuInfoByChrId(String chrId);

    public List<EduImportStuInfo> findAllGrade(EduImportStuInfo eduImportStuInfo);

    public List<EduImportStuInfo> findAllClass(EduImportStuInfo eduImportStuInfo);

    public int updateStuInfoBillNo(EduImportStuInfo eduImportStuInfo);

    public int updateStuPayflag(String chrId);

    public int updateCollectionsBillNo(EduImportStuInfo eduImportStuInfo);
}
