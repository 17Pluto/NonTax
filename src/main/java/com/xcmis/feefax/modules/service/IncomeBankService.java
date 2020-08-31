package com.xcmis.feefax.modules.service;

import com.xcmis.feefax.modules.dao.IncomeBankDao;
import com.xcmis.feefax.modules.entity.IncomeBank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional(readOnly = true)
public class IncomeBankService {
	@Autowired
	private IncomeBankDao incomeBankDao;
	
	@Transactional(readOnly = false)
	public boolean insert(IncomeBank incomeBank){
		try{
			int row = incomeBankDao.insert(incomeBank);
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
	public String insertReturnId(IncomeBank incomeBank) {
		try {
			int row = incomeBankDao.insert(incomeBank);
			if (row > 0) {
				return incomeBank.getChrId();
			}
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	@Transactional(readOnly = false)
	public boolean update(IncomeBank incomeBank){
		try{
			int row = incomeBankDao.update(incomeBank);
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
	public boolean delete(IncomeBank incomeBank){
		try{
			int row = incomeBankDao.delete(incomeBank);
			if(row > 0){
				return true;
			}
			return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public List<IncomeBank> getIncomeBankListByChrId(IncomeBank incomeBank){
		try{
			List<IncomeBank> list = incomeBankDao.getIncomeBankListByChrId(incomeBank);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public List<IncomeBank> findAllList(IncomeBank incomeBank){
		try{
			List<IncomeBank> list = incomeBankDao.findAllList(incomeBank);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public List<IncomeBank> findList(IncomeBank incomeBank){
		try{
			List<IncomeBank> list = incomeBankDao.findList(incomeBank);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public IncomeBank getIncomeBank(IncomeBank incomeBank){
		try{
			IncomeBank bt = incomeBankDao.get(incomeBank);
			return bt;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<IncomeBank> getIncomeBankListByCondition(Map<String, Object> map){
		try{
			List<IncomeBank> list = incomeBankDao.getIncomeBankListByCondition(map);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public long getIncomeBankListTotal(IncomeBank incomeBank){
		try{
			long total = incomeBankDao.getIncomeBankListTotal(incomeBank);
			return total;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}	
	
}
