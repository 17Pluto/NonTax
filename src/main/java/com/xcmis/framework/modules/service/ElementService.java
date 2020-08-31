package com.xcmis.framework.modules.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xcmis.framework.modules.dao.ElementDao;
import com.xcmis.framework.modules.entity.Element;



@Service
@Transactional(readOnly = true)
public class ElementService {
	@Autowired
	private ElementDao elementDao;
	
	@Transactional(readOnly = false)
	public boolean insert(Element element){
		try{
			int row = elementDao.insert(element);
			if(row > 0){
				return true;
			}
			return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Transactional(rollbackFor=Exception.class)
	public boolean updateIsRightfilter(List<Element> list){
		Element element = new Element();
		element.setIsRightfilter(0);
		elementDao.updateIsRightfilter(element);

		for(Element e : list){
			e.setIsRightfilter(1);
			elementDao.updateIsRightfilter(e);
		}
		return true;
	}
	
	@Transactional(readOnly = false)
	public boolean update(Element element){
		try{
			int row = elementDao.update(element);
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
	public boolean delete(Element element){
		try{
			int row = elementDao.delete(element);
			if(row > 0){
				return true;
			}
			return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public List<Element> findAllList(Element element){
		try{
			List<Element> list = elementDao.findAllList(element);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Element> findList(Element element){
		try{
			List<Element> list = elementDao.findList(element);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public Element getElement(Element element){
		try{
			Element e = elementDao.get(element);
			return e;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<Element> getElementListByCondition(Map<String, Object> map){
		try{
			List<Element> list = elementDao.getElementListByCondition(map);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public long getElementListTotal(Element element){
		try{
			long total = elementDao.getElementListTotal(element);
			return total;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}	
	
}
