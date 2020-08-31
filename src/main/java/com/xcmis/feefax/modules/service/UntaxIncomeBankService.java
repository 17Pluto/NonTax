package com.xcmis.feefax.modules.service;

import com.xcmis.feefax.modules.dao.UntaxIncomeBankDao;
import com.xcmis.feefax.modules.entity.UntaxIncomeBank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional(readOnly = true)
public class UntaxIncomeBankService {
	@Autowired
	private UntaxIncomeBankDao untaxIncomeBankDao;
	
	@Transactional(readOnly = false)
	public boolean insert(UntaxIncomeBank untaxIncomeBank){
		try{
			int row = untaxIncomeBankDao.insert(untaxIncomeBank);
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
	public String insertReturnId(UntaxIncomeBank untaxIncomeBank) {
		try {
			int row = untaxIncomeBankDao.insert(untaxIncomeBank);
			if (row > 0) {
				return untaxIncomeBank.getChrId();
			}
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	@Transactional(readOnly = false)
	public boolean update(UntaxIncomeBank untaxIncomeBank){
		try{
			int row = untaxIncomeBankDao.update(untaxIncomeBank);
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
	public boolean delete(UntaxIncomeBank untaxIncomeBank){
		try{
			int row = untaxIncomeBankDao.delete(untaxIncomeBank);
			if(row > 0){
				return true;
			}
			return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public List<UntaxIncomeBank> findAllList(UntaxIncomeBank untaxIncomeBank){
		try{
			List<UntaxIncomeBank> list = untaxIncomeBankDao.findAllList(untaxIncomeBank);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public List<UntaxIncomeBank> findList(UntaxIncomeBank untaxIncomeBank){
		try{
			List<UntaxIncomeBank> list = untaxIncomeBankDao.findList(untaxIncomeBank);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public UntaxIncomeBank getUntaxIncomeBank(UntaxIncomeBank untaxIncomeBank){
		try{
			UntaxIncomeBank bt = untaxIncomeBankDao.get(untaxIncomeBank);
			return bt;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<UntaxIncomeBank> getUntaxIncomeBankListByCondition(Map<String, Object> map){
		try{
			List<UntaxIncomeBank> list = untaxIncomeBankDao.getUntaxIncomeBankListByCondition(map);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public long getUntaxIncomeBankListTotal(UntaxIncomeBank untaxIncomeBank){
		try{
			long total = untaxIncomeBankDao.getUntaxIncomeBankListTotal(untaxIncomeBank);
			return total;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}	
	
}
