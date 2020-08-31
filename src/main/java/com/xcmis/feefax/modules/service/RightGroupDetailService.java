package com.xcmis.feefax.modules.service;

import com.xcmis.feefax.modules.dao.RightGroupDetailDao;
import com.xcmis.feefax.modules.entity.RightGroupDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class RightGroupDetailService {
	@Autowired
	private RightGroupDetailDao rightGroupDetailDao;

	public List<RightGroupDetail> findAllList(RightGroupDetail rightGroupDetail){
		try{
			List<RightGroupDetail> list = rightGroupDetailDao.findAllList(rightGroupDetail);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Transactional(readOnly = false)
	public boolean insert(RightGroupDetail rightGroupDetail){
		try{
			int row = rightGroupDetailDao.insert(rightGroupDetail);
			if(row > 0){
				return true;
			}
			return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public RightGroupDetail get(RightGroupDetail rightGroupDetail){
		try{
			RightGroupDetail r = rightGroupDetailDao.get(rightGroupDetail);
			return r;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Transactional(readOnly = false)
	public boolean update(RightGroupDetail rightGroupDetail){
		try{
			int row = rightGroupDetailDao.update(rightGroupDetail);
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
	public boolean delete(RightGroupDetail rightGroupDetail){
		try{
			int row = rightGroupDetailDao.delete(rightGroupDetail);
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
