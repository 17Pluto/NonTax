package com.xcmis.feefax.modules.service;

import com.xcmis.feefax.modules.dao.OrgtypeDao;
import com.xcmis.feefax.modules.entity.Orgtype;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class OrgtypeService {
	@Autowired
	private OrgtypeDao orgtypeDao;

	public List<Orgtype> findAllList(Orgtype orgtype){
		try{
			List<Orgtype> list = orgtypeDao.findAllList(orgtype);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
