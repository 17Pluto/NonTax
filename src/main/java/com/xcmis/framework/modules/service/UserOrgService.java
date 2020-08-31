package com.xcmis.framework.modules.service;

import com.xcmis.framework.modules.dao.UserOrgDao;
import com.xcmis.framework.modules.entity.UserOrg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class UserOrgService {
	@Autowired
	private UserOrgDao userOrgDao;

	public List<UserOrg> findAllList(UserOrg userOrg){
		try{
			List<UserOrg> list = userOrgDao.findAllList(userOrg);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Transactional(readOnly = false)
	public boolean insert(UserOrg userOrg){
		try{
			int row = userOrgDao.insert(userOrg);
			if(row > 0){
				return true;
			}
			return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public UserOrg get(UserOrg userOrg){
		try{
			UserOrg r = userOrgDao.get(userOrg);
			return r;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Transactional(readOnly = false)
	public boolean update(UserOrg userOrg){
		try{
			int row = userOrgDao.update(userOrg);
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
	public boolean delete(UserOrg userOrg){
		try{
			int row = userOrgDao.delete(userOrg);
			if(row > 0){
				return true;
			}
			return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
}
