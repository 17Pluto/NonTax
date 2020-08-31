package com.xcmis.feefax.modules.service;

import com.xcmis.feefax.modules.dao.MoneyKindDao;
import com.xcmis.feefax.modules.entity.MoneyKind;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional(readOnly = true)
public class MoneyKindService {
	@Autowired
	private MoneyKindDao moneyKindDao;
	
	@Transactional(readOnly = false)
	public boolean insert(MoneyKind moneyKind){
		try{
			int row = moneyKindDao.insert(moneyKind);
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
	public String insertReturnId(MoneyKind moneyKind) {
		try {
			int row = moneyKindDao.insert(moneyKind);
			if (row > 0) {
				return moneyKind.getChrId();
			}
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	@Transactional(readOnly = false)
	public boolean update(MoneyKind moneyKind){
		try{
			int row = moneyKindDao.update(moneyKind);
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
	public boolean delete(MoneyKind moneyKind){
		try{
			int row = moneyKindDao.delete(moneyKind);
			if(row > 0){
				return true;
			}
			return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public List<MoneyKind> findAllList(MoneyKind moneyKind){
		try{
			List<MoneyKind> list = moneyKindDao.findAllList(moneyKind);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public List<MoneyKind> findList(MoneyKind moneyKind){
		try{
			List<MoneyKind> list = moneyKindDao.findList(moneyKind);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public MoneyKind get(MoneyKind moneyKind){
		try{
			MoneyKind bt = moneyKindDao.get(moneyKind);
			return bt;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<MoneyKind> getMoneyKindListByCondition(Map<String, Object> map){
		try{
			List<MoneyKind> list = moneyKindDao.getMoneyKindListByCondition(map);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public long getMoneyKindListTotal(MoneyKind moneyKind){
		try{
			long total = moneyKindDao.getMoneyKindListTotal(moneyKind);
			return total;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}	
	
}
