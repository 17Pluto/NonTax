package com.xcmis.feefax.modules.service;
/**
 * @author fangzhe
 * @date 2020-03-30
 *  service
 */

import com.xcmis.feefax.modules.dao.BillDetailDao;
import com.xcmis.feefax.modules.dao.BillNameDao;
import com.xcmis.feefax.modules.dao.BillRecoveryDao;
import com.xcmis.feefax.modules.dao.BillRecoveryListDao;
import com.xcmis.feefax.modules.entity.BillDetail;
import com.xcmis.feefax.modules.entity.BillName;
import com.xcmis.feefax.modules.entity.BillRecovery;
import com.xcmis.feefax.modules.entity.BillRecoveryList;
import com.xcmis.framework.common.utils.DateTimeUtils;
import com.xcmis.framework.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class BillRecoveryService {
    @Autowired
    private BillRecoveryDao billRecoveryDao;

    @Autowired
    private BillRecoveryListDao billRecoveryListDao;

    @Autowired
    private BillNameDao billNameDao;

    @Autowired
    private BillDetailDao billDetailDao;

    @Transactional(rollbackFor=Exception.class)
    public boolean insert(BillRecovery billRecovery) {
        int row = billRecoveryDao.insert(billRecovery);
        if(row > 0){
            String mainId = billRecovery.getChrId();
            for(BillRecoveryList brl : billRecovery.getBillRecoveryList()){
                brl.setMainId(mainId);
                brl.setOccurTime(billRecovery.getLastVer());
                billRecoveryListDao.insert(brl);

                String strFormat = "";

                BillName billName = new BillName();
                billName.setChrId(brl.getUntaxBillnameId());
                billName = billNameDao.get(billName);

                int billLength = billName.getBillLength();
                for(int k = 0; k < billLength; k++){
                    strFormat += "0";
                }

                DecimalFormat decimalFormat = new DecimalFormat(strFormat);

                long bgnBillNo1 = Long.parseLong(brl.getBgnBillNo());
                long endBillNo1 = Long.parseLong(brl.getEndBillNo());

                for(long i = bgnBillNo1; i <= endBillNo1; i++){
                    BillDetail billDetail = new BillDetail();
                    billDetail.setUntaxBillnameId(brl.getUntaxBillnameId());
                    billDetail.setBillNo(decimalFormat.format(i));
                    billDetail = billDetailDao.get(billDetail);
                    if(billDetail != null) {
                        billDetailDao.delete(billDetail);
                    }
                }
            }


            return true;
        }else{
            return false;
        }
    }


    @Transactional(rollbackFor=Exception.class)
    public boolean update(BillRecovery billRecovery){
        int row = billRecoveryDao.update(billRecovery);
        if(row > 0){
            String mainId = billRecovery.getChrId();
            BillRecoveryList billRecoveryList = new BillRecoveryList();
            billRecoveryList.setMainId(mainId);
            billRecoveryListDao.delete(billRecoveryList);

            for(BillRecoveryList brl : billRecovery.getBillRecoveryList()){
                brl.setMainId(mainId);
                brl.setOccurTime(billRecovery.getLastVer());
                billRecoveryListDao.insert(brl);
            }
            return true;
        }else{
            return false;
        }
    }

    @Transactional(rollbackFor=Exception.class)
    public boolean delete(BillRecovery billRecovery){
        int row = billRecoveryDao.delete(billRecovery);
        if(row > 0){
            String mainId = billRecovery.getChrId();
            BillRecoveryList billRecoveryList = new BillRecoveryList();
            billRecoveryList.setMainId(mainId);
            billRecoveryListDao.delete(billRecoveryList);
            return true;
        }else{
            return false;
        }
    }

    public List<BillRecovery> findAllList(BillRecovery billRecovery){
        try{
            List<BillRecovery> list = billRecoveryDao.findAllList(billRecovery);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<BillRecovery> findList(BillRecovery billRecovery){
        try{
            List<BillRecovery> list = billRecoveryDao.findList(billRecovery);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public BillRecovery get(BillRecovery billRecovery){
        try{
            BillRecovery bt = billRecoveryDao.get(billRecovery);
            return bt;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<BillRecovery> getBillRecoveryListByCondition(Map<String, Object> map){
        try{
            List<BillRecovery> list = billRecoveryDao.getBillRecoveryListByCondition(map);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public long getBillRecoveryListTotal(BillRecovery billRecovery){
        try{
            long total = billRecoveryDao.getBillRecoveryListTotal(billRecovery);
            return total;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public String getMaxNo(BillRecovery billRecovery){
        try{
            String billserialNo = billRecoveryDao.getMaxNo(billRecovery);
            if(billserialNo == null){
                return "";
            }
            return billserialNo;
        }catch(Exception e){
            e.printStackTrace();
            return "";
        }
    }
}