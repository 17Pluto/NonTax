package com.xcmis.feefax.modules.service;

import com.xcmis.feefax.modules.dao.UnitItemBankDao;
import com.xcmis.feefax.modules.dao.UnitItemBillFormDao;
import com.xcmis.feefax.modules.entity.UnitItemBank;
import com.xcmis.feefax.modules.entity.UnitItemBillForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class UnitItemBankService {
    @Autowired
    private UnitItemBankDao unitItemBankDao;

    @Transactional(readOnly = false)
    public boolean insert(UnitItemBank unitItemBank){
        try{
            int row = unitItemBankDao.insert(unitItemBank);
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
    public String insertReturnId(UnitItemBank unitItemBank) {
        try {
            int row = unitItemBankDao.insert(unitItemBank);
            if (row > 0) {
                return unitItemBank.getChrId();
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Transactional(readOnly = false)
    public boolean update(UnitItemBank unitItemBank){
        try{
            int row = unitItemBankDao.update(unitItemBank);
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
    public boolean delete(UnitItemBank unitItemBank){
        try{
            int row = unitItemBankDao.delete(unitItemBank);
            if(row > 0){
                return true;
            }
            return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<UnitItemBank> findAllList(UnitItemBank unitItemBank){
        try{
            List<UnitItemBank> list = unitItemBankDao.findAllList(unitItemBank);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<UnitItemBank> findList(UnitItemBank unitItemBank){
        try{
            List<UnitItemBank> list = unitItemBankDao.findList(unitItemBank);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public UnitItemBank get(UnitItemBank unitItemBank){
        try{
            UnitItemBank bt = unitItemBankDao.get(unitItemBank);
            return bt;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }


    public List<UnitItemBank> getUnitItemBankListByCondition(Map<String, Object> map){
        try{
            List<UnitItemBank> list = unitItemBankDao.getUnitItemBankListByCondition(map);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public long getUnitItemBankListTotal(UnitItemBank unitItemBank){
        try{
            long total = unitItemBankDao.getUnitItemBankListTotal(unitItemBank);
            return total;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }


    public List<UnitItemBank> getUnitItemBankByenIdAndBilltypeId(UnitItemBank unitItemBank){
        try{
            List<UnitItemBank> list = unitItemBankDao.getUnitItemBankByenIdAndBilltypeId(unitItemBank);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<UnitItemBank> getUnitItemBankByenId(UnitItemBank unitItemBank){
        try{
            List<UnitItemBank> list = unitItemBankDao.getUnitItemBankByenId(unitItemBank);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
