package com.xcmis.feefax.modules.service;

import com.xcmis.feefax.modules.dao.RightGroupTypeDao;
import com.xcmis.feefax.modules.entity.RightGroupType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class RightGroupTypeService {
	@Autowired
	private RightGroupTypeDao rightGroupTypeDao;

	public List<RightGroupType> findAllList(RightGroupType rightGroupType){
		try{
			List<RightGroupType> list = rightGroupTypeDao.findAllList(rightGroupType);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Transactional(readOnly = false)
	public boolean insert(RightGroupType rightGroupType){
		try{
			int row = rightGroupTypeDao.insert(rightGroupType);
			if(row > 0){
				return true;
			}
			return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public RightGroupType get(RightGroupType rightGroupType){
		try{
			RightGroupType r = rightGroupTypeDao.get(rightGroupType);
			return r;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Transactional(readOnly = false)
	public boolean update(RightGroupType rightGroupType){
		try{
			int row = rightGroupTypeDao.update(rightGroupType);
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
	public boolean delete(RightGroupType rightGroupType){
		try{
			int row = rightGroupTypeDao.delete(rightGroupType);
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
