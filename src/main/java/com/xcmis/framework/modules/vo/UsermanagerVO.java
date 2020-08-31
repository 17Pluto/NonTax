package com.xcmis.framework.modules.vo;

import com.xcmis.feefax.modules.dao.EnterpriseDao;
import com.xcmis.feefax.modules.entity.Enterprise;
import com.xcmis.feefax.modules.entity.IncomeBank;
import com.xcmis.feefax.modules.entity.ManageBranch;
import com.xcmis.framework.modules.dto.LoginInfo;
import com.xcmis.framework.modules.entity.User;
import com.xcmis.framework.modules.entity.UserOrg;

/**
 * 
 */
public class UsermanagerVO implements Comparable<UsermanagerVO>{
	private String id;
	private String code;
	private String name;
	private String parentId;
	private String belongType;

    public UsermanagerVO(User user) {
		this.id = user.getUserId();
		this.code = user.getUserCode();
		this.name = user.getUserName();
		this.parentId = user.getBelongOrg();
		this.belongType = user.getBelongType();
    }

	public UsermanagerVO(Enterprise enterprise) {
		this.id = enterprise.getChrId();
		this.code = enterprise.getChrCode();
		this.name = enterprise.getChrName();
		this.parentId = enterprise.getParentId();
	}

	public UsermanagerVO(IncomeBank incomeBank) {
		this.id = incomeBank.getChrId();
		this.code = incomeBank.getChrCode();
		this.name = incomeBank.getChrName();
		this.parentId = incomeBank.getParentId();
	}

	public UsermanagerVO(ManageBranch manageBranch) {
		this.id = manageBranch.getChrId();
		this.code = manageBranch.getChrCode();
		this.name = manageBranch.getChrName();
		this.parentId = manageBranch.getParentId();
	}

	public UsermanagerVO(UserOrg userOrg) {
		this.id = userOrg.getOrgId();

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getBelongType() {
		return belongType;
	}

	public void setBelongType(String belongType) {
		this.belongType = belongType;
	}

	@Override
	public int compareTo(UsermanagerVO o) {
		return this.getCode().compareTo(o.getCode());
	}
}
