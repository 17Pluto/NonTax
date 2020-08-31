package com.xcmis.feefax.modules.dao;

import com.xcmis.feefax.modules.entity.BankInfo;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;

/**
 * @Author fangzhe
 * @Date 2020/8/18 12:20 下午
 * @Version 1.0
 */
@MyBatisDao

public interface BankInfoDao extends IBaseDao<BankInfo> {

    public List<BankInfo> findAllList(BankInfo bankInfo);

}
