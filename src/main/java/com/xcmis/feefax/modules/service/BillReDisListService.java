package com.xcmis.feefax.modules.service;
/**
 * @author fangzhe
 * @date 2020-03-31
 * BillInStoore service
 */
import com.xcmis.feefax.modules.dao.BillDistributeListDao;
import com.xcmis.feefax.modules.dao.BillReDisListDao;
import com.xcmis.feefax.modules.entity.BillDistributeList;
import com.xcmis.feefax.modules.entity.BillReDisList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class BillReDisListService {

    @Autowired
    private BillReDisListDao billReDisListDao;

    @Transactional(readOnly = false)
    public boolean insert(BillReDisList billReDisList) {
        try {
            int row = billReDisListDao.insert(billReDisList);
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
    public String insertReturnId(BillReDisList billReDisList){
        try{
            int row = billReDisListDao.insert(billReDisList);
            if(row > 0){
                return billReDisList.getChrId();
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Transactional(readOnly = false)
    public boolean update(BillReDisList billReDisList){
        try{
            int row = billReDisListDao.update(billReDisList);
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
    public boolean delete(BillReDisList billReDisList){
        try{
            int row = billReDisListDao.delete(billReDisList);
            if(row > 0){
                return true;
            }
            return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<BillReDisList> findAllList(BillReDisList billReDisList){
        try{
            List<BillReDisList> list = billReDisListDao.findAllList(billReDisList);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<BillReDisList> findList(BillReDisList billReDisList){
        try{
            List<BillReDisList> list = billReDisListDao.findList(billReDisList);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public BillReDisList get(BillReDisList billReDisList){
        try{
            BillReDisList bt = billReDisListDao.get(billReDisList);
            return bt;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<BillReDisList> getBillReDisListListByCondition(Map<String, Object> map){
        try{
            List<BillReDisList> list = billReDisListDao.getBillReDisListListByCondition(map);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public long getBillReDisListListTotal(BillReDisList billReDisList){
        try{
            long total = billReDisListDao.getBillReDisListListTotal(billReDisList);
            return total;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }


    public List<BillReDisList> getUntaxBillnameIdListByBilldistributer(BillReDisList billReDisList){
        try{
            List<BillReDisList> list = billReDisListDao.getUntaxBillnameIdListByBilldistributer(billReDisList);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}