package com.xcmis.feefax.modules.service;
/**
 * @author 方哲
 * @date 2020-04-26
 * service
 */
import com.xcmis.feefax.modules.dao.BillDetailDao;
import com.xcmis.feefax.modules.dao.CollectionsDao;
import com.xcmis.feefax.modules.dao.CollectionsGatherDao;
import com.xcmis.feefax.modules.dao.CollectionsGatherDetailDao;
import com.xcmis.feefax.modules.entity.*;
import com.xcmis.framework.common.utils.DateTimeUtils;
import com.xcmis.framework.common.utils.StringUtils;
import com.xcmis.framework.common.utils.UserUtils;
import com.xcmis.framework.modules.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class CollectionsGatherService {

    @Autowired
    private CollectionsGatherDao collectionsGatherDao;

    @Autowired
    private CollectionsDao collectionsDao;

    @Autowired
    private CollectionsService collectionsService;

    @Autowired
    private CollectionsGatherDetailDao collectionsGatherDetailDao;

    @Autowired
    private BillDetailDao billDetailDao;

    @Autowired
    private RegionService regionService;

    @Transactional(rollbackFor=Exception.class)
    public boolean insert(CollectionsGather collectionsGather) {
        int row = collectionsGatherDao.insert(collectionsGather);
        if (row > 0) {
            for(CollectionsGatherDetail collectionsGatherDetail : collectionsGather.getCollectionsGatherDetailList()){
                collectionsGatherDetail.setMainId(collectionsGather.getChrId());
                collectionsGatherDetail.setLastVer(DateTimeUtils.getDateTimeStr1());
                collectionsGatherDetailDao.insert(collectionsGatherDetail);
            }

            BillDetail billDetail = new BillDetail();
            billDetail.setBillNo(collectionsGather.getBillNo());
            billDetail.setUntaxBillnameId(collectionsGather.getBilltypeId());
            billDetail.setState("1");
            billDetailDao.update(billDetail);
            return true;
        }else{
            return false;
        }
    }

    @Transactional(rollbackFor=Exception.class)
    public boolean updateReturnflag(CollectionsGather collectionsGather){
        int row = collectionsGatherDao.updateReturnflag(collectionsGather);
        if(row > 0){
            return true;
        }else{
            return false;
        }
    }


    @Transactional(rollbackFor=Exception.class)
    public boolean update(CollectionsGather collectionsGather){
        int row = collectionsGatherDao.update(collectionsGather);
        if(row > 0){
            String chrId = collectionsGather.getChrId();

            CollectionsGatherDetail collectionsGatherDetail = new CollectionsGatherDetail();
            collectionsGatherDetail.setMainId(chrId);
            collectionsGatherDetailDao.delete(collectionsGatherDetail);

            for(CollectionsGatherDetail cd : collectionsGather.getCollectionsGatherDetailList()){
                cd.setMainId(chrId);
                cd.setLastVer(DateTimeUtils.getDateTimeStr1());
                collectionsGatherDetailDao.insert(cd);
            }

            return true;
        }else{
            return false;
        }
    }

    @Transactional(rollbackFor=Exception.class)
    public boolean cancelCollectionsGather( String chrIds){
        String[] chrIdArr = chrIds.split(",");
        for(String chrId : chrIdArr){
            CollectionsGather collectionsGather = new CollectionsGather();
            collectionsGather.setChrId(chrId);
            collectionsGather.setErasedate(DateTimeUtils.getDateTimeStr1());
            collectionsGather.setEraseflag(1);
            collectionsGatherDao.cancelCollectionsGather(collectionsGather);
            //collectionsGather = collectionsGatherDao.get(collectionsGather);
        }
        return true;
    }

    @Transactional(rollbackFor=Exception.class)
    public boolean delete(CollectionsGather collectionsGather){
        collectionsGather = collectionsGatherDao.get(collectionsGather);
        int row = collectionsGatherDao.delete(collectionsGather);
        if(row > 0){
            String chrId = collectionsGather.getChrId();

            CollectionsGatherDetail collectionsGatherDetail = new CollectionsGatherDetail();
            collectionsGatherDetail.setMainId(chrId);
            collectionsGatherDetailDao.delete(collectionsGatherDetail);

            if(collectionsGather.getBillNo() != null){
                if(!collectionsGather.getBillNo().equals("")) {
                    BillDetail billDetail = new BillDetail();
                    billDetail.setUntaxBillnameId(collectionsGather.getBilltypeId());
                    billDetail.setState("0");
                    billDetail.setBillNo(collectionsGather.getBillNo());
                    billDetailDao.update(billDetail);
                }
            }
            return true;
        }
        return false;
    }

    public List<CollectionsGather> findAllList(CollectionsGather collectionsGather){
        try{
            List<CollectionsGather> list = collectionsGatherDao.findAllList(collectionsGather);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<CollectionsGather> findList(CollectionsGather collectionsGather){
        try{
            List<CollectionsGather> list = collectionsGatherDao.findList(collectionsGather);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public CollectionsGather get(CollectionsGather collectionsGather){
        try{
            CollectionsGather bt = collectionsGatherDao.get(collectionsGather);
            return bt;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<CollectionsGather> getCollectionsGatherListByCondition(Map<String, Object> map){
        try{
            List<CollectionsGather> list = collectionsGatherDao.getCollectionsGatherListByCondition(map);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public long getCollectionsGatherListTotal(CollectionsGather collectionsGather){
        try{
            long total = collectionsGatherDao.getCollectionsGatherListTotal(collectionsGather);
            return total;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }


    public List<CollectionsGather> getCollectionsGatherListByConditionNew(Map<String, Object> map){
        try{
            List<CollectionsGather> list = collectionsGatherDao.getCollectionsGatherListByConditionNew(map);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public long getCollectionsGatherListTotalNew(CollectionsGather collectionsGather){
        try{
            long total = collectionsGatherDao.getCollectionsGatherListTotalNew(collectionsGather);
            return total;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Transactional(rollbackFor=Exception.class)
    public boolean updatePrintflag(String chrIds){
        String[] chrIdArr = chrIds.split(",");
        for(String chrId : chrIdArr){
            String collectId = "";
            CollectionsGather collectionsGather = new CollectionsGather();
            collectionsGather.setChrId(chrId);
            CollectionsGather cg = collectionsGatherDao.get(collectionsGather);

            /*
            if(collectionsGather.getCollectflag() == 0){
                Collections collections = new Collections(cg);
                collections.setSetYear(DateTimeUtils.getCurrentYear());
                collections.setCreateDate(DateTimeUtils.getDateTimeStr1());
                collections.setCreateUser(UserUtils.getUserId());
                collections.setLastestOpDate(DateTimeUtils.getDateTimeStr1());
                collections.setLastestOpUser(UserUtils.getUserId());
                collections.setLastVer(DateTimeUtils.getDateTimeStr1());
                collections.setRgCode(regionService.get(null).getChrCode());
                collections.setReceivetype(2);

                String maxReqBillNo = collectionsDao.getMaxReqBillNo(collections);
                collections.setReqBillNo(StringUtils.getReqBillNo(maxReqBillNo, DateTimeUtils.getCurrentYear(), regionService.get(null).getChrCode()));
                collections.setPrintdate(DateTimeUtils.getDateTimeStr1());
                collections.setPrintflag(1);

                int row = collectionsDao.insertForGather(collections);
                collectId = collections.getChrId();
            }
            */

            cg.setPrintdate(DateTimeUtils.getDateTimeStr1());
            cg.setPrintflag(1);
            cg.setBillNo(collectionsGather.getBillNo());
            //cg.setCollectId(collectId);
            //cg.setCollectdate(DateTimeUtils.getDateTimeStr1());
            //cg.setCollectflag(1);
            int row = collectionsGatherDao.updatePrintflag(cg);

            if(row > 0) {
                CollectionsGatherDetail collectionsGatherDetail = new CollectionsGatherDetail();
                collectionsGatherDetail.setMainId(collectionsGather.getChrId());
                List<CollectionsGatherDetail> collectionsGatherDetailList = collectionsGatherDetailDao.findAllList(collectionsGatherDetail);
                for (CollectionsGatherDetail cgd : collectionsGatherDetailList) {
                    collectionsGatherDetailDao.updateIncitemName(cgd);
                }
            }
        }
        return true;
    }




    @Transactional(rollbackFor=Exception.class)
    public boolean updatePrintflagForOne(CollectionsGather collectionsGather){
        String chrId = "";
        CollectionsGather cg = collectionsGatherDao.get(collectionsGather);

        /*
        if(collectionsGather.getCollectflag() == 0){
            Collections collections = new Collections(cg);
            collections.setSetYear(DateTimeUtils.getCurrentYear());
            collections.setCreateDate(DateTimeUtils.getDateTimeStr1());
            collections.setCreateUser(UserUtils.getUserId());
            collections.setLastestOpDate(DateTimeUtils.getDateTimeStr1());
            collections.setLastestOpUser(UserUtils.getUserId());
            collections.setLastVer(DateTimeUtils.getDateTimeStr1());
            collections.setRgCode(regionService.get(null).getChrCode());
            collections.setReceivetype(2);

            String maxReqBillNo = collectionsDao.getMaxReqBillNo(collections);
            collections.setReqBillNo(StringUtils.getReqBillNo(maxReqBillNo, DateTimeUtils.getCurrentYear(), regionService.get(null).getChrCode()));
            collections.setPrintdate(DateTimeUtils.getDateTimeStr1());
            collections.setPrintflag(1);

            int row = collectionsDao.insertForGather(collections);
            if (row > 0) {
                chrId = collections.getChrId();
            }else{
                return false;
            }
        }
        */

        BillDetail newBillDetail = new BillDetail();
        newBillDetail.setUntaxBillnameId(collectionsGather.getUntaxBillnameId());
        newBillDetail.setBillNo(collectionsGather.getBillNo());
        newBillDetail.setState("1");
        billDetailDao.update(newBillDetail);


        if(collectionsGather.getOldBillNo() != null) {
            if (!collectionsGather.getOldBillNo().equals("")) {
                BillDetail oldBillDetail = new BillDetail();
                oldBillDetail.setUntaxBillnameId(collectionsGather.getUntaxBillnameId());
                oldBillDetail.setBillNo(collectionsGather.getOldBillNo());
                oldBillDetail.setState("0");
                billDetailDao.update(oldBillDetail);
            }
        }



        cg.setPrintdate(DateTimeUtils.getDateTimeStr1());
        cg.setPrintflag(1);
        cg.setBillNo(collectionsGather.getBillNo());
        //cg.setCollectId(chrId);
        //cg.setCollectdate(DateTimeUtils.getDateTimeStr1());
        //cg.setCollectflag(1);
        int row = collectionsGatherDao.updatePrintflag(cg);

        if(row > 0) {
            CollectionsGatherDetail collectionsGatherDetail = new CollectionsGatherDetail();
            collectionsGatherDetail.setMainId(collectionsGather.getChrId());
            List<CollectionsGatherDetail> collectionsGatherDetailList = collectionsGatherDetailDao.findAllList(collectionsGatherDetail);
            for (CollectionsGatherDetail cgd : collectionsGatherDetailList) {
                collectionsGatherDetailDao.updateIncitemName(cgd);
            }

            return true;
        }else {
            return false;
        }
    }


    public CollectionsGather getCollectionsGatherForPrint(CollectionsGather collectionsGather){
        try{
            CollectionsGather bt = collectionsGatherDao.getCollectionsGatherForPrint(collectionsGather);
            return bt;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }


    @Transactional(rollbackFor=Exception.class)
    public boolean generateCollectionsGather(Collections collections, String chrIds){
        int row = collectionsDao.insert(collections);
        if (row > 0) {
            String insertReturnId = collections.getChrId();

            String[] chrIdArr = chrIds.split(",");
            for(String chrId : chrIdArr){
                CollectionsGather collectionsGather = new CollectionsGather();
                collectionsGather.setCollectdate(DateTimeUtils.getDateTimeStr1());
                collectionsGather.setCollectId(insertReturnId);
                collectionsGather.setCollectflag(1);
                collectionsGather.setChrId(chrId);

                collectionsGatherDao.updateCollectflag(collectionsGather);
            }
            return true;
        }else{
            return false;
        }
    }


    @Transactional(rollbackFor=Exception.class)
    public boolean revokeCollectionsGather(String chrIds){
        String[] chrIdArr = chrIds.split(",");
        for(String chrId : chrIdArr){
            Collections collections = new Collections();
            collections.setChrId(chrId);
            collectionsService.delete(collections);


            CollectionsGather cg = new CollectionsGather();
            cg.setCollectId(chrId);
            List<CollectionsGather> list = collectionsGatherDao.findAllList(cg);

            for(CollectionsGather collectionsGather : list) {
                collectionsGather.setCollectflag(0);
                collectionsGather.setCollectdate("");
                collectionsGather.setCollectId("");
                collectionsGatherDao.updateCollectflag(collectionsGather);
            }
        }
        return true;
    }


    @Transactional(rollbackFor=Exception.class)
    public boolean revokeCollections(String chrIds){
        String[] chrIdArr = chrIds.split(",");
        for(String chrId : chrIdArr){
            Collections collections = new Collections();
            collections.setChrId(chrId);
            collections.setEraseflag(1);
            collections.setErasedate(DateTimeUtils.getDateTimeStr2());
            collectionsDao.cancelCollections(collections);


            CollectionsGather cg = new CollectionsGather();
            cg.setCollectId(chrId);
            List<CollectionsGather> list = collectionsGatherDao.findAllList(cg);

            for(CollectionsGather collectionsGather : list) {
                collectionsGather.setCollectflag(0);
                collectionsGather.setCollectdate("");
                collectionsGather.setCollectId("");
                collectionsGatherDao.updateCollectflag(collectionsGather);
            }
        }
        return true;
    }

    @Transactional(rollbackFor=Exception.class)
    public boolean importInsert(ImportCollectionsGather importCollectionsGather) {
        for (int i = 0 ; i < importCollectionsGather.getCollectionsGatherList().size() ; i++){
            CollectionsGather collectionsGather = importCollectionsGather.getCollectionsGatherList().get(i);

            collectionsGather.setSetYear(DateTimeUtils.getCurrentYear());
            collectionsGather.setCreateDate(DateTimeUtils.getDateTimeStr1());
            collectionsGather.setCreateUser(UserUtils.getUserId());
            collectionsGather.setLastestOpDate(DateTimeUtils.getDateTimeStr1());
            collectionsGather.setLastestOpUser(UserUtils.getUserId());
            collectionsGather.setLastVer(DateTimeUtils.getDateTimeStr1());
            collectionsGather.setRgCode(regionService.get(null).getChrCode());

            collectionsGather.setPayerName(collectionsGather.getPayer());
            collectionsGather.setBilltypeId(importCollectionsGather.getBilltypeId());
            collectionsGather.setEnId(importCollectionsGather.getEnId());
            collectionsGather.setPmId(importCollectionsGather.getPmId());
            collectionsGather.setReceiver(importCollectionsGather.getReceiver());
            collectionsGather.setReceiverid(importCollectionsGather.getReceiverid());
            collectionsGather.setReceiveraccount(importCollectionsGather.getReceiveraccount());
            collectionsGather.setReceiverbank(importCollectionsGather.getReceiverbank());
            int row = collectionsGatherDao.insert(collectionsGather);
            if (row > 0) {
                for(CollectionsGatherDetail collectionsGatherDetail : collectionsGather.getCollectionsGatherDetailList()){
                    collectionsGatherDetail.setMainId(collectionsGather.getChrId());
                    collectionsGatherDetail.setLastVer(DateTimeUtils.getDateTimeStr1());
                    collectionsGatherDetailDao.insert(collectionsGatherDetail);
                }

                BillDetail billDetail = new BillDetail();
                billDetail.setBillNo(collectionsGather.getBillNo());
                billDetail.setUntaxBillnameId(collectionsGather.getBilltypeId());
                billDetail.setState("1");
                billDetailDao.update(billDetail);
            }
        }
        return true;
    }
}