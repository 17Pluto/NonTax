package com.xcmis.framework.modules.service;


import com.xcmis.framework.modules.dao.RoleMenuModuleDao;
import com.xcmis.framework.modules.entity.RoleMenuModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class RoleMenuModuleService {
	@Autowired
	private RoleMenuModuleDao roleMenuModuleDao;
	
	@Transactional(readOnly = false)
	public boolean insert(RoleMenuModule roleMenuModule){
		try{
			int row = roleMenuModuleDao.insert(roleMenuModule);
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
	public boolean delete(RoleMenuModule roleMenuModule){
		try{
			int row = roleMenuModuleDao.delete(roleMenuModule);
			if(row > 0){
				return true;
			}
			return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	
	public List<RoleMenuModule> findAllList(RoleMenuModule roleMenuModule){
		try{
			List<RoleMenuModule> list = roleMenuModuleDao.findAllList(roleMenuModule);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
