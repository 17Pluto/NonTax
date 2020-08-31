package com.xcmis.feefax.modules.service;
/**
 * @author fangzhe
 * @date 2020-03-30
 *  service
 */
import com.xcmis.feefax.modules.dao.BillPutSaleDao;
import com.xcmis.feefax.modules.dao.BillPutSaleListDao;
import com.xcmis.feefax.modules.entity.BillPutSale;
import com.xcmis.feefax.modules.entity.BillPutSaleList;
import com.xcmis.framework.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class BillPutSaleService {
    @Autowired
    private BillPutSaleDao billPutSaleDao;

    @Autowired
    private BillPutSaleListDao billPutSaleListDao;

    @Transactional(rollbackFor=Exception.class)
    public boolean insert(BillPutSale billPutSale) {
        int row = billPutSaleDao.insert(billPutSale);
        if(row > 0){
            String mainId = billPutSale.getChrId();
            for(BillPutSaleList bpsl : billPutSale.getBillPutSaleList()){
                bpsl.setMainId(mainId);
                bpsl.setOccurTime(billPutSale.getLastVer());
                billPutSaleListDao.insert(bpsl);
            }
            return true;
        }else{
            return false;
        }
    }


    @Transactional(rollbackFor=Exception.class)
    public boolean update(BillPutSale billPutSale){
        int row = billPutSaleDao.update(billPutSale);
        if(row > 0){
            String mainId = billPutSale.getChrId();
            BillPutSaleList billPutSaleList = new BillPutSaleList();
            billPutSaleList.setMainId(mainId);
            billPutSaleListDao.delete(billPutSaleList);

            for(BillPutSaleList bpsl : billPutSale.getBillPutSaleList()){
                bpsl.setMainId(mainId);
                bpsl.setOccurTime(billPutSale.getLastVer());
                billPutSaleListDao.insert(bpsl);
            }
            return true;
        }else{
            return false;
        }
    }

    @Transactional(rollbackFor=Exception.class)
    public boolean delete(BillPutSale billPutSale){
        int row = billPutSaleDao.delete(billPutSale);
        if(row > 0){
            String mainId = billPutSale.getChrId();
            BillPutSaleList billPutSaleList = new BillPutSaleList();
            billPutSaleList.setMainId(mainId);
            billPutSaleListDao.delete(billPutSaleList);
            return true;
        }else{
            return false;
        }
    }

    public List<BillPutSale> findAllList(BillPutSale billPutSale){
        try{
            List<BillPutSale> list = billPutSaleDao.findAllList(billPutSale);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<BillPutSale> findList(BillPutSale billPutSale){
        try{
            List<BillPutSale> list = billPutSaleDao.findList(billPutSale);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public BillPutSale get(BillPutSale billPutSale){
        try{
            BillPutSale bt = billPutSaleDao.get(billPutSale);
            return bt;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<BillPutSale> getBillPutSaleListByCondition(Map<String, Object> map){
        try{
            List<BillPutSale> list = billPutSaleDao.getBillPutSaleListByCondition(map);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public long getBillPutSaleListTotal(BillPutSale billPutSale){
        try{
            long total = billPutSaleDao.getBillPutSaleListTotal(billPutSale);
            return total;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }


    public String getMaxNo(BillPutSale billPutSale){
        try{
            String billserialNo = billPutSaleDao.getMaxNo(billPutSale);
            if(billserialNo == null){
                return "";
            }
            return billserialNo;
        }catch(Exception e){
            e.printStackTrace();
            return "";
        }
    }


    @Transactional(rollbackFor=Exception.class)
    public boolean checkBillPutSale(String chrIds, String stateCode){
        String[] chrIdArr = chrIds.split(",");
        stateCode = StringUtils.stateCodeStr(stateCode);
        int row = 0;
        for(String chrId : chrIdArr){
            BillPutSale billPutSale = new BillPutSale();
            billPutSale.setIsend(1);
            billPutSale.setStateCode(stateCode);
            billPutSale.setChrId(chrId);
            row += billPutSaleDao.checkBillPutSale(billPutSale);
        }
        if(row > 0){
            return true;
        }else{
            return false;
        }
    }

    @Transactional(rollbackFor=Exception.class)
    public boolean returnBillPutSale(String chrIds, String stateCode){
        String[] chrIdArr = chrIds.split(",");
        stateCode = StringUtils.returnStateCodeStr(stateCode);
        int row = 0;
        for(String chrId : chrIdArr){
            BillPutSale billPutSale = new BillPutSale();
            billPutSale.setIsend(0);
            billPutSale.setStateCode(stateCode);
            billPutSale.setChrId(chrId);
            row += billPutSaleDao.checkBillPutSale(billPutSale);
        }
        if(row > 0){
            return true;
        }else{
            return false;
        }
    }
}