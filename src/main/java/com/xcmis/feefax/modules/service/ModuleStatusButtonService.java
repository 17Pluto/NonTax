package com.xcmis.feefax.modules.service;

import com.xcmis.feefax.modules.dao.ModuleStatusButtonDao;
import com.xcmis.feefax.modules.entity.ModuleStatusButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class ModuleStatusButtonService {
	@Autowired
	private ModuleStatusButtonDao moduleStatusButtonDao;

	@Transactional(readOnly = false)
	public boolean insert(ModuleStatusButton moduleStatusButton){
		try{
			int row = moduleStatusButtonDao.insert(moduleStatusButton);
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
	public boolean delete(ModuleStatusButton moduleStatusButton){
		try{
			int row = moduleStatusButtonDao.delete(moduleStatusButton);
			if(row > 0){
				return true;
			}
			return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public List<ModuleStatusButton> findAllList(ModuleStatusButton moduleStatusButton){
		try{
			List<ModuleStatusButton> list = moduleStatusButtonDao.findAllList(moduleStatusButton);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
