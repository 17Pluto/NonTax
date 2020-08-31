package com.xcmis.feefax.modules.service;
/**
 * @author fangzhe
 * @date 2020-03-26
 * BillInStoore service
 */
import com.xcmis.feefax.modules.dao.BillDetailDao;
import com.xcmis.feefax.modules.entity.BillDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class BillDetailService {

    @Autowired
    private BillDetailDao billDetailDao;

    @Transactional(readOnly = false)
    public boolean insert(BillDetail billDetail) {
        try {
            int row = billDetailDao.insert(billDetail);
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
    public String insertReturnId(BillDetail billDetail){
        try{
            int row = billDetailDao.insert(billDetail);
            if(row > 0){
                return billDetail.getChrId();
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Transactional(readOnly = false)
    public boolean update(BillDetail billDetail){
        try{
            int row = billDetailDao.update(billDetail);
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
    public boolean delete(BillDetail billDetail){
        try{
            int row = billDetailDao.delete(billDetail);
            if(row > 0){
                return true;
            }
            return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<BillDetail> findAllList(BillDetail billDetail){
        try{
            List<BillDetail> list = billDetailDao.findAllList(billDetail);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<BillDetail> findList(BillDetail billDetail){
        try{
            List<BillDetail> list = billDetailDao.findList(billDetail);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<BillDetail> findListByEnIdAndBilltypeId(BillDetail billDetail){
        try{
            List<BillDetail> list = billDetailDao.findListByCondition(billDetail);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }


    public List<BillDetail> findUntaxBill(BillDetail billDetail){
        try{
            List<BillDetail> list = billDetailDao.findUntaxBill(billDetail);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<BillDetail> findListByEnIdAndBilltypeIdAndbillDistributer(BillDetail billDetail){
        try{
            List<BillDetail> list = billDetailDao.findListByCondition(billDetail);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public BillDetail get(BillDetail billDetail){
        try{
            BillDetail bt = billDetailDao.get(billDetail);
            return bt;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public BillDetail getBillDetailMinNo(BillDetail billDetail){
        try{
            BillDetail bt = billDetailDao.getBillDetailMinNo(billDetail);
            return bt;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }


    public BillDetail isvalidBillNo(BillDetail billDetail){
        try{
            BillDetail bt = billDetailDao.isvalidBillNo(billDetail);
            return bt;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }


}