package com.xcmis.feefax.modules.service;
/**
 * @author fangzhe
 * @date 2020-03-26
 * BillInStoore service
 */
import com.xcmis.feefax.modules.dao.PrintTableDao;
import com.xcmis.feefax.modules.dao.PrintTableFieldDao;
import com.xcmis.feefax.modules.entity.PrintTable;
import com.xcmis.feefax.modules.entity.PrintTableField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class PrintTableService {

    @Autowired
    private PrintTableDao printTableDao;

    @Autowired
    private PrintTableFieldDao printTableFieldDao;

    @Transactional(rollbackFor=Exception.class)
    public String insertReturnId(PrintTable printTable) {
        int row = printTableDao.insert(printTable);

        if (row > 0) {
            String chrId = printTable.getChrId();

            for(PrintTableField printTableField : printTable.getPrintTableFieldList()){
                printTableField.setMainId(chrId);
                printTableFieldDao.insert(printTableField);
            }

            return chrId;
        }
        return "";

    }


    @Transactional(rollbackFor=Exception.class)
    public boolean update(PrintTable printTable){
        int row = printTableDao.update(printTable);
        if (row > 0) {
            String chrId = printTable.getChrId();

            PrintTableField ptf = new PrintTableField();
            ptf.setMainId(chrId);
            printTableFieldDao.delete(ptf);
            for(PrintTableField printTableField : printTable.getPrintTableFieldList()){
                printTableField.setMainId(chrId);
                printTableFieldDao.insert(printTableField);
            }
            return true;
        }else {
            return false;
        }
    }

    @Transactional(rollbackFor=Exception.class)
    public boolean delete(PrintTable printTable){
        try{
            String chrId = printTable.getChrId();

            PrintTableField ptf = new PrintTableField();
            ptf.setMainId(chrId);
            printTableFieldDao.delete(ptf);

            int row = printTableDao.delete(printTable);
            if(row > 0){
                return true;
            }
            return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<PrintTable> findAllList(PrintTable printTable){
        try{
            List<PrintTable> list = printTableDao.findAllList(printTable);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<PrintTable> findList(PrintTable printTable){
        try{
            List<PrintTable> list = printTableDao.findList(printTable);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public PrintTable get(PrintTable printTable){
        try{
            PrintTable bt = printTableDao.get(printTable);
            return bt;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<PrintTable> getPrintTableListByCondition(Map<String, Object> map){
        try{
            List<PrintTable> list = printTableDao.getPrintTableListByCondition(map);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public long getPrintTableListTotal(PrintTable printTable){
        try{
            long total = printTableDao.getPrintTableListTotal(printTable);
            return total;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }
}