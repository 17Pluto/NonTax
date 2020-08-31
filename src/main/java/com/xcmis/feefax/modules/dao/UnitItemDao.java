package com.xcmis.feefax.modules.dao;
/**
 * @author fangzhe
 * @date 2020-03-25
 * 执收单位收入项目dao
 */
import com.xcmis.feefax.modules.entity.UnitItem;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface UnitItemDao extends IBaseDao<UnitItem> {

    public long getUnitItemListTotal(UnitItem unitItem);

    public List<UnitItem> getUnitItemListByCondition(Map<String, Object> map);

    public List<UnitItem> getUnitItemByEnIdAndBilltypeIdAndAccountId(UnitItem unitItem);

    public List<UnitItem> getUnitItemByUnitId(UnitItem unitItem);

    public int checkUnitItem(UnitItem unitItem);
}
