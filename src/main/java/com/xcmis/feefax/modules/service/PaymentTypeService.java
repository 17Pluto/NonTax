package com.xcmis.feefax.modules.service;

import com.xcmis.feefax.modules.dao.PaymentTypeDao;
import com.xcmis.feefax.modules.entity.PaymentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional(readOnly = true)
public class PaymentTypeService {
	@Autowired
	private PaymentTypeDao paymentTypeDao;
	
	@Transactional(readOnly = false)
	public boolean insert(PaymentType paymentType){
		try{
			int row = paymentTypeDao.insert(paymentType);
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
	public boolean update(PaymentType paymentType){
		try{
			int row = paymentTypeDao.update(paymentType);
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
	public boolean delete(PaymentType paymentType){
		try{
			int row = paymentTypeDao.delete(paymentType);
			if(row > 0){
				return true;
			}
			return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public List<PaymentType> findAllList(PaymentType paymentType){
		try{
			List<PaymentType> list = paymentTypeDao.findAllList(paymentType);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public List<PaymentType> findList(PaymentType paymentType){
		try{
			List<PaymentType> list = paymentTypeDao.findList(paymentType);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public PaymentType getPaymentType(PaymentType paymentType){
		try{
			PaymentType bt = paymentTypeDao.get(paymentType);
			return bt;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<PaymentType> getPaymentTypeListByCondition(Map<String, Object> map){
		try{
			List<PaymentType> list = paymentTypeDao.getPaymentTypeListByCondition(map);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public long getPaymentTypeListTotal(PaymentType paymentType){
		try{
			long total = paymentTypeDao.getPaymentTypeListTotal(paymentType);
			return total;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}	
	
}
