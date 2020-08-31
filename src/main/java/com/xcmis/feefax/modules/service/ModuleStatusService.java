package com.xcmis.feefax.modules.service;

import com.xcmis.feefax.modules.dao.ModuleStatusDao;
import com.xcmis.feefax.modules.entity.ModuleStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class ModuleStatusService {
	@Autowired
	private ModuleStatusDao moduleStatusDao;

	@Transactional(readOnly = false)
	public boolean insert(ModuleStatus moduleStatus){
		try{
			int row = moduleStatusDao.insert(moduleStatus);
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
	public boolean delete(ModuleStatus moduleStatus){
		try{
			int row = moduleStatusDao.delete(moduleStatus);
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
