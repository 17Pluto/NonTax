package com.xcmis.feefax.modules.service;

import com.xcmis.feefax.modules.dao.EnterpriseDao;
import com.xcmis.feefax.modules.entity.Enterprise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional(readOnly = true)
public class EnterpriseService {
	@Autowired
	private EnterpriseDao enterpriseDao;
	
	@Transactional(readOnly = false)
	public boolean insert(Enterprise enterprise){
		try{
			int row = enterpriseDao.insert(enterprise);
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
	public String insertReturnId(Enterprise enterprise) {
		try {
			int row = enterpriseDao.insert(enterprise);
			if (row > 0) {
				return enterprise.getChrId();
			}
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	@Transactional(readOnly = false)
	public boolean update(Enterprise enterprise){
		try{
			int row = enterpriseDao.update(enterprise);
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
	public boolean delete(Enterprise enterprise){
		try{
			int row = enterpriseDao.delete(enterprise);
			if(row > 0){
				return true;
			}
			return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public List<Enterprise> findAllList(Enterprise enterprise){
		try{
			List<Enterprise> list = enterpriseDao.findAllList(enterprise);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public List<Enterprise> findList(Enterprise enterprise){
		try{
			List<Enterprise> list = enterpriseDao.findList(enterprise);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Enterprise> getEnterpriseListByChrId(Enterprise enterprise){
		try{
			List<Enterprise> list = enterpriseDao.getEnterpriseListByChrId(enterprise);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public Enterprise get(Enterprise enterprise){
		try{
			Enterprise pt = enterpriseDao.get(enterprise);
			return pt;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<Enterprise> getEnterpriseListByCondition(Map<String, Object> map){
		try{
			List<Enterprise> list = enterpriseDao.getEnterpriseListByCondition(map);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public long getEnterpriseListTotal(Enterprise enterprise){
		try{
			long total = enterpriseDao.getEnterpriseListTotal(enterprise);
			return total;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}	
	
}
