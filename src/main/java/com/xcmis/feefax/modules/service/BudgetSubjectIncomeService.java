package com.xcmis.feefax.modules.service;

import com.xcmis.feefax.modules.dao.BudgetSubjectIncomeDao;
import com.xcmis.feefax.modules.entity.BudgetSubjectIncome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional(readOnly = true)
public class BudgetSubjectIncomeService {
	@Autowired
	private BudgetSubjectIncomeDao budgetSubjectIncomeDao;
	
	@Transactional(readOnly = false)
	public boolean insert(BudgetSubjectIncome budgetSubjectIncome){
		try{
			int row = budgetSubjectIncomeDao.insert(budgetSubjectIncome);
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
	public String insertReturnId(BudgetSubjectIncome budgetSubjectIncome) {
		try {
			int row = budgetSubjectIncomeDao.insert(budgetSubjectIncome);
			if (row > 0) {
				return budgetSubjectIncome.getChrId();
			}
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	@Transactional(readOnly = false)
	public boolean update(BudgetSubjectIncome budgetSubjectIncome){
		try{
			int row = budgetSubjectIncomeDao.update(budgetSubjectIncome);
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
	public boolean delete(BudgetSubjectIncome budgetSubjectIncome){
		try{
			int row = budgetSubjectIncomeDao.delete(budgetSubjectIncome);
			if(row > 0){
				return true;
			}
			return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public List<BudgetSubjectIncome> findAllList(BudgetSubjectIncome budgetSubjectIncome){
		try{
			List<BudgetSubjectIncome> list = budgetSubjectIncomeDao.findAllList(budgetSubjectIncome);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public List<BudgetSubjectIncome> findList(BudgetSubjectIncome budgetSubjectIncome){
		try{
			List<BudgetSubjectIncome> list = budgetSubjectIncomeDao.findList(budgetSubjectIncome);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public List<BudgetSubjectIncome> getBudgetSubjectIncomeListByChrId(BudgetSubjectIncome budgetSubjectIncome){
		try{
			List<BudgetSubjectIncome> list = budgetSubjectIncomeDao.getBudgetSubjectIncomeListByChrId(budgetSubjectIncome);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}


	
	public BudgetSubjectIncome getBudgetSubjectIncome(BudgetSubjectIncome budgetSubjectIncome){
		try{
			BudgetSubjectIncome bt = budgetSubjectIncomeDao.get(budgetSubjectIncome);
			return bt;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<BudgetSubjectIncome> getBudgetSubjectIncomeListByCondition(Map<String, Object> map){
		try{
			List<BudgetSubjectIncome> list = budgetSubjectIncomeDao.getBudgetSubjectIncomeListByCondition(map);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public long getBudgetSubjectIncomeListTotal(BudgetSubjectIncome budgetSubjectIncome){
		try{
			long total = budgetSubjectIncomeDao.getBudgetSubjectIncomeListTotal(budgetSubjectIncome);
			return total;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}	
	
}
