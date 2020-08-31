package com.xcmis.feefax.modules.service;
/**
 * @author fangzhe
 * @date 2020-03-25
 * 执收单位收入项目service
 */

import com.xcmis.feefax.modules.dao.*;
import com.xcmis.feefax.modules.entity.*;
import com.xcmis.framework.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class UnitItemService {
    @Autowired
    private UnitItemDao unitItemDao;

    @Autowired
    private UnitItemBankDao unitItemBankDao;

    @Autowired
    private UnitItemUserInfoDao unitItemUserInfoDao;

    @Autowired
    private UnitItemBillFormDao unitItemBillFormDao;

    @Autowired
    private UnitItemAccreditDao unitItemAccreditDao;

    @Transactional(rollbackFor=Exception.class)
    public boolean insert(UnitItem unitItem){
        int row = unitItemDao.insert(unitItem);
        if (row > 0) {
            String chrId = unitItem.getChrId();


            for(UnitItemBank unitItemBank : unitItem.getUnitItemBankList()){
                unitItemBank.setMainId(chrId);
                unitItemBank.setLastVer(unitItem.getLastVer());
                unitItemBankDao.insert(unitItemBank);
            }

            for(UnitItemBillForm unitItemBillForm : unitItem.getUnitItemBillFormList()){
                unitItemBillForm.setMainId(chrId);
                unitItemBillForm.setLastVer(unitItem.getLastVer());
                unitItemBillFormDao.insert(unitItemBillForm);
            }

            for(UnitItemUserInfo unitItemUserInfo : unitItem.getUnitItemUserInfoList()){
                unitItemUserInfo.setMainId(chrId);
                unitItemUserInfo.setLastVer(unitItem.getLastVer());
                unitItemUserInfoDao.insert(unitItemUserInfo);
            }

            for(UnitItemAccredit unitItemAccredit : unitItem.getUnitItemAccreditList()){
                unitItemAccredit.setMainId(chrId);
                unitItemAccredit.setLastVer(unitItem.getLastVer());
                unitItemAccreditDao.insert(unitItemAccredit);
            }
            return true;
        }else {
            return false;
        }
    }


    @Transactional(readOnly = false)
    public String insertReturnId(UnitItem unitItem) {
        try {
            int row = unitItemDao.insert(unitItem);
            if (row > 0) {
                return unitItem.getChrId();
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Transactional(rollbackFor=Exception.class)
    public boolean update(UnitItem unitItem){
        int row = unitItemDao.update(unitItem);
        if (row > 0) {
            String chrId = unitItem.getChrId();

            UnitItemBank uib = new UnitItemBank();
            uib.setMainId(chrId);
            unitItemBankDao.delete(uib);
            for(UnitItemBank unitItemBank : unitItem.getUnitItemBankList()){
                unitItemBank.setMainId(chrId);
                unitItemBank.setLastVer(unitItem.getLastVer());
                unitItemBankDao.insert(unitItemBank);
            }


            UnitItemBillForm uibf = new UnitItemBillForm();
            uibf.setMainId(chrId);
            unitItemBillFormDao.delete(uibf);
            for(UnitItemBillForm unitItemBillForm : unitItem.getUnitItemBillFormList()){
                unitItemBillForm.setMainId(chrId);
                unitItemBillForm.setLastVer(unitItem.getLastVer());
                unitItemBillFormDao.insert(unitItemBillForm);
            }

            UnitItemUserInfo uiui = new UnitItemUserInfo();
            uiui.setMainId(chrId);
            unitItemUserInfoDao.delete(uiui);
            for(UnitItemUserInfo unitItemUserInfo : unitItem.getUnitItemUserInfoList()){
                unitItemUserInfo.setMainId(chrId);
                unitItemUserInfo.setLastVer(unitItem.getLastVer());
                unitItemUserInfoDao.insert(unitItemUserInfo);
            }

            UnitItemAccredit uia = new UnitItemAccredit();
            uia.setMainId(chrId);
            unitItemAccreditDao.delete(uia);
            for(UnitItemAccredit unitItemAccredit : unitItem.getUnitItemAccreditList()){
                unitItemAccredit.setMainId(chrId);
                unitItemAccredit.setLastVer(unitItem.getLastVer());
                unitItemAccreditDao.insert(unitItemAccredit);
            }
            return true;
        }else {
            return false;
        }
    }

    @Transactional(rollbackFor=Exception.class)
    public boolean delete(UnitItem unitItem){
        int row = unitItemDao.delete(unitItem);
        if (row > 0) {
            String chrId = unitItem.getChrId();

            UnitItemBank uib = new UnitItemBank();
            uib.setMainId(chrId);
            unitItemBankDao.delete(uib);

            UnitItemBillForm uibf = new UnitItemBillForm();
            uibf.setMainId(chrId);
            unitItemBillFormDao.delete(uibf);

            UnitItemUserInfo uiui = new UnitItemUserInfo();
            uiui.setMainId(chrId);
            unitItemUserInfoDao.delete(uiui);

            UnitItemAccredit uia = new UnitItemAccredit();
            uia.setMainId(chrId);
            unitItemAccreditDao.delete(uia);
            return true;
        }else {
            return false;
        }
    }

    public List<UnitItem> findAllList(UnitItem unitItem){
        try{
            List<UnitItem> list = unitItemDao.findAllList(unitItem);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<UnitItem> findList(UnitItem unitItem){
        try{
            List<UnitItem> list = unitItemDao.findList(unitItem);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public UnitItem get(UnitItem unitItem){
        try{
            UnitItem bt = unitItemDao.get(unitItem);
            return bt;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }


    public List<UnitItem> getUnitItemListByCondition(Map<String, Object> map){
        try{
            List<UnitItem> list = unitItemDao.getUnitItemListByCondition(map);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public long getUnitItemListTotal(UnitItem unitItem){
        try{
            long total = unitItemDao.getUnitItemListTotal(unitItem);
            return total;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public List<UnitItem> getUnitItemByEnIdAndBilltypeIdAndAccountId(UnitItem unitItem){
        try{
            List<UnitItem> list = unitItemDao.getUnitItemByEnIdAndBilltypeIdAndAccountId(unitItem);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }


    public List<UnitItem> getUnitItemByUnitId(UnitItem unitItem){
        try{
            List<UnitItem> list = unitItemDao.getUnitItemByUnitId(unitItem);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Transactional(rollbackFor=Exception.class)
    public boolean checkUnitItem(String chrIds, String stateCode){
        String[] chrIdArr = chrIds.split(",");
        stateCode = StringUtils.stateCodeStr(stateCode);
        int row = 0;
        for(String chrId : chrIdArr){
            UnitItem unitItem = new UnitItem();
            unitItem.setIsend(1);
            unitItem.setStateCode(stateCode);
            unitItem.setChrId(chrId);
            row += unitItemDao.checkUnitItem(unitItem);
        }
        if(row > 0){
            return true;
        }else{
            return false;
        }
    }
}
