package com.xcmis.framework.modules.service;


import com.xcmis.framework.modules.dao.RoleMenuButtonDao;
import com.xcmis.framework.modules.dao.RoleMenuDao;
import com.xcmis.framework.modules.dao.RoleMenuModuleDao;
import com.xcmis.framework.modules.entity.RoleMenu;
import com.xcmis.framework.modules.entity.RoleMenuButton;
import com.xcmis.framework.modules.entity.RoleMenuModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional(readOnly = true)
public class RoleMenuService {
	@Autowired
	private RoleMenuDao roleMenuDao;

	@Autowired
	private RoleMenuButtonDao roleMenuButtonDao;

	@Autowired
	private RoleMenuModuleDao roleMenuModuleDao;

	
	@Transactional(readOnly = false)
	public boolean insert(RoleMenu roleMenu){
		try{
			int row = roleMenuDao.insert(roleMenu);
			if(row > 0){
				return true;
			}
			return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}


	@Transactional(rollbackFor=Exception.class)
	public boolean delete(RoleMenu roleMenu){
		int row = roleMenuDao.delete(roleMenu);
		if(row > 0){
			RoleMenuButton roleMenuButton = new RoleMenuButton();
			roleMenuButton.setRoleId(roleMenu.getRoleId());
			roleMenuButton.setMenuId(roleMenu.getMenuId());
			roleMenuButtonDao.delete(roleMenuButton);

			RoleMenuModule roleMenuModule = new RoleMenuModule();
			roleMenuModule.setRoleId(roleMenu.getRoleId());
			roleMenuModule.setMenuId(roleMenu.getMenuId());
			roleMenuModuleDao.delete(roleMenuModule);
			return true;
		}else{
			return false;
		}
	}

	
	public List<RoleMenu> findAllList(RoleMenu roleMenu){
		try{
			List<RoleMenu> list = roleMenuDao.findAllList(roleMenu);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
