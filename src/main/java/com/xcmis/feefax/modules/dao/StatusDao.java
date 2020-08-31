package com.xcmis.feefax.modules.dao;

import com.xcmis.feefax.modules.entity.Status;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;


@MyBatisDao
public interface StatusDao extends IBaseDao<Status> {
    public List<Status> getStatusListByModuleId(Status status);
}
