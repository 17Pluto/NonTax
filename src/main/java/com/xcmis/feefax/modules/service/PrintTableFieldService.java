package com.xcmis.feefax.modules.service;
/**
 * @author fangzhe
 * @date 2020-03-26
 * BillInStoore service
 */
import com.xcmis.feefax.modules.dao.PrintTableFieldDao;
import com.xcmis.feefax.modules.entity.PrintTableField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class PrintTableFieldService {

    @Autowired
    private PrintTableFieldDao printTableFieldDao;

    @Transactional(readOnly = false)
    public boolean insert(PrintTableField printTableField) {
        try {
            int row = printTableFieldDao.insert(printTableField);
            if (row > 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Transactional(readOnly = false)
    public String insertReturnId(PrintTableField printTableField){
        try{
            int row = printTableFieldDao.insert(printTableField);
            if(row > 0){
                return printTableField.getChrId();
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Transactional(readOnly = false)
    public boolean update(PrintTableField printTableField){
        try{
            int row = printTableFieldDao.update(printTableField);
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
    public boolean delete(PrintTableField printTableField){
        try{
            int row = printTableFieldDao.delete(printTableField);
            if(row > 0){
                return true;
            }
            return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<PrintTableField> findAllList(PrintTableField printTableField){
        try{
            List<PrintTableField> list = printTableFieldDao.findAllList(printTableField);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<PrintTableField> findList(PrintTableField printTableField){
        try{
            List<PrintTableField> list = printTableFieldDao.findList(printTableField);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public PrintTableField get(PrintTableField printTableField){
        try{
            PrintTableField bt = printTableFieldDao.get(printTableField);
            return bt;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<PrintTableField> getPrintTableFieldListByCondition(Map<String, Object> map){
        try{
            List<PrintTableField> list = printTableFieldDao.getPrintTableFieldListByCondition(map);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public long getPrintTableFieldListTotal(PrintTableField printTableField){
        try{
            long total = printTableFieldDao.getPrintTableFieldListTotal(printTableField);
            return total;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }
}