package com.xcmis.feefax.modules.service;

import com.xcmis.feefax.modules.dao.ChargeKindDao;
import com.xcmis.feefax.modules.entity.ChargeKind;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional(readOnly = true)
public class ChargeKindService {
	@Autowired
	private ChargeKindDao chargeKindDao;
	
	@Transactional(readOnly = false)
	public boolean insert(ChargeKind chargeKind){
		try{
			int row = chargeKindDao.insert(chargeKind);
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
	public String insertReturnId(ChargeKind chargeKind) {
		try {
			int row = chargeKindDao.insert(chargeKind);
			if (row > 0) {
				return chargeKind.getChrId();
			}
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	@Transactional(readOnly = false)
	public boolean update(ChargeKind chargeKind){
		try{
			int row = chargeKindDao.update(chargeKind);
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
	public boolean delete(ChargeKind chargeKind){
		try{
			int row = chargeKindDao.delete(chargeKind);
			if(row > 0){
				return true;
			}
			return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public List<ChargeKind> findAllList(ChargeKind chargeKind){
		try{
			List<ChargeKind> list = chargeKindDao.findAllList(chargeKind);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public List<ChargeKind> findList(ChargeKind chargeKind){
		try{
			List<ChargeKind> list = chargeKindDao.findList(chargeKind);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public ChargeKind get(ChargeKind chargeKind){
		try{
			ChargeKind bt = chargeKindDao.get(chargeKind);
			return bt;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<ChargeKind> getChargeKindListByCondition(Map<String, Object> map){
		try{
			List<ChargeKind> list = chargeKindDao.getChargeKindListByCondition(map);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public long getChargeKindListTotal(ChargeKind chargeKind){
		try{
			long total = chargeKindDao.getChargeKindListTotal(chargeKind);
			return total;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}	
	
}
