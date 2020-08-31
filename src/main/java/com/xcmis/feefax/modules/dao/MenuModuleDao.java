package com.xcmis.feefax.modules.dao;

        import com.xcmis.feefax.modules.entity.MenuModule;
        import com.xcmis.framework.common.persistence.IBaseDao;
        import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

        import java.util.List;


@MyBatisDao
public interface MenuModuleDao extends IBaseDao<MenuModule> {
    public List<MenuModule> getMenuModuleListByMenuId(MenuModule menuModule);

    public List<MenuModule> getMenuModuleListByMenuIdAndDisplayOrder(MenuModule menuModule);
}
