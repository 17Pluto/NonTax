package com.xcmis.feefax.modules.service;

import com.xcmis.feefax.modules.dao.IncomeEnterpriseDao;
import com.xcmis.feefax.modules.entity.IncomeEnterprise;
import com.xcmis.feefax.modules.entity.RgUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class IncomeEnterpriseService {
    @Autowired
    private IncomeEnterpriseDao incomeEnterpriseDao;

    public List<IncomeEnterprise> findAllList(IncomeEnterprise incomeEnterprise){
        try{
            List<IncomeEnterprise> list = incomeEnterpriseDao.findAllList(incomeEnterprise);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public IncomeEnterprise get(IncomeEnterprise incomeEnterprise){
        try{
            IncomeEnterprise bt = incomeEnterpriseDao.get(incomeEnterprise);
            return bt;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }


    public List<IncomeEnterprise> getIncomeEnterpriseListByCondition(Map<String, Object> map){
        try{
            List<IncomeEnterprise> list = incomeEnterpriseDao.getIncomeEnterpriseListByCondition(map);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public long getIncomeEnterpriseListTotal(IncomeEnterprise incomeEnterprise){
        try{
            long total = incomeEnterpriseDao.getIncomeEnterpriseListTotal(incomeEnterprise);
            return total;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }


    public List<IncomeEnterprise> getIncomeEnterpriseByChrId(IncomeEnterprise incomeEnterprise){
        try{
            List<IncomeEnterprise> list = incomeEnterpriseDao.getIncomeEnterpriseByChrId(incomeEnterprise);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
