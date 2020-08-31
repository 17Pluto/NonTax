package com.xcmis.feefax.modules.service;

import com.xcmis.feefax.modules.dao.BillKindDao;
import com.xcmis.feefax.modules.entity.BillKind;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional(readOnly = true)
public class BillKindService {
	@Autowired
	private BillKindDao billKindDao;
	
	@Transactional(readOnly = false)
	public boolean insert(BillKind billKind){
		try{
			int row = billKindDao.insert(billKind);
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
	public String insertReturnId(BillKind billKind) {
		try {
			int row = billKindDao.insert(billKind);
			if (row > 0) {
				return billKind.getChrId();
			}
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	@Transactional(readOnly = false)
	public boolean update(BillKind billKind){
		try{
			int row = billKindDao.update(billKind);
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
	public boolean delete(BillKind billKind){
		try{
			int row = billKindDao.delete(billKind);
			if(row > 0){
				return true;
			}
			return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public List<BillKind> findAllList(BillKind billKind){
		try{
			List<BillKind> list = billKindDao.findAllList(billKind);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public List<BillKind> findList(BillKind billKind){
		try{
			List<BillKind> list = billKindDao.findList(billKind);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public BillKind getBillKind(BillKind billKind){
		try{
			BillKind bt = billKindDao.get(billKind);
			return bt;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<BillKind> getBillKindListByCondition(Map<String, Object> map){
		try{
			List<BillKind> list = billKindDao.getBillKindListByCondition(map);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public long getBillKindListTotal(BillKind billKind){
		try{
			long total = billKindDao.getBillKindListTotal(billKind);
			return total;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}	
	
}
