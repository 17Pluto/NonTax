package com.xcmis.feefax.modules.service;

import com.xcmis.feefax.modules.dao.PayModeDao;
import com.xcmis.feefax.modules.entity.PayMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional(readOnly = true)
public class PayModeService {
	@Autowired
	private PayModeDao payModeDao;
	
	@Transactional(readOnly = false)
	public boolean insert(PayMode payMode){
		try{
			int row = payModeDao.insert(payMode);
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
	public String insertReturnId(PayMode payMode) {
		try {
			int row = payModeDao.insert(payMode);
			if (row > 0) {
				return payMode.getChrId();
			}
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	@Transactional(readOnly = false)
	public boolean update(PayMode payMode){
		try{
			int row = payModeDao.update(payMode);
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
	public boolean delete(PayMode payMode){
		try{
			int row = payModeDao.delete(payMode);
			if(row > 0){
				return true;
			}
			return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public List<PayMode> findAllList(PayMode payMode){
		try{
			List<PayMode> list = payModeDao.findAllList(payMode);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public List<PayMode> findList(PayMode payMode){
		try{
			List<PayMode> list = payModeDao.findList(payMode);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public PayMode get(PayMode payMode){
		try{
			PayMode bt = payModeDao.get(payMode);
			return bt;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<PayMode> getPayModeListByCondition(Map<String, Object> map){
		try{
			List<PayMode> list = payModeDao.getPayModeListByCondition(map);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public long getPayModeListTotal(PayMode payMode){
		try{
			long total = payModeDao.getPayModeListTotal(payMode);
			return total;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}	
	
}
