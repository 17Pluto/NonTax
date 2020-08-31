package com.xcmis.feefax.modules.dao;

import com.xcmis.feefax.modules.entity.Region;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;


@MyBatisDao
public interface RegionDao extends IBaseDao<Region> {
}
