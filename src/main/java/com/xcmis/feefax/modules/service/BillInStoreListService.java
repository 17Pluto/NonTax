package com.xcmis.feefax.modules.service;
/**
 * @author fangzhe
 * @date 2020-03-29
 * BillInStooreList service
 */
import com.xcmis.feefax.modules.dao.BillInStoreListDao;
import com.xcmis.feefax.modules.entity.BillInStoreList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class BillInStoreListService {

    @Autowired
    private BillInStoreListDao billInStoreListDao;

    @Transactional(readOnly = false)
    public boolean insert(BillInStoreList billInStoreList) {
        try {
            int row = billInStoreListDao.insert(billInStoreList);
            if (row > 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Transactional(readOnly = false)
    public String insertReturnId(BillInStoreList billInStoreList){
        try{
            int row = billInStoreListDao.insert(billInStoreList);
            if(row > 0){
                return billInStoreList.getChrId();
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Transactional(readOnly = false)
    public boolean update(BillInStoreList billInStoreList){
        try{
            int row = billInStoreListDao.update(billInStoreList);
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
    public boolean delete(BillInStoreList billInStoreList){
        try{
            int row = billInStoreListDao.delete(billInStoreList);
            if(row > 0){
                return true;
            }
            return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<BillInStoreList> findAllList(BillInStoreList billInStoreList){
        try{
            List<BillInStoreList> list = billInStoreListDao.findAllList(billInStoreList);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<BillInStoreList> findList(BillInStoreList billInStoreList){
        try{
            List<BillInStoreList> list = billInStoreListDao.findList(billInStoreList);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public BillInStoreList get(BillInStoreList billInStoreList){
        try{
            BillInStoreList bt = billInStoreListDao.get(billInStoreList);
            return bt;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<BillInStoreList> getBillInStoreListListByCondition(Map<String, Object> map){
        try{
            List<BillInStoreList> list = billInStoreListDao.getBillInStoreListListByCondition(map);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public long getBillInStoreListListTotal(BillInStoreList billInStoreList){
        try{
            long total = billInStoreListDao.getBillInStoreListListTotal(billInStoreList);
            return total;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public List<BillInStoreList> getBillInStoreListByUntaxBillnameId(BillInStoreList billInStoreList){
        try{
            List<BillInStoreList> list = billInStoreListDao.getBillInStoreListByUntaxBillnameId(billInStoreList);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}