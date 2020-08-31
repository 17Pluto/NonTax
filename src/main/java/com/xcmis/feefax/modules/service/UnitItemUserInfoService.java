package com.xcmis.feefax.modules.service;

import com.xcmis.feefax.modules.dao.UnitItemUserInfoDao;
import com.xcmis.feefax.modules.entity.UnitItemUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class UnitItemUserInfoService {
    @Autowired
    private UnitItemUserInfoDao unitItemUserInfoDao;



    @Transactional(readOnly = false)
    public boolean insert(UnitItemUserInfo unitItemUserInfo){
        try{
            int row = unitItemUserInfoDao.insert(unitItemUserInfo);
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
    public String insertReturnId(UnitItemUserInfo unitItemUserInfo) {
        try {
            int row = unitItemUserInfoDao.insert(unitItemUserInfo);
            if (row > 0) {
                return unitItemUserInfo.getChrId();
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Transactional(readOnly = false)
    public boolean update(UnitItemUserInfo unitItemUserInfo){
        try{
            int row = unitItemUserInfoDao.update(unitItemUserInfo);
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
    public boolean delete(UnitItemUserInfo unitItemUserInfo){
        try{
            int row = unitItemUserInfoDao.delete(unitItemUserInfo);
            if(row > 0){
                return true;
            }
            return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<UnitItemUserInfo> findAllList(UnitItemUserInfo unitItemUserInfo){
        try{
            List<UnitItemUserInfo> list = unitItemUserInfoDao.findAllList(unitItemUserInfo);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<UnitItemUserInfo> findList(UnitItemUserInfo unitItemUserInfo){
        try{
            List<UnitItemUserInfo> list = unitItemUserInfoDao.findList(unitItemUserInfo);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public UnitItemUserInfo get(UnitItemUserInfo unitItemUserInfo){
        try{
            UnitItemUserInfo bt = unitItemUserInfoDao.get(unitItemUserInfo);
            return bt;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }


    public List<UnitItemUserInfo> getUnitItemUserInfoListByCondition(Map<String, Object> map){
        try{
            List<UnitItemUserInfo> list = unitItemUserInfoDao.getUnitItemUserInfoListByCondition(map);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public long getUnitItemUserInfoListTotal(UnitItemUserInfo unitItemUserInfo){
        try{
            long total = unitItemUserInfoDao.getUnitItemUserInfoListTotal(unitItemUserInfo);
            return total;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

}
