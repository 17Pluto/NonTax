package com.xcmis.feefax.modules.service;

import com.xcmis.feefax.modules.dao.EduBatchManagementDao;
import com.xcmis.feefax.modules.entity.EduBatchManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author fangzhe
 * @Date 2020/6/18 1:45 下午
 * @Version 1.0
 */
@Service
public class EduBatchManagementService {

    @Autowired
    private EduBatchManagementDao eduBatchManagementDao;

    @Transactional(readOnly = false)
    public List<EduBatchManagement> getEduBatchManagement(Map<String,Object> map){
        try{
            List<EduBatchManagement> list = eduBatchManagementDao.getEduBatchManagement(map);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 列表
     * @param map
     * @return
     */
    @Transactional(readOnly = false)
    public List<EduBatchManagement> getEduBatchManagementListByCondition(Map<String,Object> map){
        try{
            List<EduBatchManagement> list = eduBatchManagementDao.getEduBatchManagementListByCondition(map);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 分页
     * @param eduBatchManagement
     * @return
     */
    @Transactional(readOnly = false)
    public long getEduBatchManagementListTotal(EduBatchManagement eduBatchManagement){
        try{
            long total = eduBatchManagementDao.getEduBatchManagementListTotal(eduBatchManagement);
            return total;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 插入
     * @param eduBatchManagement
     * @return
     */
    @Transactional(readOnly = false)
    public boolean insert(EduBatchManagement eduBatchManagement) {
        try {
            int row = eduBatchManagementDao.insert(eduBatchManagement);
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
    public boolean update(EduBatchManagement eduBatchManagement){
        try{
            int row = eduBatchManagementDao.update(eduBatchManagement);
            if(row > 0){
                return true;
            }
            return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     *
     * @param chrId
     * @return
     */
    @Transactional(readOnly = false)
    public boolean editEnableStatus(String chrId){
        try{
            int row = eduBatchManagementDao.editEnableStatus(chrId);
            if(row > 0){
                return true;
            }
            return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 检查启停状态
     * @return
     */
    @Transactional(readOnly = false)
    public boolean checkEnableStatus(Map<String,Object> map){
        try{
            List<EduBatchManagement> list = eduBatchManagementDao.checkEnableStatus(map);
            if(list != null){
                for (int i =0 ; i < list.size() ; i++){
                    EduBatchManagement eduBatchManagement = new EduBatchManagement();
                    eduBatchManagement = list.get(i);
                    //停用过期的批次
                    eduBatchManagementDao.updateEnable(eduBatchManagement);
                }
                return true;
            }
            return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 手动停用批次
     * @param chrIds
     * @return
     */
    @Transactional(readOnly = false)
    public boolean disableStatus(String chrIds){
        //获取chrid
        String[] chrIdArr = chrIds.split(",");
        for(String chrId : chrIdArr){
            EduBatchManagement eduBatchManagement = new EduBatchManagement();
            eduBatchManagement.setChrId(chrId);
            eduBatchManagementDao.updateEnable(eduBatchManagement);
        }
        return true;
    }



    @Transactional(readOnly = false)
    public boolean deleteBatchDo(String chrIds){
        //获取chrid
        String[] chrIdArr = chrIds.split(",");
        for(String chrId : chrIdArr){
            EduBatchManagement eduBatchManagement = new EduBatchManagement();
            eduBatchManagement.setChrId(chrId);
            //校验批次是否被使用⏰⏰
            long total = eduBatchManagementDao.checkBatchIsUsed(eduBatchManagement);
            if(total > 1){
                return false;
            }
            //校验完成后进行删除
            eduBatchManagementDao.deleteBatchDo(eduBatchManagement);
        }
        return true;
    }

}
