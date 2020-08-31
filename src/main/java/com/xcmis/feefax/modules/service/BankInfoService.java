package com.xcmis.feefax.modules.service;

import com.xcmis.feefax.modules.dao.BankInfoDao;
import com.xcmis.feefax.modules.entity.BankInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author fangzhe
 * @Date 2020/8/18 12:18 下午
 * @Version 1.0
 */
@Service
public class BankInfoService {

    @Autowired
    private BankInfoDao bankInfoDao;

    @Transactional(readOnly = false)
    public boolean insert(BankInfo bankInfo) {
        try {
            int row = bankInfoDao.insert(bankInfo);
            if (row > 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
