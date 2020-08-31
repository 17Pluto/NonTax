package com.xcmis.framework.modules.service;

import com.xcmis.framework.modules.dao.ResourceDao;
import com.xcmis.framework.modules.entity.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional(readOnly = true)
public class ResourceService {
	@Autowired
	private ResourceDao resourceDao;

	public List<Resource> getResourceListByUserName(Resource resource){
		try{
			List<Resource> list = resourceDao.getResourceListByUserName(resource);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
