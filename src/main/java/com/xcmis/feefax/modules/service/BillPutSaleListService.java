package com.xcmis.feefax.modules.service;
/**
 * @author fangzhe
 * @date 2020-03-31
 *  service
 */
import com.xcmis.feefax.modules.dao.BillPutSaleDao;
import com.xcmis.feefax.modules.dao.BillPutSaleListDao;
import com.xcmis.feefax.modules.entity.BillPutSale;
import com.xcmis.feefax.modules.entity.BillPutSaleList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class BillPutSaleListService {

    @Autowired
    private BillPutSaleListDao billPutSaleListDao;

    @Transactional(readOnly = false)
    public boolean insert(BillPutSaleList billPutSaleList) {
        try {
            int row = billPutSaleListDao.insert(billPutSaleList);
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
    public String insertReturnId(BillPutSaleList billPutSaleList){
        try{
            int row = billPutSaleListDao.insert(billPutSaleList);
            if(row > 0){
                return billPutSaleList.getChrId();
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Transactional(readOnly = false)
    public boolean update(BillPutSaleList billPutSaleList){
        try{
            int row = billPutSaleListDao.update(billPutSaleList);
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
    public boolean delete(BillPutSaleList billPutSaleList){
        try{
            int row = billPutSaleListDao.delete(billPutSaleList);
            if(row > 0){
                return true;
            }
            return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<BillPutSaleList> findAllList(BillPutSaleList billPutSaleList){
        try{
            List<BillPutSaleList> list = billPutSaleListDao.findAllList(billPutSaleList);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<BillPutSaleList> findList(BillPutSaleList billPutSaleList){
        try{
            List<BillPutSaleList> list = billPutSaleListDao.findList(billPutSaleList);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public BillPutSaleList get(BillPutSaleList billPutSaleList){
        try{
            BillPutSaleList bt = billPutSaleListDao.get(billPutSaleList);
            return bt;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<BillPutSaleList> getBillPutSaleListListByCondition(Map<String, Object> map){
        try{
            List<BillPutSaleList> list = billPutSaleListDao.getBillPutSaleListListByCondition(map);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public long getBillPutSaleListListTotal(BillPutSaleList billPutSaleList){
        try{
            long total = billPutSaleListDao.getBillPutSaleListListTotal(billPutSaleList);
            return total;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public List<BillPutSaleList> getBillPutSaleListByUntaxBillnameId(BillPutSaleList billPutSaleList){
        try{
            List<BillPutSaleList> list = billPutSaleListDao.getBillPutSaleListByUntaxBillnameId(billPutSaleList);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}