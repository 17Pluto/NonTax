package com.xcmis.feefax.modules.service;

import com.xcmis.feefax.modules.dao.UpdownAccountDao;
import com.xcmis.feefax.modules.entity.UpdownAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional(readOnly = true)
public class UpdownAccountService {
	@Autowired
	private UpdownAccountDao updownAccountDao;
	
	@Transactional(readOnly = false)
	public boolean insert(UpdownAccount updownAccount){
		try{
			int row = updownAccountDao.insert(updownAccount);
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
	public String insertReturnId(UpdownAccount updownAccount) {
		try {
			int row = updownAccountDao.insert(updownAccount);
			if (row > 0) {
				return updownAccount.getChrId();
			}
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	@Transactional(readOnly = false)
	public boolean update(UpdownAccount updownAccount){
		try{
			int row = updownAccountDao.update(updownAccount);
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
	public boolean delete(UpdownAccount updownAccount){
		try{
			int row = updownAccountDao.delete(updownAccount);
			if(row > 0){
				return true;
			}
			return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public List<UpdownAccount> findAllList(UpdownAccount updownAccount){
		try{
			List<UpdownAccount> list = updownAccountDao.findAllList(updownAccount);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public List<UpdownAccount> findList(UpdownAccount updownAccount){
		try{
			List<UpdownAccount> list = updownAccountDao.findList(updownAccount);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public UpdownAccount get(UpdownAccount updownAccount){
		try{
			UpdownAccount bt = updownAccountDao.get(updownAccount);
			return bt;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<UpdownAccount> getUpdownAccountListByCondition(Map<String, Object> map){
		try{
			List<UpdownAccount> list = updownAccountDao.getUpdownAccountListByCondition(map);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public long getUpdownAccountListTotal(UpdownAccount updownAccount){
		try{
			long total = updownAccountDao.getUpdownAccountListTotal(updownAccount);
			return total;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}	
	
}
