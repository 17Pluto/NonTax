package com.xcmis.feefax.modules.service;

import com.xcmis.feefax.modules.dao.BugetLevelDao;
import com.xcmis.feefax.modules.entity.BugetLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional(readOnly = true)
public class BugetLevelService {
	@Autowired
	private BugetLevelDao bugetLevelDao;
	
	@Transactional(readOnly = false)
	public boolean insert(BugetLevel bugetLevel){
		try{
			int row = bugetLevelDao.insert(bugetLevel);
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
	public String insertReturnId(BugetLevel bugetLevel) {
		try {
			int row = bugetLevelDao.insert(bugetLevel);
			if (row > 0) {
				return bugetLevel.getChrId();
			}
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	@Transactional(readOnly = false)
	public boolean update(BugetLevel bugetLevel){
		try{
			int row = bugetLevelDao.update(bugetLevel);
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
	public boolean delete(BugetLevel bugetLevel){
		try{
			int row = bugetLevelDao.delete(bugetLevel);
			if(row > 0){
				return true;
			}
			return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public List<BugetLevel> findAllList(BugetLevel bugetLevel){
		try{
			List<BugetLevel> list = bugetLevelDao.findAllList(bugetLevel);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public List<BugetLevel> findList(BugetLevel bugetLevel){
		try{
			List<BugetLevel> list = bugetLevelDao.findList(bugetLevel);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public BugetLevel get(BugetLevel bugetLevel){
		try{
			BugetLevel bt = bugetLevelDao.get(bugetLevel);
			return bt;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<BugetLevel> getBugetLevelListByCondition(Map<String, Object> map){
		try{
			List<BugetLevel> list = bugetLevelDao.getBugetLevelListByCondition(map);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public long getBugetLevelListTotal(BugetLevel bugetLevel){
		try{
			long total = bugetLevelDao.getBugetLevelListTotal(bugetLevel);
			return total;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}	
	
}
