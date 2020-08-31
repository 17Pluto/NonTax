package com.xcmis.feefax.modules.service;

import com.xcmis.feefax.modules.dao.ProjectTypeDao;
import com.xcmis.feefax.modules.entity.ProjectType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional(readOnly = true)
public class ProjectTypeService {
	@Autowired
	private ProjectTypeDao projectTypeDao;
	
	@Transactional(readOnly = false)
	public boolean insert(ProjectType projectType){
		try{
			int row = projectTypeDao.insert(projectType);
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
	public boolean update(ProjectType projectType){
		try{
			int row = projectTypeDao.update(projectType);
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
	public boolean delete(ProjectType projectType){
		try{
			int row = projectTypeDao.delete(projectType);
			if(row > 0){
				return true;
			}
			return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public List<ProjectType> findAllList(ProjectType projectType){
		try{
			List<ProjectType> list = projectTypeDao.findAllList(projectType);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public List<ProjectType> findList(ProjectType projectType){
		try{
			List<ProjectType> list = projectTypeDao.findList(projectType);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public ProjectType getProjectType(ProjectType projectType){
		try{
			ProjectType pt = projectTypeDao.get(projectType);
			return pt;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<ProjectType> getProjectTypeListByCondition(Map<String, Object> map){
		try{
			List<ProjectType> list = projectTypeDao.getProjectTypeListByCondition(map);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public long getProjectTypeListTotal(ProjectType projectType){
		try{
			long total = projectTypeDao.getProjectTypeListTotal(projectType);
			return total;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}	
	
}
