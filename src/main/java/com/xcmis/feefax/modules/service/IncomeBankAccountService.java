package com.xcmis.feefax.modules.service;

import com.xcmis.feefax.modules.dao.IncomeBankAccountDao;
import com.xcmis.feefax.modules.entity.IncomeBankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional(readOnly = true)
public class IncomeBankAccountService {
	@Autowired
	private IncomeBankAccountDao incomeBankAccountDao;
	
	@Transactional(readOnly = false)
	public boolean insert(IncomeBankAccount incomeBankAccount){
		try{
			int row = incomeBankAccountDao.insert(incomeBankAccount);
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
	public String insertReturnId(IncomeBankAccount incomeBankAccount) {
		try {
			int row = incomeBankAccountDao.insert(incomeBankAccount);
			if (row > 0) {
				return incomeBankAccount.getChrId();
			}
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	@Transactional(readOnly = false)
	public boolean update(IncomeBankAccount incomeBankAccount){
		try{
			int row = incomeBankAccountDao.update(incomeBankAccount);
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
	public boolean delete(IncomeBankAccount incomeBankAccount){
		try{
			int row = incomeBankAccountDao.delete(incomeBankAccount);
			if(row > 0){
				return true;
			}
			return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public List<IncomeBankAccount> findAllList(IncomeBankAccount incomeBankAccount){
		try{
			List<IncomeBankAccount> list = incomeBankAccountDao.findAllList(incomeBankAccount);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public List<IncomeBankAccount> findList(IncomeBankAccount incomeBankAccount){
		try{
			List<IncomeBankAccount> list = incomeBankAccountDao.findList(incomeBankAccount);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public IncomeBankAccount get(IncomeBankAccount incomeBankAccount){
		try{
			IncomeBankAccount iba = incomeBankAccountDao.get(incomeBankAccount);
			return iba;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<IncomeBankAccount> getIncomeBankAccountListByCondition(Map<String, Object> map){
		try{
			List<IncomeBankAccount> list = incomeBankAccountDao.getIncomeBankAccountListByCondition(map);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public long getIncomeBankAccountListTotal(IncomeBankAccount incomeBankAccount){
		try{
			long total = incomeBankAccountDao.getIncomeBankAccountListTotal(incomeBankAccount);
			return total;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}	
	
}
