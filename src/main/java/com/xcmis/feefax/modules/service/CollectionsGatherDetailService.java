package com.xcmis.feefax.modules.service;
/**
 * @author fangzhe
 * @date 2020-04-26
 *  service
 */
import com.xcmis.feefax.modules.dao.CollectionsGatherDetailDao;
import com.xcmis.feefax.modules.entity.CollectionsGatherDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class CollectionsGatherDetailService {

    @Autowired
    private CollectionsGatherDetailDao collectionsGatherDetailDao;

    @Transactional(readOnly = false)
    public boolean insert(CollectionsGatherDetail collectionsGatherDetail) {
        int row = collectionsGatherDetailDao.insert(collectionsGatherDetail);
        if (row > 0) {
            String chrId = collectionsGatherDetail.getChrId();

            return true;
        }else{
            return false;
        }

    }

    @Transactional(readOnly = false)
    public String insertReturnId(CollectionsGatherDetail collectionsGatherDetail){
        try{
            int row = collectionsGatherDetailDao.insert(collectionsGatherDetail);
            if(row > 0){
                return collectionsGatherDetail.getChrId();
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Transactional(readOnly = false)
    public boolean update(CollectionsGatherDetail collectionsGatherDetail){
        try{
            int row = collectionsGatherDetailDao.update(collectionsGatherDetail);
            if(row > 0){
                return true;
            }
            return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Transactional(readOnly = false)
    public boolean delete(CollectionsGatherDetail collectionsGatherDetail){
        try{
            int row = collectionsGatherDetailDao.delete(collectionsGatherDetail);
            if(row > 0){
                return true;
            }
            return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<CollectionsGatherDetail> findAllList(CollectionsGatherDetail collectionsGatherDetail){
        try{
            List<CollectionsGatherDetail> list = collectionsGatherDetailDao.findAllList(collectionsGatherDetail);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<CollectionsGatherDetail> findAllListGroupByCollectId(CollectionsGatherDetail collectionsGatherDetail){
        try{
            List<CollectionsGatherDetail> list = collectionsGatherDetailDao.findAllListGroupByCollectId(collectionsGatherDetail);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<CollectionsGatherDetail> findList(CollectionsGatherDetail collectionsGatherDetail){
        try{
            List<CollectionsGatherDetail> list = collectionsGatherDetailDao.findList(collectionsGatherDetail);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public CollectionsGatherDetail get(CollectionsGatherDetail collectionsGatherDetail){
        try{
            CollectionsGatherDetail bt = collectionsGatherDetailDao.get(collectionsGatherDetail);
            return bt;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<CollectionsGatherDetail> getCollectionsGatherDetailListByCondition(Map<String, Object> map){
        try{
            List<CollectionsGatherDetail> list = collectionsGatherDetailDao.getCollectionsGatherDetailListByCondition(map);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public long getCollectionsGatherDetailListTotal(CollectionsGatherDetail collectionsGatherDetail){
        try{
            long total = collectionsGatherDetailDao.getCollectionsGatherDetailListTotal(collectionsGatherDetail);
            return total;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }
}