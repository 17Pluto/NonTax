package com.xcmis.feefax.modules.service;
/**
 * @author fangzhe
 * @date 2020-03-31
 *  service
 */

import com.xcmis.feefax.modules.dao.BillRecoveryListDao;
import com.xcmis.feefax.modules.entity.BillRecoveryList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class BillRecoveryListService {

    @Autowired
    private BillRecoveryListDao billRecoveryListDao;

    @Transactional(readOnly = false)
    public boolean insert(BillRecoveryList billRecoveryList) {
        try {
            int row = billRecoveryListDao.insert(billRecoveryList);
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
    public String insertReturnId(BillRecoveryList billRecoveryList){
        try{
            int row = billRecoveryListDao.insert(billRecoveryList);
            if(row > 0){
                return billRecoveryList.getChrId();
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Transactional(readOnly = false)
    public boolean update(BillRecoveryList billRecoveryList){
        try{
            int row = billRecoveryListDao.update(billRecoveryList);
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
    public boolean delete(BillRecoveryList billRecoveryList){
        try{
            int row = billRecoveryListDao.delete(billRecoveryList);
            if(row > 0){
                return true;
            }
            return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<BillRecoveryList> findAllList(BillRecoveryList billRecoveryList){
        try{
            List<BillRecoveryList> list = billRecoveryListDao.findAllList(billRecoveryList);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<BillRecoveryList> findList(BillRecoveryList billRecoveryList){
        try{
            List<BillRecoveryList> list = billRecoveryListDao.findList(billRecoveryList);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public BillRecoveryList get(BillRecoveryList billRecoveryList){
        try{
            BillRecoveryList bt = billRecoveryListDao.get(billRecoveryList);
            return bt;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<BillRecoveryList> getBillRecoveryListListByCondition(Map<String, Object> map){
        try{
            List<BillRecoveryList> list = billRecoveryListDao.getBillRecoveryListListByCondition(map);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public long getBillRecoveryListListTotal(BillRecoveryList billRecoveryList){
        try{
            long total = billRecoveryListDao.getBillRecoveryListListTotal(billRecoveryList);
            return total;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public List<BillRecoveryList> getBillRecoveryListByUntaxBillnameId(BillRecoveryList billRecoveryList){
        try{
            List<BillRecoveryList> list = billRecoveryListDao.getBillRecoveryListByUntaxBillnameId(billRecoveryList);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}