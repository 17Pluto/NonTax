package com.xcmis.feefax.modules.service;

import com.xcmis.feefax.modules.dao.MenuModuleDao;
import com.xcmis.feefax.modules.entity.MenuModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class MenuModuleService {
	@Autowired
	private MenuModuleDao menuModuleDao;

	@Transactional(readOnly = false)
	public boolean insert(MenuModule menuModule){
		try{
			int row = menuModuleDao.insert(menuModule);
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
	public boolean delete(MenuModule menuModule){
		try{
			int row = menuModuleDao.delete(menuModule);
			if(row > 0){
				return true;
			}
			return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public List<MenuModule> getMenuModuleListByMenuId(MenuModule menuModule){
		try{
			List<MenuModule> list = menuModuleDao.getMenuModuleListByMenuId(menuModule);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}


	public List<MenuModule> getMenuModuleListByMenuIdAndDisplayOrder(MenuModule menuModule){
		try{
			List<MenuModule> list = menuModuleDao.getMenuModuleListByMenuIdAndDisplayOrder(menuModule);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
