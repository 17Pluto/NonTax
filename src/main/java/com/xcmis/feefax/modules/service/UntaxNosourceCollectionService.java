package com.xcmis.feefax.modules.service;
/**
 * @author fangzhe
 * @date 2020-05-18
 *
 */

import com.xcmis.feefax.modules.dao.UntaxNosourceCollectionDao;
import com.xcmis.feefax.modules.entity.UntaxNosourceCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

;

@Service
@Transactional(readOnly = true)
public class UntaxNosourceCollectionService {

    @Autowired
    private UntaxNosourceCollectionDao untaxNosourceCollectionDao;

    //增
    @Transactional(readOnly = false)
    public boolean insert(UntaxNosourceCollection untaxNosourceCollection) {
        try {
            int row = untaxNosourceCollectionDao.insert(untaxNosourceCollection);
            if (row > 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

//    @Transactional(readOnly = false)
//    public String insertReturnId(UntaxNosource untaxNosource){
//        try{
//            int row = untaxNosourceDao.insert(untaxNosource);
//            if(row > 0){
//                return untaxNosource.getChrId();
//            }
//            return "";
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "";
//        }
//    }

    //改
    @Transactional(readOnly = false)
    public boolean update(UntaxNosourceCollection untaxNosourceCollection){
        try{
            int row = untaxNosourceCollectionDao.update(untaxNosourceCollection);
            if(row > 0){
                return true;
            }
            return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    //删
    @Transactional(readOnly = false)
    public boolean delete(UntaxNosourceCollection untaxNosourceCollection){
        try{
            int row = untaxNosourceCollectionDao.delete(untaxNosourceCollection);
            if(row > 0){
                return true;
            }
            return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

//    public UntaxNosource get(UntaxNosource untaxNosource){
//        try{
//            UntaxNosource bt = untaxNosourceDao.get(untaxNosource);
//            return bt;
//        }catch(Exception e){
//            e.printStackTrace();
//            return null;
//        }
//    }

    //查
    public List<UntaxNosourceCollection> findAllList(UntaxNosourceCollection untaxNosourceCollection){
        try{
            List<UntaxNosourceCollection> list = untaxNosourceCollectionDao.findAllList(untaxNosourceCollection);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

//    public List<UntaxNosource> findList(UntaxNosource untaxNosource){
//        try{
//            List<UntaxNosource> list = untaxNosourceDao.findList(untaxNosource);
//            return list;
//        }catch(Exception e){
//            e.printStackTrace();
//            return null;
//        }
//    }

    //查
    public List<UntaxNosourceCollection> getUntaxNosourceCollectionListByCondition(Map<String, Object> map){
        try{
            List<UntaxNosourceCollection> list = untaxNosourceCollectionDao.getUntaxNosourceCollectionListByCondition(map);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //查
    public long getUntaxNosourceCollectionListTotal(UntaxNosourceCollection untaxNosourceCollection){
        try{
            long total = untaxNosourceCollectionDao.getUntaxNosourceCollectionListTotal(untaxNosourceCollection);
            return total;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }
}