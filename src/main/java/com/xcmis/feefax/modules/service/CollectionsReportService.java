package com.xcmis.feefax.modules.service;

import com.xcmis.feefax.modules.dao.CollectionsReportDao;
import com.xcmis.feefax.modules.entity.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author fangzhe
 * @Date 2020/6/8 9:23 上午
 * @Version 1.0
 */
@Service
@Transactional(readOnly = true)
public class CollectionsReportService {

    @Autowired
    private CollectionsReportDao collectionsReportDao;

    public List<Collections> getCollectionsReportListByCondition(Map<String, Object> map){
        try{
            List<Collections> list = collectionsReportDao.getCollectionsReportListByCondition(map);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
