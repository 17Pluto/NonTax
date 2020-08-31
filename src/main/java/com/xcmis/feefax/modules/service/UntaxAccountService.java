package com.xcmis.feefax.modules.service;
/**
 * @author fangzhe
 * @date 2020-04-16
 */
import com.xcmis.feefax.modules.dao.UntaxAccountDao;
import com.xcmis.feefax.modules.entity.UntaxAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class UntaxAccountService {

    @Autowired
    private UntaxAccountDao untaxAccountDao;

    @Transactional(readOnly = false)
    public boolean insert(UntaxAccount untaxAccount) {
        try {
            int row = untaxAccountDao.insert(untaxAccount);
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
    public String insertReturnId(UntaxAccount untaxAccount){
        try{
            int row = untaxAccountDao.insert(untaxAccount);
            if(row > 0){
                return untaxAccount.getChrId();
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Transactional(readOnly = false)
    public boolean update(UntaxAccount untaxAccount){
        try{
            int row = untaxAccountDao.update(untaxAccount);
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
    public boolean delete(UntaxAccount untaxAccount){
        try{
            int row = untaxAccountDao.delete(untaxAccount);
            if(row > 0){
                return true;
            }
            return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<UntaxAccount> findAllList(UntaxAccount untaxAccount){
        try{
            List<UntaxAccount> list = untaxAccountDao.findAllList(untaxAccount);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<UntaxAccount> findList(UntaxAccount billInStore){
        try{
            List<UntaxAccount> list = untaxAccountDao.findList(billInStore);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public UntaxAccount get(UntaxAccount untaxAccount){
        try{
            UntaxAccount bt = untaxAccountDao.get(untaxAccount);
            return bt;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<UntaxAccount> getUntaxAccountListByCondition(Map<String, Object> map){
        try{
            List<UntaxAccount> list = untaxAccountDao.getUntaxAccountListByCondition(map);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public long getUntaxAccountListTotal(UntaxAccount untaxAccount){
        try{
            long total = untaxAccountDao.getUntaxAccountListTotal(untaxAccount);
            return total;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }
}