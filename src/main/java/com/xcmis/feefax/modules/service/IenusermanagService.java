package com.xcmis.feefax.modules.service;

import com.xcmis.feefax.modules.dao.IenusermanagDao;
import com.xcmis.feefax.modules.entity.Ienusermanag;
import com.xcmis.framework.common.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class IenusermanagService {
	@Autowired
	private IenusermanagDao ienusermanagDao;

	public List<Ienusermanag> findAllList(Ienusermanag ienusermanag){
		try{
			List<Ienusermanag> list = ienusermanagDao.findAllList(ienusermanag);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}


	public List<Ienusermanag> findList(Ienusermanag ienusermanag){
		try{
			List<Ienusermanag> list = ienusermanagDao.findList(ienusermanag);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Transactional(rollbackFor=Exception.class)
	public boolean insert(List<Ienusermanag> list, String ienId) {
		Ienusermanag ienusermanag = new Ienusermanag();
		ienusermanag.setIenId(ienId);
		ienusermanagDao.delete(ienusermanag);

		for(Ienusermanag e : list){
			e.setSetYear(DateTimeUtils.getCurrentYear());
			e.setLastVer(DateTimeUtils.getDateTimeStr1());
			ienusermanagDao.insert(e);
		}
		return true;
	}

	@Transactional(rollbackFor=Exception.class)
	public boolean delete(List<Ienusermanag> list) {
		for(Ienusermanag e : list){
			ienusermanagDao.delete(e);
		}
		return true;
	}
}
