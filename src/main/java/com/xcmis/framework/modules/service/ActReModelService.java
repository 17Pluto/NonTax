package com.xcmis.framework.modules.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xcmis.framework.modules.dao.ActReModelDao;
import com.xcmis.framework.modules.entity.ActReModel;


@Service
@Transactional(readOnly = true)
public class ActReModelService {
	@Autowired
	private ActReModelDao actReModelDao;
	
	public List<ActReModel> getActReModelListByCondition(Map<String, Object> map){
		try{
			List<ActReModel> list = actReModelDao.getActReModelListByCondition(map);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public long getActReModelListTotal(ActReModel actReModel){
		try{
			long total = actReModelDao.getActReModelListTotal(actReModel);
			return total;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}	
}
