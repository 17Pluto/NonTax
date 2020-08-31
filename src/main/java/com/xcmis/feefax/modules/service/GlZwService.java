package com.xcmis.feefax.modules.service;

import com.xcmis.feefax.modules.dao.GlZwDao;
import com.xcmis.feefax.modules.entity.GlZw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class GlZwService {
	@Autowired
	private GlZwDao glZwDao;

	public List<GlZw> findAllList(GlZw glZw){
		try{
			List<GlZw> list = glZwDao.findAllList(glZw);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
