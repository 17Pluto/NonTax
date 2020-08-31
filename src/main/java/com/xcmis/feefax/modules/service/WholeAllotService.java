package com.xcmis.feefax.modules.service;

import com.xcmis.feefax.modules.dao.WholeAllotDao;
import com.xcmis.feefax.modules.entity.WholeAllot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional(readOnly = true)
public class WholeAllotService {
	@Autowired
	private WholeAllotDao wholeAllotDao;
	
	@Transactional(readOnly = false)
	public boolean insert(WholeAllot wholeAllot){
		try{
			int row = wholeAllotDao.insert(wholeAllot);
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
	public String insertReturnId(WholeAllot wholeAllot) {
		try {
			int row = wholeAllotDao.insert(wholeAllot);
			if (row > 0) {
				return wholeAllot.getChrId();
			}
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	@Transactional(readOnly = false)
	public boolean update(WholeAllot wholeAllot){
		try{
			int row = wholeAllotDao.update(wholeAllot);
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
	public boolean delete(WholeAllot wholeAllot){
		try{
			int row = wholeAllotDao.delete(wholeAllot);
			if(row > 0){
				return true;
			}
			return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public List<WholeAllot> findAllList(WholeAllot wholeAllot){
		try{
			List<WholeAllot> list = wholeAllotDao.findAllList(wholeAllot);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public List<WholeAllot> findList(WholeAllot wholeAllot){
		try{
			List<WholeAllot> list = wholeAllotDao.findList(wholeAllot);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public WholeAllot get(WholeAllot wholeAllot){
		try{
			WholeAllot bt = wholeAllotDao.get(wholeAllot);
			return bt;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<WholeAllot> getWholeAllotListByCondition(Map<String, Object> map){
		try{
			List<WholeAllot> list = wholeAllotDao.getWholeAllotListByCondition(map);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public long getWholeAllotListTotal(WholeAllot wholeAllot){
		try{
			long total = wholeAllotDao.getWholeAllotListTotal(wholeAllot);
			return total;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}	
	
}
