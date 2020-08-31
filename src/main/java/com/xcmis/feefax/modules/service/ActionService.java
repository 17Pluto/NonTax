package com.xcmis.feefax.modules.service;

import com.xcmis.feefax.modules.dao.ActionDao;
import com.xcmis.feefax.modules.entity.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class ActionService {
	@Autowired
	private ActionDao actionDao;

	public List<Action> findAllList(Action action){
		try{
			List<Action> list = actionDao.findAllList(action);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Transactional(readOnly = false)
	public String insertReturnId(Action action) {
		try {
			int row = actionDao.insert(action);
			if (row > 0) {
				return action.getActionId();
			}
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}
