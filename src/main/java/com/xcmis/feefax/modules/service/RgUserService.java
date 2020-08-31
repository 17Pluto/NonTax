package com.xcmis.feefax.modules.service;


import com.xcmis.feefax.modules.dao.RgUserDao;
import com.xcmis.feefax.modules.entity.RgUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class RgUserService {
    @Autowired
    private RgUserDao rgUserDao;

    public List<RgUser> findAllList(RgUser rgUser){
        try{
            List<RgUser> list = rgUserDao.findAllList(rgUser);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public RgUser get(RgUser rgUser){
        try{
            RgUser bt = rgUserDao.get(rgUser);
            return bt;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }


    public List<RgUser> getRgUserListByCondition(Map<String, Object> map){
        try{
            List<RgUser> list = rgUserDao.getRgUserListByCondition(map);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public long getRgUserListTotal(RgUser rgUser){
        try{
            long total = rgUserDao.getRgUserListTotal(rgUser);
            return total;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }


}
