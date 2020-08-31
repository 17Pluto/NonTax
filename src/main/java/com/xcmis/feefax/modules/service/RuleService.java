package com.xcmis.feefax.modules.service;

import com.xcmis.feefax.modules.dao.RightGroupDao;
import com.xcmis.feefax.modules.dao.RightGroupDetailDao;
import com.xcmis.feefax.modules.dao.RightGroupTypeDao;
import com.xcmis.feefax.modules.dao.RuleDao;
import com.xcmis.feefax.modules.entity.RightGroup;
import com.xcmis.feefax.modules.entity.RightGroupDetail;
import com.xcmis.feefax.modules.entity.RightGroupType;
import com.xcmis.feefax.modules.entity.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class RuleService {
	@Autowired
	private RuleDao ruleDao;

	@Autowired
	private RightGroupDao rightGroupDao;

	@Autowired
	private RightGroupTypeDao rightGroupTypeDao;

	@Autowired
	private RightGroupDetailDao rightGroupDetailDao;


	public List<Rule> findAllList(Rule rule){
		try{
			List<Rule> list = ruleDao.findAllList(rule);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public Rule get(Rule rule){
		try{
			Rule r = ruleDao.get(rule);

			RightGroup rightGroup = new RightGroup();
			rightGroup.setRuleId(r.getRuleId());
			rightGroup = rightGroupDao.findAllList(rightGroup).get(0);

			RightGroupType rightGroupType = new RightGroupType();
			rightGroupType.setRightGroupId(rightGroup.getRightGroupId());
			List<RightGroupType> rightGroupTypeList = rightGroupTypeDao.findAllList(rightGroupType);

			RightGroupDetail rightGroupDetail = new RightGroupDetail();
			rightGroupDetail.setRightGroupId(rightGroup.getRightGroupId());
			List<RightGroupDetail> rightGroupDetailList = rightGroupDetailDao.findAllList(rightGroupDetail);

			rightGroup.setRightGroupTypeList(rightGroupTypeList);
			rightGroup.setRightGroupDetailList(rightGroupDetailList);

			r.setRightGroup(rightGroup);

			return r;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Transactional(rollbackFor=Exception.class)
	public boolean insert(Rule rule) {
		int row = ruleDao.insert(rule);
		if (row > 0) {
			long ruleId = rule.getRuleId();
			rule.getRightGroup().setRightGroupDescribe("001");
			rule.getRightGroup().setRuleId(ruleId);
			if(rule.getRightGroup().getRightGroupDetailList().size() > 0){
				rule.getRightGroup().setRightType(1);
			}else{
				rule.getRightGroup().setRightType(0);
			}
			rule.getRightGroup().setLastVer(rule.getLastVer());
			rule.getRightGroup().setRgCode(rule.getRgCode());
			rule.getRightGroup().setSetYear(rule.getSetYear());

			rightGroupDao.insert(rule.getRightGroup());

			String rightGroupId = rule.getRightGroup().getRightGroupId();

			for(RightGroupType rightGroupType : rule.getRightGroup().getRightGroupTypeList()){
				rightGroupType.setRightGroupId(rightGroupId);
				rightGroupType.setLastVer(rule.getLastVer());
				rightGroupType.setRgCode(rule.getRgCode());
				rightGroupType.setSetYear(rule.getSetYear());

				rightGroupTypeDao.insert(rightGroupType);
			}

			for(RightGroupDetail rightGroupDetail : rule.getRightGroup().getRightGroupDetailList()){
				rightGroupDetail.setRightGroupId(rightGroupId);
				rightGroupDetail.setLastVer(rule.getLastVer());
				rightGroupDetail.setRgCode(rule.getRgCode());
				rightGroupDetail.setSetYear(rule.getSetYear());
				rightGroupDetailDao.insert(rightGroupDetail);
			}
			return true;
		}else {
			return false;
		}
	}

    @Transactional(rollbackFor=Exception.class)
	public boolean update(Rule rule){
	    int row = ruleDao.update(rule);
        if (row > 0) {
            Rule r = ruleDao.get(rule);

            RightGroup rightGroup = new RightGroup();
            rightGroup.setRuleId(r.getRuleId());
            rightGroup = rightGroupDao.findAllList(rightGroup).get(0);
            String oldRightGroupId = rightGroup.getRightGroupId();

            rightGroupDao.delete(rightGroup);
            rule.getRightGroup().setRightGroupDescribe("001");
            rule.getRightGroup().setRuleId(rule.getRuleId());
            if(rule.getRightGroup().getRightGroupDetailList().size() > 0){
                rule.getRightGroup().setRightType(1);
            }else{
                rule.getRightGroup().setRightType(0);
            }
            rule.getRightGroup().setLastVer(rule.getLastVer());
            rule.getRightGroup().setRgCode(rule.getRgCode());
            rule.getRightGroup().setSetYear(rule.getSetYear());
            rightGroupDao.insert(rule.getRightGroup());


            RightGroupType rgt = new RightGroupType();
            rgt.setRightGroupId(oldRightGroupId);
            rightGroupTypeDao.delete(rgt);

            String rightGroupId = rule.getRightGroup().getRightGroupId();

            for(RightGroupType rightGroupType : rule.getRightGroup().getRightGroupTypeList()){
                rightGroupType.setRightGroupId(rightGroupId);
                rightGroupType.setLastVer(rule.getLastVer());
                rightGroupType.setRgCode(rule.getRgCode());
                rightGroupType.setSetYear(rule.getSetYear());

                rightGroupTypeDao.insert(rightGroupType);
            }

            RightGroupDetail rgd = new RightGroupDetail();
            rgd.setRightGroupId(oldRightGroupId);
            rightGroupDetailDao.delete(rgd);

            for(RightGroupDetail rightGroupDetail : rule.getRightGroup().getRightGroupDetailList()){
                rightGroupDetail.setRightGroupId(rightGroupId);
                rightGroupDetail.setLastVer(rule.getLastVer());
                rightGroupDetail.setRgCode(rule.getRgCode());
                rightGroupDetail.setSetYear(rule.getSetYear());
                rightGroupDetailDao.insert(rightGroupDetail);
            }

            return true;
        }else{
            return false;
        }
	}

	@Transactional(rollbackFor=Exception.class)
	public boolean delete(Rule rule){
		long ruleId = rule.getRuleId();
		int row = ruleDao.delete(rule);
		if(row > 0){
			RightGroup rightGroup = new RightGroup();
			rightGroup.setRuleId(ruleId);
			rightGroup = rightGroupDao.findAllList(rightGroup).get(0);
			String oldRightGroupId = rightGroup.getRightGroupId();

			rightGroupDao.delete(rightGroup);

			RightGroupType rgt = new RightGroupType();
			rgt.setRightGroupId(oldRightGroupId);
			rightGroupTypeDao.delete(rgt);

			RightGroupDetail rgd = new RightGroupDetail();
			rgd.setRightGroupId(oldRightGroupId);
			rightGroupDetailDao.delete(rgd);

			return true;
		}else {
			return false;
		}

	}
}
