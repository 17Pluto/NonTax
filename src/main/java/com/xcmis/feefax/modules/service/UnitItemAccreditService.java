package com.xcmis.feefax.modules.service;
/**
 * @author fangzhe
 * @date 2020-04-10
 * AccreditDao service
 */
import com.xcmis.feefax.modules.dao.UnitItemAccreditDao;
import com.xcmis.feefax.modules.entity.UnitItemAccredit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class UnitItemAccreditService {

    @Autowired
    private UnitItemAccreditDao accreditDao;

    @Transactional(readOnly = false)
    public boolean insert(UnitItemAccredit accredit) {
        try {
            int row = accreditDao.insert(accredit);
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
    public String insertReturnId(UnitItemAccredit accredit){
        try{
            int row = accreditDao.insert(accredit);
            if(row > 0){
                return accredit.getChrId();
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Transactional(readOnly = false)
    public boolean update(UnitItemAccredit accredit){
        try{
            int row = accreditDao.update(accredit);
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
    public boolean delete(UnitItemAccredit accredit){
        try{
            int row = accreditDao.delete(accredit);
            if(row > 0){
                return true;
            }
            return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<UnitItemAccredit> findAllList(UnitItemAccredit accredit){
        try{
            List<UnitItemAccredit> list = accreditDao.findAllList(accredit);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<UnitItemAccredit> findList(UnitItemAccredit accredit){
        try{
            List<UnitItemAccredit> list = accreditDao.findList(accredit);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public UnitItemAccredit get(UnitItemAccredit accredit){
        try{
            UnitItemAccredit bt = accreditDao.get(accredit);
            return bt;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<UnitItemAccredit> getAccreditListByCondition(Map<String, Object> map){
        try{
            List<UnitItemAccredit> list = accreditDao.getAccreditListByCondition(map);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public long getAccreditListTotal(UnitItemAccredit accredit){
        try{
            long total = accreditDao.getAccreditListTotal(accredit);
            return total;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }
}