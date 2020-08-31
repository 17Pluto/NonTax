package com.xcmis.framework.modules.service;


import com.xcmis.framework.modules.dao.RoleMenuButtonDao;
import com.xcmis.framework.modules.entity.RoleMenuButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class RoleMenuButtonService {
	@Autowired
	private RoleMenuButtonDao roleMenuButtonDao;
	
	@Transactional(readOnly = false)
	public boolean insert(RoleMenuButton roleMenuButton){
		try{
			int row = roleMenuButtonDao.insert(roleMenuButton);
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
	public boolean delete(RoleMenuButton roleMenuButton){
		try{
			int row = roleMenuButtonDao.delete(roleMenuButton);
			if(row > 0){
				return true;
			}
			return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	
	public List<RoleMenuButton> findAllList(RoleMenuButton roleMenuButton){
		try{
			List<RoleMenuButton> list = roleMenuButtonDao.findAllList(roleMenuButton);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
