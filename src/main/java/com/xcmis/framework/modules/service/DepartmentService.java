package com.xcmis.framework.modules.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xcmis.framework.modules.dao.DepartmentDao;
import com.xcmis.feefax.modules.entity.Enterprise;


@Service
@Transactional(readOnly = true)
public class DepartmentService {
	@Autowired
	private DepartmentDao departmentDao;
	
	@Transactional(readOnly = false)
	public boolean insert(Enterprise department){
		try{
			int row = departmentDao.insert(department);
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
	public boolean update(Enterprise department){
		try{
			int row = departmentDao.update(department);
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
	public boolean delete(Enterprise department){
		try{
			int row = departmentDao.delete(department);
			if(row > 0){
				return true;
			}
			return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public List<Enterprise> findAllList(Enterprise department){
		try{
			List<Enterprise> list = departmentDao.findAllList(department);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Enterprise> findList(Enterprise department){
		try{
			List<Enterprise> list = departmentDao.findList(department);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public Enterprise getDepartment(Enterprise department){
		try{
			Enterprise d = departmentDao.get(department);
			return d;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<Enterprise> getDepartmentListByCondition(Map<String, Object> map){
		try{
			List<Enterprise> list = departmentDao.getDepartmentListByCondition(map);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public long getDepartmentListTotal(Enterprise department){
		try{
			long total = departmentDao.getDepartmentListTotal(department);
			return total;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}	
	
	
	public List<Enterprise> getDepartmentListByUser(String userId){
		try{
			List<Enterprise> list = departmentDao.getDepartmentListByUser(userId);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
