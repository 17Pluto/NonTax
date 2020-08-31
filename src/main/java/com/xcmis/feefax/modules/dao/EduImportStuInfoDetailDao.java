package com.xcmis.feefax.modules.dao;

import com.xcmis.feefax.modules.entity.EduImportStuInfoDetail;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;

/**
 * @Author fangzhe
 * @Date 2020/6/22 3:13 下午
 * @Version 1.0
 */
@MyBatisDao
public interface EduImportStuInfoDetailDao extends IBaseDao<EduImportStuInfoDetail> {

    public List<EduImportStuInfoDetail> getEduStuInfoDetailList(Map<String, Object> map);

    public String getItemCode(EduImportStuInfoDetail eduImportStuInfoDetail);

    public String getItemChrId(EduImportStuInfoDetail eduImportStuInfoDetail);

    public int deleteTotalDetail(String mainId);

}
