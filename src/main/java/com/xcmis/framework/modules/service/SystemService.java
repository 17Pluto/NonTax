package com.xcmis.framework.modules.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xcmis.framework.modules.dao.BaseClassDao;
import com.xcmis.framework.modules.dao.BaseCodeDao;
import com.xcmis.framework.modules.entity.BaseClass;
import com.xcmis.framework.modules.entity.BaseCode;


@Service
@Transactional(readOnly = true)
public class SystemService {
	@Autowired
	private BaseClassDao baseClassDao;

	@Autowired
	private BaseCodeDao baseCodeDao;

	public List<BaseClass> findBaseClassList(BaseClass baseClass) {
		try {
			List<BaseClass> list = baseClassDao.findAllList(baseClass);
			if (list != null) {
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<BaseCode> getBaseCodeListByBaseClass(String baseClassCode) {
		try {
			List<BaseCode> list = baseCodeDao
					.getBaseCodeListByBaseClass(baseClassCode);
			if (list != null) {
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public BaseCode getBaseCodeById(String id) {
		try {
			BaseCode baseCode = baseCodeDao
					.get(id);
			return baseCode;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public List<BaseCode> getBaseCodeListByBaseClassAndDepartmentId(BaseCode baseCode) {
		try {
			List<BaseCode> list = baseCodeDao
					.getBaseCodeListByBaseClassAndDepartmentId(baseCode);
			if (list != null) {
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@Transactional(readOnly = false)
	public boolean insertBaseCode(BaseCode baseCode) {
		try {
			int row = baseCodeDao.insert(baseCode);
			if (row > 0) {
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	
	@Transactional(readOnly = false)
	public boolean updateBaseCode(BaseCode baseCode){
		try{
			int row = baseCodeDao.update(baseCode);
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
	public boolean deleteBaseCode(BaseCode baseCode){
		try{
			int row = baseCodeDao.delete(baseCode);
			if(row > 0){
				return true;
			}
			return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public List<BaseCode> getBaseCodeListByCondition(Map<String, Object> map){
		try{
			List<BaseCode> list = baseCodeDao.getBaseCodeListByCondition(map);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public long getBaseCodeListTotal(BaseCode baseCode){
		try{
			long total = baseCodeDao.getBaseCodeListTotal(baseCode);
			return total;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
		
	}
}
