package com.xcmis.framework.modules.service;

import com.xcmis.framework.modules.dao.UserRoleRuleDao;
import com.xcmis.framework.modules.entity.UserRoleRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class UserRoleRuleService {
	@Autowired
	private UserRoleRuleDao userRoleRuleDao;

	public List<UserRoleRule> findAllList(UserRoleRule userRoleRule){
		try{
			List<UserRoleRule> list = userRoleRuleDao.findAllList(userRoleRule);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Transactional(readOnly = false)
	public boolean insert(UserRoleRule userRoleRule){
		try{
			int row = userRoleRuleDao.insert(userRoleRule);
			if(row > 0){
				return true;
			}
			return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public UserRoleRule get(UserRoleRule userRoleRule){
		try{
			UserRoleRule r = userRoleRuleDao.get(userRoleRule);
			return r;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}


	@Transactional(readOnly = false)
	public boolean delete(UserRoleRule userRoleRule){
		try{
			int row = userRoleRuleDao.delete(userRoleRule);
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
