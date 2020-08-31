package com.xcmis.feefax.modules.service;

import com.xcmis.feefax.modules.dao.BillDetailDao;
import com.xcmis.feefax.modules.dao.EduImportStuInfoDao;
import com.xcmis.feefax.modules.dao.EduImportStuInfoDetailDao;
import com.xcmis.feefax.modules.dao.IncomeItemDao;
import com.xcmis.feefax.modules.entity.*;
import com.xcmis.framework.common.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.List;
import java.util.Map;

/**
 * @Author fangzhe
 * @Date 2020/6/21 11:11 上午
 * @Version 1.3
 */
@Service
@Transactional(readOnly = true)
public class EduStuInfoService {

    @Autowired
    private EduImportStuInfoDao eduImportStuInfoDao;

    @Autowired
    private EduImportStuInfoDetailDao eduImportStuInfoDetailDao;

    @Autowired
    private IncomeItemDao incomeItemDao;

    @Autowired
    private BillDetailDao billDetailDao;

    /**
     * 学生信息导入
     * @param eduImportStuInfo
     * @return
     */
    @Transactional(rollbackFor=Exception.class)
    public boolean stuInfoInsert(EduImportStuInfo eduImportStuInfo){
        //导入前插入当前启用的批次
        int row = eduImportStuInfoDao.insert(eduImportStuInfo);
        if(row > 0){
            for(EduImportStuInfoDetail eduImportStuInfoDetail : eduImportStuInfo.getEduImportStuInfoDetailList()){
                eduImportStuInfoDetail.setStuMainid(eduImportStuInfo.getChrId());
                String chrId = eduImportStuInfoDetailDao.getItemChrId(eduImportStuInfoDetail);
                eduImportStuInfoDetail.setIncitemid(chrId);
                eduImportStuInfoDetailDao.insert(eduImportStuInfoDetail);
            }
        }
        return true;
    }

    @Transactional(readOnly = false)
    public long getEduStuInfoListTotal(EduImportStuInfo eduImportStuInfo){
        try{
            long total = eduImportStuInfoDao.getEduStuInfoListTotal(eduImportStuInfo);
            return total;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Transactional(readOnly = false)
    public List<EduImportStuInfo> getEduStuInfoListByCondition(Map<String,Object> map){
        try{
            List<EduImportStuInfo> list = eduImportStuInfoDao.getEduStuInfoListByCondition(map);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }


    @Transactional(readOnly = false)
    public List<EduImportStuInfo> getEduStuInfoListByConditionForExport(Map<String,Object> map){
        try{
            List<EduImportStuInfo> list = eduImportStuInfoDao.getEduStuInfoListByConditionForExport(map);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Transactional(readOnly = false)
    public List<EduImportStuInfoDetail> getEduStuInfoDetailList(Map<String,Object> map){
        try{
            List<EduImportStuInfoDetail> list = eduImportStuInfoDetailDao.getEduStuInfoDetailList(map);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }



    @Transactional(rollbackFor=Exception.class)
    public boolean delete(String chrIds){

        String[] chrIdArr = chrIds.split(",");
        for(String chrId : chrIdArr){
            EduImportStuInfo eduImportStuInfo = new EduImportStuInfo();
            eduImportStuInfo.setChrId(chrId);
            int row = eduImportStuInfoDao.delete(eduImportStuInfo);
            if(row > 0){
                EduImportStuInfoDetail eduImportStuInfoDetail = new EduImportStuInfoDetail();
                eduImportStuInfoDetail.setStuMainid(eduImportStuInfo.getChrId());
                eduImportStuInfoDetailDao.delete(eduImportStuInfoDetail);
            }
        }
        return true;
    }

    /**
     *
     * @param chrId 页面回传的chrId
     * @return
     */
    @Transactional(rollbackFor=Exception.class)
    public EduImportStuInfo getEduStuInfoByChrId(String chrId){
        EduImportStuInfo eduImportStuInfo = eduImportStuInfoDao.getEduStuInfoByChrId(chrId);

        return eduImportStuInfo;
    }

    @Transactional(rollbackFor=Exception.class)
    public Boolean getEduStuInfoDetailByChrId(Map<String,Object> map){
        List<EduImportStuInfoDetail> list = eduImportStuInfoDetailDao.getEduStuInfoDetailList(map);
        if (list.size() == 0){
            return false;
        }else{
            return true;
        }
    }


    /**
     *
     * @param eduImportStuInfo
     * @return
     */
    @Transactional(rollbackFor=Exception.class)
    public boolean insert(EduImportStuInfo eduImportStuInfo) {
        int row = eduImportStuInfoDao.insert(eduImportStuInfo);
        if (row > 0) {
            for(EduImportStuInfoDetail eduImportStuInfoDetail : eduImportStuInfo.getEduImportStuInfoDetailList()){
                eduImportStuInfoDetail.setStuMainid(eduImportStuInfo.getChrId());
                String chrCode = eduImportStuInfoDetailDao.getItemCode(eduImportStuInfoDetail);
                eduImportStuInfoDetail.setIncitemCode(chrCode);
                eduImportStuInfoDetailDao.insert(eduImportStuInfoDetail);
            }
            return true;
        }else{
            return false;
        }
    }

    @Transactional(rollbackFor=Exception.class)
    public boolean updateStuInfo(EduImportStuInfo eduImportStuInfo) {
        int row = eduImportStuInfoDao.update(eduImportStuInfo);
        /**
         * 需要先删除详细表
         *
         */
        eduImportStuInfoDetailDao.deleteTotalDetail(eduImportStuInfo.getChrId());
        if (row > 0){
            for(EduImportStuInfoDetail eduImportStuInfoDetail : eduImportStuInfo.getEduImportStuInfoDetailList()){
                String chrCode = eduImportStuInfoDetailDao.getItemCode(eduImportStuInfoDetail);
                eduImportStuInfoDetail.setIncitemCode(chrCode);
                eduImportStuInfoDetail.setStuMainid(eduImportStuInfo.getChrId());
                eduImportStuInfoDetail.setLastVer(DateTimeUtils.getDateTimeStr1());
                eduImportStuInfoDetailDao.insert(eduImportStuInfoDetail);
            }
            return true;
        }else{
            return false;
        }
    }

    @Transactional(rollbackFor=Exception.class)
    public boolean updateStuPayflag(String chrId) {
        int row = eduImportStuInfoDao.updateStuPayflag(chrId);
        if (row > 0){
            return true;
        }else{
            return false;
        }
    }

//    @Transactional(rollbackFor=Exception.class)
//    public boolean checkStuIdIsExist(String stuId) {
//
////        int row = eduImportStuInfoDao.checkStuIdIsExist(stuId);
////        if (row > 0){
////            return true;
////        }else{
////            return false;
////        }
//    }


    @Transactional(rollbackFor=Exception.class)
    public boolean updateStuInfoBillNo(EduImportStuInfo eduImportStuInfo) {
        int row = eduImportStuInfoDao.updateStuInfoBillNo(eduImportStuInfo);
        int row1 = eduImportStuInfoDao.updateCollectionsBillNo(eduImportStuInfo);
        if (row > 0 && row1 >0){
            BillDetail billDetail = new BillDetail();
            billDetail.setBillNo(eduImportStuInfo.getBillNo());
            billDetail.setUntaxBillnameId(eduImportStuInfo.getBilltypeId());
            billDetail.setState("1");
            billDetailDao.update(billDetail);
            return true;
        }else{
            return false;
        }
    }

    /**
     * 根据登录的账号查找年级
     * @param eduImportStuInfo
     * @return
     */
    public List<EduImportStuInfo> findAllGrade(EduImportStuInfo eduImportStuInfo){
        try{
            List<EduImportStuInfo> list = eduImportStuInfoDao.findAllGrade(eduImportStuInfo);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public List<EduImportStuInfo> findAllClass(EduImportStuInfo eduImportStuInfo){
        try{
            List<EduImportStuInfo> list = eduImportStuInfoDao.findAllClass(eduImportStuInfo);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public EduImportStuInfo get(EduImportStuInfo eduImportStuInfo){
        try{
            EduImportStuInfo bt = eduImportStuInfoDao.get(eduImportStuInfo);
            return bt;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }



}
