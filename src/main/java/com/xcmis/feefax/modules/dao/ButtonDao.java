package com.xcmis.feefax.modules.dao;

import com.xcmis.feefax.modules.entity.Button;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;


@MyBatisDao
public interface ButtonDao extends IBaseDao<Button> {
    public List<Button> getButtonListByStatusId(Button button);
}
