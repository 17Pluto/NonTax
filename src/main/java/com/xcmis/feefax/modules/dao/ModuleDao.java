package com.xcmis.feefax.modules.dao;

import com.xcmis.feefax.modules.entity.Module;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;


@MyBatisDao
public interface ModuleDao extends IBaseDao<Module> {
    public List<Module> getModuleListByMenuId(Module module);
}
