package com.xcmis.feefax.modules.service;

import com.xcmis.feefax.modules.dao.ButtonDao;
import com.xcmis.feefax.modules.entity.Button;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class ButtonService {
	@Autowired
	private ButtonDao buttonDao;

	public List<Button> findAllList(Button button){
		try{
			List<Button> list = buttonDao.findAllList(button);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public List<Button> getButtonListByStatusId(Button button){
		try{
			List<Button> list = buttonDao.getButtonListByStatusId(button);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Transactional(readOnly = false)
	public String insertReturnId(Button button) {
		try {
			int row = buttonDao.insert(button);
			if (row > 0) {
				return button.getButtonId();
			}
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	@Transactional(readOnly = false)
	public boolean delete(Button button){
		try{
			int row = buttonDao.delete(button);
			if(row > 0){
				return true;
			}
			return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
}
