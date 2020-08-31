package com.xcmis.feefax.modules.dao;

/**
 * @author fangzhe
 * @date 2020-05-18
 *  dao
 */
import com.xcmis.feefax.modules.entity.Collections;
import com.xcmis.feefax.modules.entity.UntaxNosource;
import com.xcmis.feefax.modules.entity.UntaxNosourceCollection;
import com.xcmis.framework.common.persistence.IBaseDao;
import com.xcmis.framework.common.persistence.annotation.MyBatisDao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@MyBatisDao
public interface UntaxNosourceDao extends IBaseDao<UntaxNosource> {

    public long getUntaxNosourceListTotal(UntaxNosource untaxNosource);

    public List<UntaxNosource> getUntaxNosourceListByCondition(Map<String, Object> map);

    public int editIsClaimDo(UntaxNosource untaxNosource);

    public int cancelIsClaim(UntaxNosource untaxNosource);

    public int changeStateCode(UntaxNosource untaxNosource);

    public int cancelStateCode(UntaxNosource untaxNosource);

    //审核按钮
    public int editIsAuditDo(UntaxNosource untaxNosource);

    //撤回审核按钮
    public int cancelAudit(UntaxNosource untaxNosource);

    //绑定缴款书分页
    public long getCollectionsListTotal(Collections collections);

    //绑定缴款书查询列表
    public List<Collections> getCollections(Map<String, Object> map);

    //校验是否重复绑定
    public long checkIsRepeat(UntaxNosourceCollection untaxNosourceCollection);

    //绑定缴款书 向collections中插入绑定数据
    public int bindingIsCollect(Collections collections);

    //改变不明款项的绑定状态
    public int bindingIsCollectInNosource(UntaxNosource untaxNosource);

    public int bindingNosourceInCollection(Collections collections);

    public List<UntaxNosource> getUntaxNosourceForPrint(String chrId);

    public String getUserNamebyId(String userId);

    //撤回绑定票据状态标记
    public int recallIsCollectInNosource(UntaxNosource untaxNosource);
    //清除collections中的nosourceids
    public int recallCollectionIds(Collections collections);
    //撤回绑定票据关联关系
    public int recallNosourceIds(UntaxNosource untaxNosource);
    //更新绑定数据
    public int updateNosourceIds(Collections collections);

    public int updateBindingTime(UntaxNosource untaxNosource);

    public int updateConfirm(UntaxNosource untaxNosource);


    public int updateCheckEarne(UntaxNosource untaxNosource);

}
