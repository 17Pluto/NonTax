package com.xcmis.feefax.modules.service;

import com.xcmis.feefax.modules.dao.ManageBranchDao;
import com.xcmis.feefax.modules.entity.ManageBranch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional(readOnly = true)
public class ManageBranchService {
	@Autowired
	private ManageBranchDao manageBranchDao;
	
	@Transactional(readOnly = false)
	public boolean insert(ManageBranch manageBranch){
		try{
			int row = manageBranchDao.insert(manageBranch);
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
	public String insertReturnId(ManageBranch manageBranch) {
		try {
			int row = manageBranchDao.insert(manageBranch);
			if (row > 0) {
				return manageBranch.getChrId();
			}
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	@Transactional(readOnly = false)
	public boolean update(ManageBranch manageBranch){
		try{
			int row = manageBranchDao.update(manageBranch);
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
	public boolean delete(ManageBranch manageBranch){
		try{
			int row = manageBranchDao.delete(manageBranch);
			if(row > 0){
				return true;
			}
			return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public List<ManageBranch> findAllList(ManageBranch manageBranch){
		try{
			List<ManageBranch> list = manageBranchDao.findAllList(manageBranch);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public List<ManageBranch> getManageBranchListByChrId(ManageBranch manageBranch){
		try{
			List<ManageBranch> list = manageBranchDao.getManageBranchListByChrId(manageBranch);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public List<ManageBranch> findList(ManageBranch manageBranch){
		try{
			List<ManageBranch> list = manageBranchDao.findList(manageBranch);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public ManageBranch get(ManageBranch manageBranch){
		try{
			ManageBranch bt = manageBranchDao.get(manageBranch);
			return bt;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<ManageBranch> getManageBranchListByCondition(Map<String, Object> map){
		try{
			List<ManageBranch> list = manageBranchDao.getManageBranchListByCondition(map);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public long getManageBranchListTotal(ManageBranch manageBranch){
		try{
			long total = manageBranchDao.getManageBranchListTotal(manageBranch);
			return total;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}	
	
}
