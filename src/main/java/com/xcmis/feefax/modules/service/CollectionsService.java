package com.xcmis.feefax.modules.service;
/**
 * @author fangzhe
 * @date 2020-04-01
 * BillInStoore service
 */
import com.xcmis.feefax.modules.dao.*;
import com.xcmis.feefax.modules.entity.*;
import com.xcmis.framework.common.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class CollectionsService {

    @Autowired
    private CollectionsDao collectionsDao;

    @Autowired
    private CollectionsDetailDao collectionsDetailDao;

    @Autowired
    private CollectionsGatherDao collectionsGatherDao;

    @Autowired
    private BillDetailDao billDetailDao;

    @Autowired
    private UntaxNosourceCollectionDao untaxNosourceCollectionDao;

    @Autowired
    private UntaxNosourceDao untaxNosourceDao;

    @Autowired
    private EduImportStuInfoDao eduImportStuInfoDao;


    @Transactional(rollbackFor=Exception.class)
    public boolean insert(Collections collections) {
        if(collections.getIsconsign() == 0){
            collections.setConsigneeEnId(collections.getEnId());
        }
        int row = collectionsDao.insert(collections);
        if (row > 0) {
            for(CollectionsDetail collectionsDetail : collections.getCollectionsDetailList()){
                collectionsDetail.setMainId(collections.getChrId());
                collectionsDetail.setLastVer(DateTimeUtils.getDateTimeStr1());
                collectionsDetailDao.insert(collectionsDetail);
            }

            BillDetail billDetail = new BillDetail();
            billDetail.setBillNo(collections.getBillNo());
            billDetail.setUntaxBillnameId(collections.getBilltypeId());
            billDetail.setState("1");
            billDetailDao.update(billDetail);
            return true;
        }else{
            return false;
        }
    }


    @Transactional(readOnly = false)
    public String insertReturnId(Collections collections) {
        try {
            if(collections.getIsconsign() == 0){
                collections.setConsigneeEnId(collections.getEnId());
            }
            int row = collectionsDao.insert(collections);
            if (row > 0) {
                return collections.getChrId();
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Transactional(readOnly = false)
    public boolean updateIsSend(Collections collections) {
        try {
            int row = collectionsDao.updateIsSend(collections);
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
    public boolean updateRedRushFlag(Collections collections) {
        try {
            int row = collectionsDao.updateRedRushFlag(collections);
            if (row > 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Transactional(rollbackFor=Exception.class)
    public boolean update(Collections collections){
        int row = collectionsDao.update(collections);
        if(row > 0){
            String chrId = collections.getChrId();

            CollectionsDetail collectionsDetail = new CollectionsDetail();
            collectionsDetail.setMainId(chrId);
            collectionsDetailDao.delete(collectionsDetail);

            for(CollectionsDetail cd : collections.getCollectionsDetailList()){
                cd.setMainId(chrId);
                cd.setLastVer(DateTimeUtils.getDateTimeStr1());
                collectionsDetailDao.insert(cd);
            }

            return true;
        }else{
            return false;
        }
    }

    @Transactional(rollbackFor=Exception.class)
    public boolean cancelCollections( String chrIds){
        String[] chrIdArr = chrIds.split(",");
        for(String chrId : chrIdArr){
            Collections collections = new Collections();
            collections.setChrId(chrId);
            collections.setErasedate(DateTimeUtils.getDateTimeStr1());
            collections.setEraseflag(1);
            collectionsDao.cancelCollections(collections);


            /*
            collections = collectionsDao.get(collections);

            BillDetail billDetail = new BillDetail();
            billDetail.setBillNo(collections.getBillNo());
            billDetail.setUntaxBillnameId(collections.getBilltypeId());
            billDetail.setState("0");
            billDetailService.update(billDetail);
            */
        }
        return true;
    }

    @Transactional(rollbackFor=Exception.class)
    public boolean delete(Collections collections){
        int row = collectionsDao.delete(collections);
        if(row > 0){
            String chrId = collections.getChrId();

            CollectionsDetail collectionsDetail = new CollectionsDetail();
            collectionsDetail.setMainId(chrId);
            collectionsDetailDao.delete(collectionsDetail);

            if(collections.getBillNo() != null){
                if(!collections.getBillNo().equals("")) {
                    BillDetail billDetail = new BillDetail();
                    billDetail.setUntaxBillnameId(collections.getBilltypeId());
                    billDetail.setState("0");
                    billDetail.setBillNo(collections.getBillNo());
                    billDetailDao.update(billDetail);
                }
            }
            return true;
        }
        return false;
    }

    public List<Collections> findAllList(Collections collections){
        try{
            List<Collections> list = collectionsDao.findAllList(collections);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<Collections> testFindAllList(Collections collections){
        try{
            List<Collections> list = collectionsDao.testFindAllList(collections);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }

    }

    public List<Collections> findList(Collections collections){
        try{
            List<Collections> list = collectionsDao.findList(collections);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Collections get(Collections collections){
        try{
            Collections bt = collectionsDao.get(collections);
            return bt;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }


    public Collections getCollectionsForPrint(Collections collections){
        try{
            Collections bt = collectionsDao.getCollectionsForPrint(collections);
            return bt;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Collections getStuCollectionsForPrint(Collections collections){
        try{
            Collections bt = collectionsDao.getStuCollectionsForPrint(collections);
            return bt;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<Collections> getCollectionsListByCondition(Map<String, Object> map){
        try{
            List<Collections> list = collectionsDao.getCollectionsListByCondition(map);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public long getCollectionsListTotal(Collections collections){
        try{
            long total = collectionsDao.getCollectionsListTotal(collections);
            return total;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }


    @Transactional(rollbackFor=Exception.class)
    public boolean updatePayflag(Collections collections){
        int row = collectionsDao.updatePayflag(collections);
        if(row > 0)
            return true;
        else
            return false;
    }


    @Transactional(rollbackFor=Exception.class)
    public boolean updateLateflag(Collections collections){
        int row = collectionsDao.updateLateflag(collections);
        if(row > 0)
            return true;
        else
            return false;
    }


    @Transactional(rollbackFor=Exception.class)
    public boolean updatePayflag(String chrIds, String payflag){
        String[] chrIdArr = chrIds.split(",");
        for(String chrId : chrIdArr) {
            Collections collections = new Collections();
            collections.setChrId(chrId);
            collections.setPayflag(Integer.parseInt(payflag));
            collections.setPaydate(DateTimeUtils.getDateTimeStr1());
            collectionsDao.updatePayflag(collections);
        }
        return true;
    }


    @Transactional(rollbackFor=Exception.class)
    public boolean updateCheckflag(Collections collections){
        int row = collectionsDao.updateCheckflag(collections);
        if(row > 0)
            return true;
        else
            return false;
    }


    @Transactional(rollbackFor=Exception.class)
    public boolean updatePrintflag(String chrIds){
        String[] chrIdArr = chrIds.split(",");
        for(String chrId : chrIdArr){
            Collections collections = new Collections();
            collections.setChrId(chrId);
            collections = collectionsDao.get(collections);

            collections.setPrintdate(DateTimeUtils.getDateTimeStr1());
            collections.setPrintflag(1);
            collectionsDao.updatePrintflag(collections);


            CollectionsDetail collectionsDetail = new CollectionsDetail();
            collectionsDetail.setMainId(collections.getChrId());
            List<CollectionsDetail> collectionsDetailList = collectionsDetailDao.findAllList(collectionsDetail);
            for(CollectionsDetail cd : collectionsDetailList){
                collectionsDetailDao.updateIncitemName(cd);
            }
        }
        return true;
    }


    @Transactional(rollbackFor=Exception.class)
    public boolean updatePrintflagForOne(Collections collections){
        BillDetail newBillDetail = new BillDetail();
        newBillDetail.setUntaxBillnameId(collections.getUntaxBillnameId());
        newBillDetail.setBillNo(collections.getBillNo());
        newBillDetail.setState("1");
        billDetailDao.update(newBillDetail);

        if(collections.getOldBillNo() != null){
            if(!collections.getOldBillNo().equals("")){
                BillDetail oldBillDetail = new BillDetail();
                oldBillDetail.setUntaxBillnameId(collections.getUntaxBillnameId());
                oldBillDetail.setBillNo(collections.getOldBillNo());
                oldBillDetail.setState("0");
                billDetailDao.update(oldBillDetail);
            }
        }

        Collections c = collectionsDao.get(collections);
        c.setPrintdate(DateTimeUtils.getDateTimeStr1());
        c.setPrintflag(1);
        c.setBillNo(collections.getBillNo());
        int row = collectionsDao.updatePrintflag(c);

        if(row > 0) {
            CollectionsDetail collectionsDetail = new CollectionsDetail();
            collectionsDetail.setMainId(collections.getChrId());
            List<CollectionsDetail> collectionsDetailList = collectionsDetailDao.findAllList(collectionsDetail);
            for(CollectionsDetail cd : collectionsDetailList){
                collectionsDetailDao.updateIncitemName(cd);
            }

            return true;
        }else{
            return false;
        }

    }


    public String getMaxReqBillNo(Collections collections){
        try{
            String maxReqBillNo = collectionsDao.getMaxReqBillNo(collections);
            return maxReqBillNo;
        }catch(Exception e){
            e.printStackTrace();
            return "";
        }
    }


    /**
     * 核收数据管理
     * @param map
     * @return
     */
    public List<Collections> getCollectionsByCondition(Map<String, Object> map){
        try{
            List<Collections> list = collectionsDao.getCollectionsByCondition(map);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Transactional(rollbackFor=Exception.class)
    public boolean updateCheckEarne(Collections collections){
        if(collectionsDao.updateCheckEarne(collections) > 0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 更新核收状态和日期
     * @param
     * @return
     */
    @Transactional(rollbackFor=Exception.class)
    public boolean updateCheckEarne(String chrIds){
        String[] chrIdArr = chrIds.split(",");
        for(String chrId : chrIdArr){
            Collections collections = new Collections();
            collections.setChrId(chrId);
            collections = collectionsDao.get(collections);
            //更新核收时间
            collections.setCheckearnedate(DateTimeUtils.getDateTimeStr2());
            //更新核收状态为已核收
            collections.setCheckearne(1);
            collectionsDao.updateCheckEarne(collections);

            if(collections.getReceivetype() == 3){
                CollectionsGather collectionsGather = new CollectionsGather();
                collectionsGather.setCollectId(chrId);
                List<CollectionsGather> collectionsGatherList = collectionsGatherDao.findAllList(collectionsGather);
                for(CollectionsGather cg : collectionsGatherList){
                    cg.setReturndate(DateTimeUtils.getDateTimeStr1());
                    cg.setReturnflag(1);
                    collectionsGatherDao.updateReturnflag(cg);
                }
            }

            if(collections.getNosourceIds() != null) {
                if (collections.getNosourceIds().indexOf(",") > -1) {
                    String[] nosourceIdsArr = collections.getNosourceIds().split(",");
                    for (String nosourceId : nosourceIdsArr) {
                        UntaxNosource un = new UntaxNosource();
                        un.setChrId(nosourceId);
                        un.setCheckearnedate(DateTimeUtils.getDateTimeStr2());
                        un.setIsAudit(1);
                        un.setCheckearne(1);
                        untaxNosourceDao.updateCheckEarne(un);
                    }
                }else{
                    boolean isUpdate = true;
                    List<Collections> collectionsList = collectionsDao.findAllList(collections);
                    for(Collections cs : collectionsList){
                        int lateflag = cs.getLateflag();
                        if(lateflag == 0){
                            isUpdate = false;
                            break;
                        }
                    }
                    if(isUpdate){
                        UntaxNosource un = new UntaxNosource();
                        un.setChrId(collections.getNosourceIds());
                        un.setCheckearnedate(DateTimeUtils.getDateTimeStr2());
                        un.setIsAudit(1);
                        un.setCheckearne(1);
                        untaxNosourceDao.updateCheckEarne(un);
                    }
                }
            }else{
                boolean isUpdate = true;
                List<Collections> collectionsList = collectionsDao.findAllList(collections);
                for(Collections cs : collectionsList){
                    int lateflag = cs.getLateflag();
                    if(lateflag == 0){
                        isUpdate = false;
                        break;
                    }
                }
                if(isUpdate){
                    UntaxNosource un = new UntaxNosource();
                    un.setChrId(collections.getNosourceIds());
                    un.setCheckearnedate(DateTimeUtils.getDateTimeStr2());
                    un.setIsAudit(1);
                    un.setCheckearne(1);
                    untaxNosourceDao.updateCheckEarne(un);
                }
            }

        }
        return true;
    }

    /**
     * 核收数据分页
     * @param map
     * @return
     */
    public long getCollectionsTotal(Map<String, Object> map){
        try{
            long total = collectionsDao.getCollectionsTotal(map);
            return total;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }


    public List<Collections> getCollectionsForPay(Map<String, Object> map){
        try{
            List<Collections> list = collectionsDao.getCollectionsForPay(map);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public long getCollectionsForPayTotal(Collections collections){
        try{
            long total = collectionsDao.getCollectionsForPayTotal(collections);
            return total;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }


    @Transactional(readOnly = false)
    public Collections bindCollections(Collections collections) {
        String collectionId = "";
        if(collections.getIsconsign() == 0){
            collections.setConsigneeEnId(collections.getEnId());
        }
        int row = collectionsDao.insert(collections);
        if (row > 0) {
            for(CollectionsDetail collectionsDetail : collections.getCollectionsDetailList()){
                collectionsDetail.setMainId(collections.getChrId());
                collectionsDetail.setLastVer(DateTimeUtils.getDateTimeStr1());
                collectionsDetailDao.insert(collectionsDetail);
            }

            BillDetail billDetail = new BillDetail();
            billDetail.setBillNo(collections.getBillNo());
            billDetail.setUntaxBillnameId(collections.getBilltypeId());
            billDetail.setState("1");
            billDetailDao.update(billDetail);

            collectionId = collections.getChrId();

            String[] nosourceIds = collections.getNosourceIds().split(",");

            for(String nosourceId : nosourceIds) {
                UntaxNosource untaxNosource = new UntaxNosource();
                untaxNosource.setChrId(nosourceId);
                untaxNosource.setLastVer(DateTimeUtils.getDateTimeStr1());
                untaxNosourceDao.bindingIsCollectInNosource(untaxNosource);

                UntaxNosourceCollection untaxNosourceCollection = new UntaxNosourceCollection();
                untaxNosourceCollection.setCollectionId(collectionId);
                untaxNosourceCollection.setNosourceId(nosourceId);
                untaxNosourceCollectionDao.insertIds(untaxNosourceCollection);
            }

            return collections;
        }
        return null;

    }


    /**
     * 学校信息插入
     * @param collections
     * @return
     */
    @Transactional(rollbackFor=Exception.class)
    public boolean insertStuInfo(Collections collections) {
        String chrId = collections.getChrCode4();
        eduImportStuInfoDao.updateIsCollection(chrId);

        if(collections.getIsconsign() == 0){
            collections.setConsigneeEnId(collections.getEnId());
        }
        int row = collectionsDao.insert(collections);
        if (row>0){
            return true;
        }else {
            return false;
        }
    }
}