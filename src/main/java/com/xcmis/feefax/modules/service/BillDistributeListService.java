package com.xcmis.feefax.modules.service;
/**
 * @author fangzhe
 * @date 2020-03-31
 * BillInStoore service
 */
import com.xcmis.feefax.modules.dao.BillDistributeDao;
import com.xcmis.feefax.modules.dao.BillDistributeListDao;
import com.xcmis.feefax.modules.entity.BillDistribute;
import com.xcmis.feefax.modules.entity.BillDistributeList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class BillDistributeListService {

    @Autowired
    private BillDistributeListDao billDistributeListDao;

    @Transactional(readOnly = false)
    public boolean insert(BillDistributeList billDistributeList) {
        try {
            int row = billDistributeListDao.insert(billDistributeList);
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
    public String insertReturnId(BillDistributeList billDistributeList){
        try{
            int row = billDistributeListDao.insert(billDistributeList);
            if(row > 0){
                return billDistributeList.getChrId();
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Transactional(readOnly = false)
    public boolean update(BillDistributeList billDistributeList){
        try{
            int row = billDistributeListDao.update(billDistributeList);
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
    public boolean delete(BillDistributeList billDistributeList){
        try{
            int row = billDistributeListDao.delete(billDistributeList);
            if(row > 0){
                return true;
            }
            return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<BillDistributeList> findAllList(BillDistributeList billDistributeList){
        try{
            List<BillDistributeList> list = billDistributeListDao.findAllList(billDistributeList);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<BillDistributeList> findList(BillDistributeList billDistributeList){
        try{
            List<BillDistributeList> list = billDistributeListDao.findList(billDistributeList);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public BillDistributeList get(BillDistributeList billDistributeList){
        try{
            BillDistributeList bt = billDistributeListDao.get(billDistributeList);
            return bt;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<BillDistributeList> getBillDistributeListListByCondition(Map<String, Object> map){
        try{
            List<BillDistributeList> list = billDistributeListDao.getBillDistributeListListByCondition(map);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public long getBillDistributeListListTotal(BillDistributeList billDistribute){
        try{
            long total = billDistributeListDao.getBillDistributeListListTotal(billDistribute);
            return total;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }


    public List<BillDistributeList> getUntaxBillnameIdListByBilldistributer(BillDistributeList billDistribute){
        try{
            List<BillDistributeList> list = billDistributeListDao.getUntaxBillnameIdListByBilldistributer(billDistribute);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}