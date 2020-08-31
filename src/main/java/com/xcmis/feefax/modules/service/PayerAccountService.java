package com.xcmis.feefax.modules.service;

import com.xcmis.feefax.modules.dao.PayerAccountDao;
import com.xcmis.feefax.modules.entity.PayerAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional(readOnly = true)
public class PayerAccountService {
	@Autowired
	private PayerAccountDao payerAccountDao;
	
	@Transactional(readOnly = false)
	public boolean insert(PayerAccount payerAccount){
		try{
			int row = payerAccountDao.insert(payerAccount);
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
	public String insertReturnId(PayerAccount payerAccount) {
		try {
			int row = payerAccountDao.insert(payerAccount);
			if (row > 0) {
				return payerAccount.getChrId();
			}
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	@Transactional(readOnly = false)
	public boolean update(PayerAccount payerAccount){
		try{
			int row = payerAccountDao.update(payerAccount);
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
	public boolean delete(PayerAccount payerAccount){
		try{
			int row = payerAccountDao.delete(payerAccount);
			if(row > 0){
				return true;
			}
			return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public List<PayerAccount> findAllList(PayerAccount payerAccount){
		try{
			List<PayerAccount> list = payerAccountDao.findAllList(payerAccount);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public List<PayerAccount> findList(PayerAccount payerAccount){
		try{
			List<PayerAccount> list = payerAccountDao.findList(payerAccount);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public PayerAccount get(PayerAccount payerAccount){
		try{
			PayerAccount bt = payerAccountDao.get(payerAccount);
			return bt;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<PayerAccount> getPayerAccountListByCondition(Map<String, Object> map){
		try{
			List<PayerAccount> list = payerAccountDao.getPayerAccountListByCondition(map);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public long getPayerAccountListTotal(PayerAccount payerAccount){
		try{
			long total = payerAccountDao.getPayerAccountListTotal(payerAccount);
			return total;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}	
	
}
