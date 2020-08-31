package com.xcmis.feefax.modules.service;

import com.xcmis.feefax.modules.dao.AppDao;
import com.xcmis.feefax.modules.entity.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional(readOnly = true)
public class AppService {
	@Autowired
	private AppDao appDao;

	public List<App> findAllList(App app){
		try{
			List<App> list = appDao.findAllList(app);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
