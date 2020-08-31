package com.xcmis.feefax.modules.service;

import com.xcmis.feefax.modules.dao.ReceiverAccountDao;
import com.xcmis.feefax.modules.entity.ReceiverAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional(readOnly = true)
public class ReceiverAccountService {
	@Autowired
	private ReceiverAccountDao receiverAccountDao;
	
	@Transactional(readOnly = false)
	public boolean insert(ReceiverAccount receiverAccount){
		try{
			int row = receiverAccountDao.insert(receiverAccount);
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
	public String insertReturnId(ReceiverAccount receiverAccount) {
		try {
			int row = receiverAccountDao.insert(receiverAccount);
			if (row > 0) {
				return receiverAccount.getChrId();
			}
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	@Transactional(readOnly = false)
	public boolean update(ReceiverAccount receiverAccount){
		try{
			int row = receiverAccountDao.update(receiverAccount);
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
	public boolean delete(ReceiverAccount receiverAccount){
		try{
			int row = receiverAccountDao.delete(receiverAccount);
			if(row > 0){
				return true;
			}
			return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public List<ReceiverAccount> findAllList(ReceiverAccount receiverAccount){
		try{
			List<ReceiverAccount> list = receiverAccountDao.findAllList(receiverAccount);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public List<ReceiverAccount> findList(ReceiverAccount receiverAccount){
		try{
			List<ReceiverAccount> list = receiverAccountDao.findList(receiverAccount);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public ReceiverAccount get(ReceiverAccount receiverAccount){
		try{
			ReceiverAccount iba = receiverAccountDao.get(receiverAccount);
			return iba;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<ReceiverAccount> getReceiverAccountListByCondition(Map<String, Object> map){
		try{
			List<ReceiverAccount> list = receiverAccountDao.getReceiverAccountListByCondition(map);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public long getReceiverAccountListTotal(ReceiverAccount receiverAccount){
		try{
			long total = receiverAccountDao.getReceiverAccountListTotal(receiverAccount);
			return total;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}	
	
}
