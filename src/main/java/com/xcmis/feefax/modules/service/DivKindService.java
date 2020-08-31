package com.xcmis.feefax.modules.service;

import com.xcmis.feefax.modules.dao.DivKindDao;
import com.xcmis.feefax.modules.entity.DivKind;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional(readOnly = true)
public class DivKindService {
	@Autowired
	private DivKindDao divKindDao;
	
	@Transactional(readOnly = false)
	public boolean insert(DivKind divKind){
		try{
			int row = divKindDao.insert(divKind);
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
	public String insertReturnId(DivKind divKind) {
		try {
			int row = divKindDao.insert(divKind);
			if (row > 0) {
				return divKind.getChrId();
			}
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	@Transactional(readOnly = false)
	public boolean update(DivKind divKind){
		try{
			int row = divKindDao.update(divKind);
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
	public boolean delete(DivKind divKind){
		try{
			int row = divKindDao.delete(divKind);
			if(row > 0){
				return true;
			}
			return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public List<DivKind> findAllList(DivKind divKind){
		try{
			List<DivKind> list = divKindDao.findAllList(divKind);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public List<DivKind> findList(DivKind divKind){
		try{
			List<DivKind> list = divKindDao.findList(divKind);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public DivKind get(DivKind divKind){
		try{
			DivKind bt = divKindDao.get(divKind);
			return bt;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<DivKind> getDivKindListByCondition(Map<String, Object> map){
		try{
			List<DivKind> list = divKindDao.getDivKindListByCondition(map);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public long getDivKindListTotal(DivKind divKind){
		try{
			long total = divKindDao.getDivKindListTotal(divKind);
			return total;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}	
	
}
