package com.xcmis.framework.modules.sys.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xcmis.framework.modules.dao.RolePermissionDao;
import com.xcmis.framework.modules.entity.RolePermission;




@Service
@Transactional(readOnly = true)
public class RolePermissionService {
	@Autowired
	private RolePermissionDao rolePermissionDao;
	
	@Transactional(readOnly = false)
	public boolean insert(RolePermission rolePermission){
		try{
			int row = rolePermissionDao.insert(rolePermission);
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
	public boolean delete(RolePermission rolePermission){
		try{
			int row = rolePermissionDao.delete(rolePermission);
			if(row > 0){
				return true;
			}
			return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public List<RolePermission> findAllList(RolePermission rolePermission){
		try{
			List<RolePermission> list = rolePermissionDao.findAllList(rolePermission);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
