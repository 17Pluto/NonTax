package com.xcmis.feefax.modules.service;

import com.xcmis.feefax.modules.dao.RegionDao;
import com.xcmis.feefax.modules.entity.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class RegionService {
	@Autowired
	private RegionDao regionDao;

	public Region get(Region region){
		try{
			region = regionDao.get(region);
			return region;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
