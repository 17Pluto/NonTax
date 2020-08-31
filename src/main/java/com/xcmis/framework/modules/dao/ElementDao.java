package com.xcmis.framework.modules.dao;

import java.util.List;
import java.util.Map;

import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;
import com.xcmis.framework.modules.entity.Element;


@MyBatisDao
public interface ElementDao extends IBaseDao<Element> {
	
	public long getElementListTotal(Element element);
	
	public List<Element> getElementListByCondition(Map<String, Object> map);

	public int updateIsRightfilter(Element element);
}
