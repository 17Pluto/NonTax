package com.xcmis.feefax.modules.service;

import com.xcmis.feefax.modules.dao.ItemSortDao;
import com.xcmis.feefax.modules.entity.ItemSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional(readOnly = true)
public class ItemSortService {
	@Autowired
	private ItemSortDao itemSortDao;
	
	@Transactional(readOnly = false)
	public boolean insert(ItemSort itemSort){
		try{
			int row = itemSortDao.insert(itemSort);
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
	public String insertReturnId(ItemSort itemSort) {
		try {
			int row = itemSortDao.insert(itemSort);
			if (row > 0) {
				return itemSort.getChrId();
			}
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	@Transactional(readOnly = false)
	public boolean update(ItemSort itemSort){
		try{
			int row = itemSortDao.update(itemSort);
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
	public boolean delete(ItemSort itemSort){
		try{
			int row = itemSortDao.delete(itemSort);
			if(row > 0){
				return true;
			}
			return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public List<ItemSort> findAllList(ItemSort itemSort){
		try{
			List<ItemSort> list = itemSortDao.findAllList(itemSort);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public List<ItemSort> findList(ItemSort itemSort){
		try{
			List<ItemSort> list = itemSortDao.findList(itemSort);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public ItemSort get(ItemSort itemSort){
		try{
			ItemSort bt = itemSortDao.get(itemSort);
			return bt;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<ItemSort> getItemSortListByCondition(Map<String, Object> map){
		try{
			List<ItemSort> list = itemSortDao.getItemSortListByCondition(map);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public long getItemSortListTotal(ItemSort itemSort){
		try{
			long total = itemSortDao.getItemSortListTotal(itemSort);
			return total;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}	
	
}
