package com.xcmis.feefax.modules.service;

import com.xcmis.feefax.modules.dao.MenuDao;
import com.xcmis.feefax.modules.entity.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class MenuService {
	@Autowired
	private MenuDao menuDao;

	public List<Menu> findAllList(Menu menu){
		try{
			List<Menu> list = menuDao.findAllList(menu);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public List<Menu> getMenuListByUserId(Menu menu){
		try{
			List<Menu> list = menuDao.getMenuListByUserId(menu);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public Menu get(Menu menu){
		try{
			Menu m = menuDao.get(menu);
			return m;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Transactional(readOnly = false)
	public long insertReturnId(Menu menu) {
		try {
			int row = menuDao.insert(menu);
			if (row > 0) {
				return menu.getMenuId();
			}
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Transactional(readOnly = false)
	public boolean update(Menu menu){
		try{
			int row = menuDao.update(menu);
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
	public boolean delete(Menu menu){
		try{
			int row = menuDao.delete(menu);
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
