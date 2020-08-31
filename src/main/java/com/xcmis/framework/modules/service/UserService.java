package com.xcmis.framework.modules.service;

import java.util.List;
import java.util.Map;

import com.xcmis.framework.modules.dao.UserOrgDao;
import com.xcmis.framework.modules.dao.UserRoleRuleDao;
import com.xcmis.framework.modules.dao.UserRuleDao;
import com.xcmis.framework.modules.entity.UserOrg;
import com.xcmis.framework.modules.entity.UserRoleRule;
import com.xcmis.framework.modules.entity.UserRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xcmis.framework.modules.dao.UserDao;
import com.xcmis.framework.modules.entity.User;


@Service
@Transactional(readOnly = true)
public class UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private UserRuleDao userRuleDao;

	@Autowired
	private UserRoleRuleDao userRoleRuleDao;

	@Autowired
	private UserOrgDao userOrgDao;

	public User get(User user) {
		try {
			User u = userDao.get(user);
			if (u != null) {
				return u;
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public User findByUsername(String username) {
		try {
			User u = userDao.findByUsername(username);
			if (u != null) {
				return u;
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public User findByUserId(User user) {
		try {
			User u = userDao.get(user);
			if (u != null) {
				return u;
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<User> findList(User user){
		try {
			List<User> list = userDao.findList(user);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<User> findAllList(User user){
		try {
			List<User> list = userDao.findAllList(user);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<User> getBelongOrgList(User user){
		try {
			List<User> list = userDao.getBelongOrgList(user);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	public List<User> getUserListByDepartmentId(String departmentId) {
		try {
			List<User> list = userDao.getUserListByDepartmentId(departmentId);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	@Transactional(rollbackFor=Exception.class)
	public boolean insert(User user) {
		String userId = "";
		String ruleId = "";
		int row = userDao.insert(user);
		if (row > 0) {
			userId = user.getUserId();

			for(UserOrg userOrg : user.getUserOrgList()){
				userOrg.setLastVer(user.getLastVer());
				userOrg.setSetYear(user.getSetYear());
				userOrg.setUserId(userId);
				userOrgDao.insert(userOrg);
			}

			for(UserRule userRule : user.getUserRuleList())
			{
				userRule.setLastVer(user.getLastVer());
				userRule.setSetYear(user.getSetYear());
				userRule.setRgCode(user.getRgCode());
				userRule.setUserId(userId);
				userRuleDao.insert(userRule);
				ruleId = userRule.getRuleId();
			}
			for(UserRoleRule userRoleRule : user.getUserRoleRuleList()){
				if(!ruleId.equals("")) {
					userRoleRule.setRuleId(ruleId);
				}
				userRoleRule.setLastVer(user.getLastVer());
				userRoleRule.setSetYear(user.getSetYear());
				userRoleRule.setRgCode(user.getRgCode());
				userRoleRule.setUserId(userId);
				userRoleRuleDao.insert(userRoleRule);
			}

			return true;
		}else{
			return false;
		}
	}


	@Transactional(rollbackFor=Exception.class)
	public boolean update(User user) {
		String userId = "";
		String ruleId = "";
		int row = userDao.update(user);
		if (row > 0) {
			userId = user.getUserId();

			UserOrg uo = new UserOrg();
			uo.setUserId(userId);
			userOrgDao.delete(uo);

			for(UserOrg userOrg : user.getUserOrgList()){
				userOrg.setLastVer(user.getLastVer());
				userOrg.setSetYear(user.getSetYear());
				userOrg.setUserId(userId);
				userOrgDao.insert(userOrg);
			}

			UserRule ur = new UserRule();
			ur.setUserId(userId);
			userRuleDao.delete(ur);

			for(UserRule userRule : user.getUserRuleList())
			{
				userRule.setLastVer(user.getLastVer());
				userRule.setSetYear(user.getSetYear());
				userRule.setRgCode(user.getRgCode());
				userRule.setUserId(userId);
				userRuleDao.insert(userRule);
				ruleId = userRule.getRuleId();
			}

			UserRoleRule urr = new UserRoleRule();
			urr.setUserId(userId);
			userRoleRuleDao.delete(urr);

			for(UserRoleRule userRoleRule : user.getUserRoleRuleList()){
				if(!ruleId.equals("")) {
					userRoleRule.setRuleId(ruleId);
				}
				userRoleRule.setLastVer(user.getLastVer());
				userRoleRule.setSetYear(user.getSetYear());
				userRoleRule.setRgCode(user.getRgCode());
				userRoleRule.setUserId(userId);
				userRoleRuleDao.insert(userRoleRule);
			}

			return true;
		}else{
			return false;
		}
	}

	@Transactional(rollbackFor=Exception.class)
	public boolean delete(User user) {
		String userId = user.getUserId();
		int row = userDao.delete(user);
		if (row > 0) {
			UserOrg uo = new UserOrg();
			uo.setUserId(userId);
			userOrgDao.delete(uo);

			UserRule ur = new UserRule();
			ur.setUserId(userId);
			userRuleDao.delete(ur);


			UserRoleRule urr = new UserRoleRule();
			urr.setUserId(userId);
			userRoleRuleDao.delete(urr);

			return true;
		}else{
			return false;
		}
	}

	@Transactional(readOnly = false)
	public boolean editPassword(User user) {
		try {
			int row = userDao.editPassword(user);
			if (row > 0) {
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public List<User> getUserListByCondition(Map<String, Object> map){
		try{
			List<User> list = userDao.getUserListByCondition(map);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public long getUserListTotal(User user){
		try{
			long total = userDao.getUserListTotal(user);
			return total;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
}
