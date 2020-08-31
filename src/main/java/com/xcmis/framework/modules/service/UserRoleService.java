 package com.xcmis.framework.modules.sys.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xcmis.framework.modules.dao.UserRoleDao;
import com.xcmis.framework.modules.entity.UserRole;


@Service
@Transactional(readOnly = true)
public class UserRoleService {
	@Autowired
	private UserRoleDao userRoleDao;
	
	@Transactional(readOnly = false)
	public boolean insert(UserRole userRole){
		try{
			int row = userRoleDao.insert(userRole);
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
	public boolean delete(UserRole userRole){
		try{
			int row = userRoleDao.delete(userRole);
			if(row > 0){
				return true;
			}
			return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public List<UserRole> findAllList(UserRole userRole){
		try{
			List<UserRole> list = userRoleDao.findAllList(userRole);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
