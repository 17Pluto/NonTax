package com.xcmis.feefax.modules.service;

import com.xcmis.feefax.modules.dao.UnitItemBillFormDao;
import com.xcmis.feefax.modules.entity.UnitItemBillForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class UnitItemBillFormService {
    @Autowired
    private UnitItemBillFormDao unitItemBillFormDao;

    @Transactional(readOnly = false)
    public boolean insert(UnitItemBillForm unitItemBillForm){
        try{
            int row = unitItemBillFormDao.insert(unitItemBillForm);
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
    public String insertReturnId(UnitItemBillForm unitItemBillForm) {
        try {
            int row = unitItemBillFormDao.insert(unitItemBillForm);
            if (row > 0) {
                return unitItemBillForm.getChrId();
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Transactional(readOnly = false)
    public boolean update(UnitItemBillForm unitItemBillForm){
        try{
            int row = unitItemBillFormDao.update(unitItemBillForm);
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
    public boolean delete(UnitItemBillForm unitItemBillForm){
        try{
            int row = unitItemBillFormDao.delete(unitItemBillForm);
            if(row > 0){
                return true;
            }
            return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<UnitItemBillForm> findAllList(UnitItemBillForm unitItemBillForm){
        try{
            List<UnitItemBillForm> list = unitItemBillFormDao.findAllList(unitItemBillForm);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<UnitItemBillForm> findList(UnitItemBillForm unitItemBillForm){
        try{
            List<UnitItemBillForm> list = unitItemBillFormDao.findList(unitItemBillForm);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public UnitItemBillForm get(UnitItemBillForm unitItemBillForm){
        try{
            UnitItemBillForm bt = unitItemBillFormDao.get(unitItemBillForm);
            return bt;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }


    public List<UnitItemBillForm> getUnitItemBillFormListByCondition(Map<String, Object> map){
        try{
            List<UnitItemBillForm> list = unitItemBillFormDao.getUnitItemBillFormListByCondition(map);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public long getUnitItemBillFormListTotal(UnitItemBillForm unitItemBillForm){
        try{
            long total = unitItemBillFormDao.getUnitItemBillFormListTotal(unitItemBillForm);
            return total;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

}
