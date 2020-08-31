package com.xcmis.feefax.modules.service;

import com.xcmis.feefax.modules.dao.RightGroupDao;
import com.xcmis.feefax.modules.entity.RightGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class RightGroupService {
	@Autowired
	private RightGroupDao rightGroupDao;

	public List<RightGroup> findAllList(RightGroup rightGroup){
		try{
			List<RightGroup> list = rightGroupDao.findAllList(rightGroup);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Transactional(readOnly = false)
	public String insertReturnId(RightGroup rightGroup) {
		try {
			int row = rightGroupDao.insert(rightGroup);
			if (row > 0) {
				return rightGroup.getRightGroupId();
			}
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public RightGroup get(RightGroup rightGroup){
		try{
			RightGroup r = rightGroupDao.get(rightGroup);
			return r;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Transactional(readOnly = false)
	public boolean update(RightGroup rightGroup){
		try{
			int row = rightGroupDao.update(rightGroup);
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
	public boolean delete(RightGroup rightGroup){
		try{
			int row = rightGroupDao.delete(rightGroup);
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
