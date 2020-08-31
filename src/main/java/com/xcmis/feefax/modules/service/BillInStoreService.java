package com.xcmis.feefax.modules.service;
/**
 * @author fangzhe
 * @date 2020-03-26
 * BillInStoore service
 */
import com.xcmis.feefax.modules.dao.BillInStoreDao;
import com.xcmis.feefax.modules.dao.BillInStoreListDao;
import com.xcmis.feefax.modules.entity.BillInStore;
import com.xcmis.feefax.modules.entity.BillInStoreList;
import com.xcmis.feefax.modules.entity.IncomeItem;
import com.xcmis.framework.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class BillInStoreService {

    @Autowired
    private BillInStoreDao billInStoreDao;

    @Autowired
    private BillInStoreListDao billInStoreListDao;

    @Transactional(rollbackFor=Exception.class)
    public boolean insert(BillInStore billInStore) {
        int row = billInStoreDao.insert(billInStore);
        if(row > 0){
            String mainId = billInStore.getChrId();
            for(BillInStoreList bil : billInStore.getBillInStoreList()){
                bil.setMainId(mainId);
                bil.setOccurTime(billInStore.getLastVer());
                billInStoreListDao.insert(bil);
            }
            return true;
        }else{
            return false;
        }
    }


    @Transactional(rollbackFor=Exception.class)
    public boolean update(BillInStore billInStore){
        int row = billInStoreDao.update(billInStore);
        if(row > 0){
            String mainId = billInStore.getChrId();
            BillInStoreList billInStoreList = new BillInStoreList();
            billInStoreList.setMainId(mainId);
            billInStoreListDao.delete(billInStoreList);

            for(BillInStoreList bil : billInStore.getBillInStoreList()){
                bil.setMainId(mainId);
                bil.setOccurTime(billInStore.getLastVer());
                billInStoreListDao.insert(bil);
            }
            return true;
        }else{
            return false;
        }
    }

    @Transactional(rollbackFor=Exception.class)
    public boolean delete(BillInStore billInStore){
        int row = billInStoreDao.delete(billInStore);
        if(row > 0){
            String mainId = billInStore.getChrId();
            BillInStoreList billInStoreList = new BillInStoreList();
            billInStoreList.setMainId(mainId);
            billInStoreListDao.delete(billInStoreList);
            return true;
        }else{
            return false;
        }
    }


    @Transactional(rollbackFor=Exception.class)
    public boolean checkBillInStore(String chrIds, String stateCode){
        String[] chrIdArr = chrIds.split(",");
        stateCode = StringUtils.stateCodeStr(stateCode);
        int row = 0;
        for(String chrId : chrIdArr){
            BillInStore billInStore = new BillInStore();
            billInStore.setIsend(1);
            billInStore.setStateCode(stateCode);
            billInStore.setChrId(chrId);
            row += billInStoreDao.checkBillInStore(billInStore);
        }
        if(row > 0){
            return true;
        }else{
            return false;
        }
    }

    @Transactional(rollbackFor=Exception.class)
    public boolean returnBillInStore(String chrIds, String stateCode){
        String[] chrIdArr = chrIds.split(",");
        stateCode = StringUtils.returnStateCodeStr(stateCode);
        int row = 0;
        for(String chrId : chrIdArr){
            BillInStore billInStore = new BillInStore();
            billInStore.setIsend(0);
            billInStore.setStateCode(stateCode);
            billInStore.setChrId(chrId);
            row += billInStoreDao.checkBillInStore(billInStore);
        }
        if(row > 0){
            return true;
        }else{
            return false;
        }
    }

    public List<BillInStore> findAllList(BillInStore billInStore){
        try{
            List<BillInStore> list = billInStoreDao.findAllList(billInStore);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<BillInStore> findList(BillInStore billInStore){
        try{
            List<BillInStore> list = billInStoreDao.findList(billInStore);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public BillInStore get(BillInStore billInStore){
        try{
            BillInStore bt = billInStoreDao.get(billInStore);
            return bt;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public String getMaxNo(BillInStore billInStore){
        try{
            String billserialNo = billInStoreDao.getMaxNo(billInStore);
            if(billserialNo == null){
                return "";
            }
            return billserialNo;
        }catch(Exception e){
            e.printStackTrace();
            return "";
        }
    }

    public List<BillInStore> getBillInStoreListByCondition(Map<String, Object> map){
        try{
            List<BillInStore> list = billInStoreDao.getBillInStoreListByCondition(map);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public long getBillInStoreListTotal(BillInStore billInStore){
        try{
            long total = billInStoreDao.getBillInStoreListTotal(billInStore);
            return total;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }
}