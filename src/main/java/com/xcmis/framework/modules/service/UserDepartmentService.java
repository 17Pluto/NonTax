package com.xcmis.framework.modules.sys.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xcmis.framework.modules.dao.UserDepartmentDao;
import com.xcmis.framework.modules.entity.UserDepartment;



@Service
@Transactional(readOnly = true)
public class UserDepartmentService {
	@Autowired
	private UserDepartmentDao userDepartmentDao;
	
	@Transactional(readOnly = false)
	public boolean insert(UserDepartment userDepartment){
		try{
			int row = userDepartmentDao.insert(userDepartment);
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
	public boolean delete(UserDepartment userDepartment){
		try{
			int row = userDepartmentDao.delete(userDepartment);
			if(row > 0){
				return true;
			}
			return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public List<UserDepartment> findAllList(UserDepartment userDepartment){
		try{
			List<UserDepartment> list = userDepartmentDao.findAllList(userDepartment);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
