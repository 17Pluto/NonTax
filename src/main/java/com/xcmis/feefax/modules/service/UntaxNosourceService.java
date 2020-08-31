package com.xcmis.feefax.modules.service;
/**
 * @author fangzhe
 * @date 2020-05-18
 *
 */
import com.xcmis.feefax.modules.dao.*;
;
import com.xcmis.feefax.modules.entity.*;
import com.xcmis.framework.common.utils.DateTimeUtils;
import com.xcmis.framework.jwt.JwtTokenUtil;
import com.xcmis.framework.modules.entity.User;
import com.xcmis.framework.modules.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class UntaxNosourceService {

    @Autowired
    private UntaxNosourceCollectionDao untaxNosourceCollectionDao;

    @Autowired
    private BillDetailDao billDetailDao;

    @Autowired
    private CollectionsDetailDao collectionsDetailDao;

    @Autowired
    private CollectionsDao collectionsDao;

    @Autowired
    private UserService userService;

    @Autowired
    private UntaxNosourceDao untaxNosourceDao;

    //增
    @Transactional(readOnly = false)
    public boolean insert(UntaxNosource untaxNosource) {
        try {
            int row = untaxNosourceDao.insert(untaxNosource);
            if (row > 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //改
    @Transactional(readOnly = false)
    public boolean update(UntaxNosource untaxNosource){
        try{
            int row = untaxNosourceDao.update(untaxNosource);
            if(row > 0){
                return true;
            }
            return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    //删
    @Transactional(readOnly = false)
    public boolean delete(UntaxNosource untaxNosource){
        try{
            int row = untaxNosourceDao.delete(untaxNosource);
            if(row > 0){
                return true;
            }
            return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public UntaxNosource get(UntaxNosource untaxNosource){
        try{
            UntaxNosource bt = untaxNosourceDao.get(untaxNosource);
            return bt;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //查
    public List<UntaxNosource> findAllList(UntaxNosource untaxNosource){
        try{
            List<UntaxNosource> list = untaxNosourceDao.findAllList(untaxNosource);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

//    public List<UntaxNosource> findList(UntaxNosource untaxNosource){
//        try{
//            List<UntaxNosource> list = untaxNosourceDao.findList(untaxNosource);
//            return list;
//        }catch(Exception e){
//            e.printStackTrace();
//            return null;
//        }
//    }

    //查
    public List<UntaxNosource> getUntaxNosourceListByCondition(Map<String, Object> map){
        try{
            List<UntaxNosource> list = untaxNosourceDao.getUntaxNosourceListByCondition(map);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //查
    public long getUntaxNosourceListTotal(UntaxNosource untaxNosource){
        try{
            long total = untaxNosourceDao.getUntaxNosourceListTotal(untaxNosource);
            return total;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 绑定缴款书分页
     * @param
     * @return
     */
    public long getCollectionsListTotal(Collections collections){
        try{
            long total = untaxNosourceDao.getCollectionsListTotal(collections);
            return total;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 缴款绑定书列表
     * @param map
     * @return
     */
    public List<Collections> getCollections(Map<String, Object> map){
        try{
            List<Collections> list = untaxNosourceDao.getCollections(map);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 不明款项打印方法
     * @param
     * @return
     */
    public List<UntaxNosource> getUntaxNosourceForPrint(String chrId){
        try{
            List<UntaxNosource> list = untaxNosourceDao.getUntaxNosourceForPrint(chrId);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public String getUserNamebyId(String userId){
        String name  = untaxNosourceDao.getUserNamebyId(userId);
        return name;
    }

    /**
     * 不款项 认领按钮
     * @param chrIds
     * @return
     */
    public boolean editIsClaimDo(String chrIds,UntaxNosource untaxNosource){
        String[] chrIdArr = chrIds.split(",");
        for(String chrId : chrIdArr){

            /**
             * 获取执收单位
             */
            Subject subject = SecurityUtils.getSubject();
            String token = (String)subject.getPrincipal();
            String userId = JwtTokenUtil.getUserId(token);

            User user = new User();
            user.setUserId(userId);
            user = userService.get(user);
            //执收单位id：user.getBelongOrg();
            untaxNosource.setEnId(user.getBelongOrg());

            untaxNosource.setChrId(chrId);
            untaxNosourceDao.editIsClaimDo(untaxNosource);
        }
        return true;
    }

    /**
     * 认领撤销
     * @param chrIds
     * @return
     */
    public boolean cancelIsClaim(String chrIds){
        String[] chrIdArr = chrIds.split(",");
        for(String chrId : chrIdArr){
            UntaxNosource untaxNosource = new UntaxNosource();
            untaxNosource.setChrId(chrId);
            untaxNosourceDao.cancelIsClaim(untaxNosource);
        }
        return true;
    }

    /**
     * 绑定撤销
     * @param chrIds
     * @return
     */
    @Transactional(rollbackFor=Exception.class)
    public boolean cancelIsCollection(String chrIds,String collectionIds){
        String[] collectionIdArr = collectionIds.split(",");
        for(String collectionId : collectionIdArr ){
            UntaxNosourceCollection unc = new UntaxNosourceCollection();
            unc.setCollectionId(collectionId);
            List<UntaxNosourceCollection> uncList = untaxNosourceCollectionDao.findAllList(unc);
            for(UntaxNosourceCollection untaxNosourceCollection : uncList){
                UntaxNosource untaxNosource = new UntaxNosource();
                untaxNosource.setChrId(untaxNosourceCollection.getNosourceId());
                //删除是否绑定状态位置
                untaxNosourceDao.recallIsCollectInNosource(untaxNosource);
                //清除nosourcecollection中的对应关系
                untaxNosourceDao.recallNosourceIds(untaxNosource);
            }

            Collections collections = new Collections();
            collections.setChrId(collectionId);
            //清除绑定票据的数据
            untaxNosourceDao.recallCollectionIds(collections);

            //重写nosourceids
            //untaxNosourceDao.updateNosourceIds(collections);
        }
        return true;
    }

    /**
     * 改变状态 送审
     * @param chrIds
     * @return
     */
    public boolean changeStateCode(String chrIds){
        String[] chrIdArr = chrIds.split(",");
        for(String chrId : chrIdArr){
            UntaxNosource untaxNosource = new UntaxNosource();
            untaxNosource.setChrId(chrId);
            untaxNosourceDao.changeStateCode(untaxNosource);
        }
        return true;
    }

    /**
     * 审核
     * @param chrIds
     * @return
     */
    public boolean editIsAuditDo(String chrIds){
        String[] chrIdArr = chrIds.split(",");
        for(String chrId : chrIdArr){
            UntaxNosource untaxNosource = new UntaxNosource();
            untaxNosource.setChrId(chrId);
            untaxNosourceDao.editIsAuditDo(untaxNosource);
        }
        return true;
    }
    /**
     * 撤回送审
     * @param chrIds
     * @return
     */
    public boolean cancelStateCode(String chrIds){
        String[] chrIdArr = chrIds.split(",");
        for(String chrId : chrIdArr){
            UntaxNosource untaxNosource = new UntaxNosource();
            untaxNosource.setChrId(chrId);
            untaxNosourceDao.cancelStateCode(untaxNosource);
        }
        return true;
    }
    /**
     * 撤回审核
     * @param chrIds
     * @return
     */
    public boolean cancelAudit(String chrIds){
        String[] chrIdArr = chrIds.split(",");
        for(String chrId : chrIdArr){
            UntaxNosource untaxNosource = new UntaxNosource();
            untaxNosource.setChrId(chrId);
            untaxNosourceDao.cancelAudit(untaxNosource);
        }
        return true;
    }

    /**
     * 绑定缴款书保存
     * 不明款项多选nosourceIds 票据单选collectionIds
     * @param
     * @return
     */
    @Transactional(rollbackFor=Exception.class)
    public boolean saveIsCollectForNosourceIds(String nosourceIds,String collectionIds){

        Collections collections = new Collections();
        collections.setNosourceIds(nosourceIds);
        collections.setChrId(collectionIds);
        //写入关联关系collections
        untaxNosourceDao.bindingIsCollect(collections);

        //修改iscollect状态位
        String[] nosourceIdArr = nosourceIds.split(",");
        for(String chrId : nosourceIdArr){
            UntaxNosourceCollection untaxNosourceCollection = new UntaxNosourceCollection();
            UntaxNosource untaxNosource = new UntaxNosource();
            untaxNosource.setChrId(chrId);
            untaxNosource.setLastVer(DateTimeUtils.getDateTimeStr1());
            untaxNosourceDao.bindingIsCollectInNosource(untaxNosource);


            untaxNosourceCollection.setNosourceId(chrId);
            untaxNosourceCollection.setCollectionId(collectionIds);
            untaxNosourceCollectionDao.insertIds(untaxNosourceCollection);
        }

        return true;
    }

    /**
     * 绑定缴款书保存
     * 票据多选collectionIds 不明款项单选
     * @param
     * @return
     */
    @Transactional(rollbackFor=Exception.class)
    public boolean saveIsCollectForCollectionIds(String nosourceIds,String collectionIds){

        String[] collectionIdArr = collectionIds.split(",");
        for (String chrId : collectionIdArr){
            Collections collections = new Collections();
            collections.setChrId(chrId);
            collections.setNosourceIds(nosourceIds);
            untaxNosourceDao.bindingNosourceInCollection(collections);

            UntaxNosourceCollection untaxNosourceCollection = new UntaxNosourceCollection();

            untaxNosourceCollection.setNosourceId(nosourceIds);
            untaxNosourceCollection.setCollectionId(chrId);
            untaxNosourceCollectionDao.insertIds(untaxNosourceCollection);
        }

        UntaxNosource untaxNosource = new UntaxNosource();
        untaxNosource.setChrId(nosourceIds);
        untaxNosource.setLastVer(DateTimeUtils.getDateTimeStr1());
        untaxNosourceDao.bindingIsCollectInNosource(untaxNosource);

        return true;
    }

    @Transactional(rollbackFor=Exception.class)
    public boolean insertAndAddCollectionsDo(Collections collections) {
        int row = collectionsDao.insert(collections);
        if (row > 0) {
            for(CollectionsDetail collectionsDetail : collections.getCollectionsDetailList()){
                collectionsDetail.setMainId(collections.getChrId());
                collectionsDetail.setLastVer(DateTimeUtils.getDateTimeStr1());
                collectionsDetailDao.insert(collectionsDetail);
            }

            BillDetail billDetail = new BillDetail();
            billDetail.setBillNo(collections.getBillNo());
            billDetail.setUntaxBillnameId(collections.getBilltypeId());
            billDetail.setState("1");
            billDetailDao.update(billDetail);
            return true;
        }else{
            return false;
        }
    }


    @Transactional(rollbackFor=Exception.class)
    public boolean updateBindingTime(String chrIds, String createDate, String createUser) {
        String[] chrIdsArr = chrIds.split(",");
        for(String chrId : chrIdsArr){
            UntaxNosource untaxNosource = new UntaxNosource();
            untaxNosource.setChrId(chrId);
            untaxNosource.setCreateDate(createDate);
            untaxNosource.setCreateUser(createUser);
            untaxNosourceDao.updateBindingTime(untaxNosource);
        }

        return true;
    }

    @Transactional(rollbackFor=Exception.class)
    public boolean updateConfirm(String nosourceIds) {
        String[] nosourceIdsArr = nosourceIds.split(",");
        for(String nosourceId : nosourceIdsArr){
            UntaxNosource untaxNosource = new UntaxNosource();
            untaxNosource.setChrId(nosourceId);
            untaxNosourceDao.updateConfirm(untaxNosource);
        }
        return true;
    }
}