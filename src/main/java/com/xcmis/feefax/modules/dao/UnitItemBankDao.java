package com.xcmis.feefax.modules.dao;

import com.xcmis.feefax.modules.entity.UnitItemBank;
import com.xcmis.feefax.modules.entity.UnitItemBillForm;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface UnitItemBankDao extends IBaseDao<UnitItemBank> {

    public long getUnitItemBankListTotal(UnitItemBank unitItemBank);

    public List<UnitItemBank> getUnitItemBankListByCondition(Map<String, Object> map);

    public List<UnitItemBank> getUnitItemBankByenIdAndBilltypeId(UnitItemBank unitItemBank);

    public List<UnitItemBank> getUnitItemBankByenId(UnitItemBank unitItemBank);

}
