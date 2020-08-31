package com.xcmis.feefax.modules.service;

import com.xcmis.feefax.modules.dao.ModuleDao;
import com.xcmis.feefax.modules.entity.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class ModuleService {
	@Autowired
	private ModuleDao moduleDao;

	public List<Module> getModuleListByMenuId(Module module){
		try{
			List<Module> list = moduleDao.getModuleListByMenuId(module);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public List<Module> findAllList(Module module){
		try{
			List<Module> list = moduleDao.findAllList(module);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public Module get(Module module){
		try{
			Module m = moduleDao.get(module);
			return m;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Transactional(readOnly = false)
	public long insertReturnId(Module module) {
		try {
			int row = moduleDao.insert(module);
			if (row > 0) {
				return module.getModuleId();
			}
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Transactional(readOnly = false)
	public boolean update(Module module){
		try{
			int row = moduleDao.update(module);
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
	public boolean delete(Module module){
		try{
			int row = moduleDao.delete(module);
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
