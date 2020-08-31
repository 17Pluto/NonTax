package com.xcmis.feefax.modules.service;

import com.xcmis.feefax.modules.dao.StatusDao;
import com.xcmis.feefax.modules.entity.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class StatusService {
	@Autowired
	private StatusDao statusDao;

	public List<Status> findAllList(Status status){
		try{
			List<Status> list = statusDao.findAllList(status);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public List<Status> getStatusListByModuleId(Status status){
		try{
			List<Status> list = statusDao.getStatusListByModuleId(status);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Transactional(readOnly = false)
	public String insertReturnId(Status status) {
		try {
			int row = statusDao.insert(status);
			if (row > 0) {
				return status.getStatusId();
			}
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	@Transactional(readOnly = false)
	public boolean delete(Status status){
		try{
			int row = statusDao.delete(status);
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
