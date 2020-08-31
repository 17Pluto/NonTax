package com.xcmis.framework.modules.service;

import com.xcmis.framework.modules.dao.UserRuleDao;
import com.xcmis.framework.modules.entity.UserRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class UserRuleService {
	@Autowired
	private UserRuleDao userRuleDao;

	public List<UserRule> findAllList(UserRule userRule){
		try{
			List<UserRule> list = userRuleDao.findAllList(userRule);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Transactional(readOnly = false)
	public boolean insert(UserRule userRule){
		try{
			int row = userRuleDao.insert(userRule);
			if(row > 0){
				return true;
			}
			return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public UserRule get(UserRule userRule){
		try{
			UserRule r = userRuleDao.get(userRule);
			return r;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}


	@Transactional(readOnly = false)
	public boolean delete(UserRule userRule){
		try{
			int row = userRuleDao.delete(userRule);
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
