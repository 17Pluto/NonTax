package com.xcmis.feefax.modules.service;

import com.xcmis.feefax.modules.dao.IncomeItemDao;
import com.xcmis.feefax.modules.entity.IncomeItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional(readOnly = true)
public class IncomeItemService {
	@Autowired
	private IncomeItemDao incomeItemDao;
	
	@Transactional(readOnly = false)
	public boolean insert(IncomeItem incomeItem){
		try{
			int row = incomeItemDao.insert(incomeItem);
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
	public String insertReturnId(IncomeItem incomeItem) {
		try {
			int row = incomeItemDao.insert(incomeItem);
			if (row > 0) {
				return incomeItem.getChrId();
			}
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	@Transactional(readOnly = false)
	public boolean update(IncomeItem incomeItem){
		try{
			int row = incomeItemDao.update(incomeItem);
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
	public boolean delete(IncomeItem incomeItem){
		try{
			int row = incomeItemDao.delete(incomeItem);
			if(row > 0){
				return true;
			}
			return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public List<IncomeItem> findAllList(IncomeItem incomeItem){
		try{
			List<IncomeItem> list = incomeItemDao.findAllList(incomeItem);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public List<IncomeItem> findList(IncomeItem incomeItem){
		try{
			List<IncomeItem> list = incomeItemDao.findList(incomeItem);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public List<IncomeItem> getIncomeItemListByChrId(IncomeItem incomeItem){
		try{
			List<IncomeItem> list = incomeItemDao.getIncomeItemListByChrId(incomeItem);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public IncomeItem get(IncomeItem incomeItem){
		try{
			IncomeItem bt = incomeItemDao.get(incomeItem);
			return bt;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public IncomeItem showIncomeItem(IncomeItem incomeItem){
		try{
			IncomeItem bt = incomeItemDao.showIncomeItem(incomeItem);
			return bt;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<IncomeItem> getIncomeItemListByCondition(Map<String, Object> map){
		try{
			List<IncomeItem> list = incomeItemDao.getIncomeItemListByCondition(map);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public long getIncomeItemListTotal(IncomeItem incomeItem){
		try{
			long total = incomeItemDao.getIncomeItemListTotal(incomeItem);
			return total;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
}
