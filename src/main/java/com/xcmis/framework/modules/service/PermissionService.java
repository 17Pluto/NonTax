package com.xcmis.framework.modules.service;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xcmis.framework.modules.dao.PermissionDao;
import com.xcmis.framework.modules.entity.Permission;


@Service
@Transactional(readOnly = true)
public class PermissionService {
	@Autowired
	private PermissionDao permissionDao;
	
	@Transactional(readOnly = false)
	public boolean insert(Permission permission){
		try{
			int row = permissionDao.insert(permission);
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
	public boolean update(Permission permission){
		try{
			int row = permissionDao.update(permission);
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
	public boolean delete(Permission permission){
		try{
			int row = permissionDao.delete(permission);
			if(row > 0){
				return true;
			}
			return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	
	public Permission get(Permission permission){
		try{
			Permission p = permissionDao.get(permission);
			return p;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Permission> findAllList(Permission permission){
		try{
			List<Permission> list = permissionDao.findAllList(permission);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	public List<Permission> getPermissionListByCondition(Map<String, Object> map){
		try{
			List<Permission> list = permissionDao.getPermissionListByCondition(map);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public long getPermissionListTotal(Permission permission){
		try{
			long total = permissionDao.getPermissionListTotal(permission);
			return total;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	
}
