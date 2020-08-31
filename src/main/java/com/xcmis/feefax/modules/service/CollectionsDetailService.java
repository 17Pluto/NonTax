package com.xcmis.feefax.modules.service;
/**
 * @author fangzhe
 * @date 2020-04-01
 * BillInStoore service
 */
import com.xcmis.feefax.modules.dao.CollectionsDetailDao;
import com.xcmis.feefax.modules.entity.CollectionsDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class CollectionsDetailService {

    @Autowired
    private CollectionsDetailDao collectionsDetailDao;

    @Transactional(readOnly = false)
    public boolean insert(CollectionsDetail collectionsDetail) {
        int row = collectionsDetailDao.insert(collectionsDetail);
        if (row > 0) {
            String chrId = collectionsDetail.getChrId();

            return true;
        }else{
            return false;
        }

    }

    @Transactional(readOnly = false)
    public String insertReturnId(CollectionsDetail collectionsDetail){
        try{
            int row = collectionsDetailDao.insert(collectionsDetail);
            if(row > 0){
                return collectionsDetail.getChrId();
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Transactional(readOnly = false)
    public boolean update(CollectionsDetail collectionsDetail){
        try{
            int row = collectionsDetailDao.update(collectionsDetail);
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
    public boolean delete(CollectionsDetail collectionsDetail){
        try{
            int row = collectionsDetailDao.delete(collectionsDetail);
            if(row > 0){
                return true;
            }
            return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<CollectionsDetail> findAllList(CollectionsDetail collectionsDetail){
        try{
            List<CollectionsDetail> list = collectionsDetailDao.findAllList(collectionsDetail);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<CollectionsDetail> findList(CollectionsDetail collectionsDetail){
        try{
            List<CollectionsDetail> list = collectionsDetailDao.findList(collectionsDetail);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public CollectionsDetail get(CollectionsDetail collectionsDetail){
        try{
            CollectionsDetail bt = collectionsDetailDao.get(collectionsDetail);
            return bt;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<CollectionsDetail> getCollectionsDetailListByCondition(Map<String, Object> map){
        try{
            List<CollectionsDetail> list = collectionsDetailDao.getCollectionsDetailListByCondition(map);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public long getCollectionsDetailListTotal(CollectionsDetail collectionsDetail){
        try{
            long total = collectionsDetailDao.getCollectionsDetailListTotal(collectionsDetail);
            return total;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    /**
     *
     * @param collectionsDetail
     * @return
     */
    public List<CollectionsDetail> findAllListGroupByCollectId(CollectionsDetail collectionsDetail){
        try{
            List<CollectionsDetail> list = collectionsDetailDao.findAllListGroupByCollectId(collectionsDetail);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}