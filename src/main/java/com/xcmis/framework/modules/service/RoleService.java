package com.xcmis.framework.modules.service;


import java.util.List;
import java.util.Map;

import com.xcmis.framework.modules.dao.RoleMenuButtonDao;
import com.xcmis.framework.modules.dao.RoleMenuDao;
import com.xcmis.framework.modules.dao.RoleMenuModuleDao;
import com.xcmis.framework.modules.entity.RoleMenu;
import com.xcmis.framework.modules.entity.RoleMenuButton;
import com.xcmis.framework.modules.entity.RoleMenuModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xcmis.framework.modules.dao.RoleDao;
import com.xcmis.framework.modules.entity.Role;



@Service
@Transactional(readOnly = true)
public class RoleService {
	@Autowired
	private RoleDao roleDao;

	@Autowired
	private RoleMenuDao roleMenuDao;

	@Autowired
	private RoleMenuButtonDao roleMenuButtonDao;

	@Autowired
	private RoleMenuModuleDao roleMenuModuleDao;
	
	@Transactional(readOnly = false)
	public boolean insert(Role role){
		try{
			int row = roleDao.insert(role);
			if(row > 0){
				return true;
			}
			return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	@Transactional(readOnly = false)
	public boolean update(Role role){
		try{
			int row = roleDao.update(role);
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
	public boolean delete(Role role){
		RoleMenuButton roleMenuButton = new RoleMenuButton();
		roleMenuButton.setRoleId(Long.parseLong(role.getRoleId()));
		roleMenuButtonDao.delete(roleMenuButton);

		RoleMenuModule roleMenuModule = new RoleMenuModule();
		roleMenuModule.setRoleId(Long.parseLong(role.getRoleId()));
		roleMenuModuleDao.delete(roleMenuModule);

		RoleMenu roleMenu = new RoleMenu();
		roleMenu.setRoleId(Long.parseLong(role.getRoleId()));
		roleMenuDao.delete(roleMenu);

		int row = roleDao.delete(role);
		if(row > 0) {
			return true;
		}else{
			return false;
		}
	}
	
	
	public Role get(Role role){
		try{
			Role p = roleDao.get(role);
			return p;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Role> findAllList(Role role){
		try{
			List<Role> list = roleDao.findAllList(role);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<Role> getRoleListByCondition(Map<String, Object> map){
		try{
			List<Role> list = roleDao.getRoleListByCondition(map);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public long getRoleListTotal(Role role){
		try{
			long total = roleDao.getRoleListTotal(role);
			return total;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	
}
