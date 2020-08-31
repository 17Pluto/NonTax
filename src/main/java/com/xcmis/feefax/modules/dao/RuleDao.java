package com.xcmis.feefax.modules.dao;

import com.xcmis.feefax.modules.entity.Rule;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;


@MyBatisDao
public interface RuleDao extends IBaseDao<Rule> {

}
