package com.xcmis.feefax.modules.service;

import com.xcmis.feefax.modules.dao.BillNameDao;
import com.xcmis.feefax.modules.entity.BillName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional(readOnly = true)
public class BillNameService {
	@Autowired
	private BillNameDao billNameDao;
	
	@Transactional(readOnly = false)
	public boolean insert(BillName billName){
		try{
			int row = billNameDao.insert(billName);
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
	public String insertReturnId(BillName billName) {
		try {
			int row = billNameDao.insert(billName);
			if (row > 0) {
				return billName.getChrId();
			}
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	@Transactional(readOnly = false)
	public boolean update(BillName billName){
		try{
			int row = billNameDao.update(billName);
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
	public boolean delete(BillName billName){
		try{
			int row = billNameDao.delete(billName);
			if(row > 0){
				return true;
			}
			return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public List<BillName> findAllList(BillName billName){
		try{
			List<BillName> list = billNameDao.findAllList(billName);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public List<BillName> findList(BillName billName){
		try{
			List<BillName> list = billNameDao.findList(billName);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public BillName get(BillName billName){
		try{
			BillName bt = billNameDao.get(billName);
			return bt;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public List<BillName> getBillNameListByMainId(BillName billName){
		try{
			List<BillName> list = billNameDao.getBillNameListByMainId(billName);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<BillName> getBillNameListByCondition(Map<String, Object> map){
		try{
			List<BillName> list = billNameDao.getBillNameListByCondition(map);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public long getBillNameListTotal(BillName billName){
		try{
			long total = billNameDao.getBillNameListTotal(billName);
			return total;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}	
	
}
