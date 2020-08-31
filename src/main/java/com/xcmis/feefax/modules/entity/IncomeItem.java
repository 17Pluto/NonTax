package com.xcmis.feefax.modules.entity;

public class IncomeItem extends CommonEntity implements Comparable<IncomeItem>{
	private String chrCode6;
	private String chrCode7;
	private String chrCode8;
	private String chrCode9;
	private String chrId6;
	private String chrId7;
	private String chrId8;
	private String chrId9;
	private String itemsortId;
	private String chargekindId;
	private String managebranch;
	private String beginTime;
	private String endTime;
	private String remark;
	private String keyword;
	private String attachs;
	private long uplimit;
	private long lowlimit;
	private String ancestorId;
	private String measure;
	private int feestandardtype;
	private int enableUsedate;
	private String fileNo;
	private String inBsId;

	private int costtype;
	private double costprice;
	private String preitemid;
	private String mkId;
	private String inItematId;
	private int isChange;
	private String changeApplyUser;
	private String changeAuditUser;
	private String changeDate;
	private String changeFileId;
	private String gatherItemid;
	private String divprovinceId;
	private int isDiv;
	private int isEnd;
	private int isCommon;
	private String backremark;
	private String divstandardId;
	private String divstandardCode;
	private int divMode;
	private String centerValue;
	private String provinceValue;
	private String cityValue;
	private String countyValue;
	private int isParent;
	private int isAllUse;
	private String parentIdAll;
	private String inBsIdAll;
	private String mkIdAll;
	private String inItematIdAll;
	private String divstandardIdAll;
	private String itemsortIdAll;
	private String chargekindIdAll;

	private String inItematName;

	private String itemsortName;
	private String chargekindName;
	private String inBsName;
	private String mkName;

	private String stateCode;
	private String unitItemId;
	private String compareDate;

	public String getCompareDate() {
		return compareDate;
	}

	public void setCompareDate(String compareDate) {
		this.compareDate = compareDate;
	}

	public String getUnitItemId() {
		return unitItemId;
	}

	public void setUnitItemId(String unitItemId) {
		this.unitItemId = unitItemId;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getInItematName() {
		return inItematName;
	}

	public void setInItematName(String inItematName) {
		this.inItematName = inItematName;
	}

	public String getMkName() {
		return mkName;
	}

	public void setMkName(String mkName) {
		this.mkName = mkName;
	}

	public String getInBsName() {
		return inBsName;
	}

	public void setInBsName(String inBsName) {
		this.inBsName = inBsName;
	}

	public String getChargekindName() {
		return chargekindName;
	}

	public void setChargekindName(String chargekindName) {
		this.chargekindName = chargekindName;
	}

	public String getItemsortName() {
		return itemsortName;
	}

	public void setItemsortName(String itemsortName) {
		this.itemsortName = itemsortName;
	}

	public String getChargekindIdAll() {
		return chargekindIdAll;
	}

	public void setChargekindIdAll(String chargekindIdAll) {
		this.chargekindIdAll = chargekindIdAll;
	}

	public String getItemsortIdAll() {
		return itemsortIdAll;
	}

	public void setItemsortIdAll(String itemsortIdAll) {
		this.itemsortIdAll = itemsortIdAll;
	}

	public String getDivstandardIdAll() {
		return divstandardIdAll;
	}

	public void setDivstandardIdAll(String divstandardIdAll) {
		this.divstandardIdAll = divstandardIdAll;
	}

	public String getInItematIdAll() {
		return inItematIdAll;
	}

	public void setInItematIdAll(String inItematIdAll) {
		this.inItematIdAll = inItematIdAll;
	}

	public String getMkIdAll() {
		return mkIdAll;
	}

	public void setMkIdAll(String mkIdAll) {
		this.mkIdAll = mkIdAll;
	}

	public String getInBsIdAll() {
		return inBsIdAll;
	}

	public void setInBsIdAll(String inBsIdAll) {
		this.inBsIdAll = inBsIdAll;
	}

	public String getParentIdAll() {
		return parentIdAll;
	}

	public void setParentIdAll(String parentIdAll) {
		this.parentIdAll = parentIdAll;
	}

	public String getChrCode6() {
		return chrCode6;
	}

	public void setChrCode6(String chrCode6) {
		this.chrCode6 = chrCode6;
	}

	public String getChrCode7() {
		return chrCode7;
	}

	public void setChrCode7(String chrCode7) {
		this.chrCode7 = chrCode7;
	}

	public String getChrCode8() {
		return chrCode8;
	}

	public void setChrCode8(String chrCode8) {
		this.chrCode8 = chrCode8;
	}

	public String getChrCode9() {
		return chrCode9;
	}

	public void setChrCode9(String chrCode9) {
		this.chrCode9 = chrCode9;
	}

	public String getChrId6() {
		return chrId6;
	}

	public void setChrId6(String chrId6) {
		this.chrId6 = chrId6;
	}

	public String getChrId7() {
		return chrId7;
	}

	public void setChrId7(String chrId7) {
		this.chrId7 = chrId7;
	}

	public String getChrId8() {
		return chrId8;
	}

	public void setChrId8(String chrId8) {
		this.chrId8 = chrId8;
	}

	public String getChrId9() {
		return chrId9;
	}

	public void setChrId9(String chrId9) {
		this.chrId9 = chrId9;
	}

	public String getItemsortId() {
		return itemsortId;
	}

	public void setItemsortId(String itemsortId) {
		this.itemsortId = itemsortId;
	}

	public String getChargekindId() {
		return chargekindId;
	}

	public void setChargekindId(String chargekindId) {
		this.chargekindId = chargekindId;
	}

	public String getManagebranch() {
		return managebranch;
	}

	public void setManagebranch(String managebranch) {
		this.managebranch = managebranch;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getAttachs() {
		return attachs;
	}

	public void setAttachs(String attachs) {
		this.attachs = attachs;
	}

	public long getUplimit() {
		return uplimit;
	}

	public void setUplimit(long uplimit) {
		this.uplimit = uplimit;
	}

	public long getLowlimit() {
		return lowlimit;
	}

	public void setLowlimit(long lowlimit) {
		this.lowlimit = lowlimit;
	}

	public String getAncestorId() {
		return ancestorId;
	}

	public void setAncestorId(String ancestorId) {
		this.ancestorId = ancestorId;
	}

	public String getMeasure() {
		return measure;
	}

	public void setMeasure(String measure) {
		this.measure = measure;
	}

	public int getFeestandardtype() {
		return feestandardtype;
	}

	public void setFeestandardtype(int feestandardtype) {
		this.feestandardtype = feestandardtype;
	}

	public int getEnableUsedate() {
		return enableUsedate;
	}

	public void setEnableUsedate(int enableUsedate) {
		this.enableUsedate = enableUsedate;
	}

	public String getFileNo() {
		return fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}

	public String getInBsId() {
		return inBsId;
	}

	public void setInBsId(String inBsId) {
		this.inBsId = inBsId;
	}

	public int getCosttype() {
		return costtype;
	}

	public void setCosttype(int costtype) {
		this.costtype = costtype;
	}

	public double getCostprice() {
		return costprice;
	}

	public void setCostprice(double costprice) {
		this.costprice = costprice;
	}

	public String getPreitemid() {
		return preitemid;
	}

	public void setPreitemid(String preitemid) {
		this.preitemid = preitemid;
	}

	public String getMkId() {
		return mkId;
	}

	public void setMkId(String mkId) {
		this.mkId = mkId;
	}

	public String getInItematId() {
		return inItematId;
	}

	public void setInItematId(String inItematId) {
		this.inItematId = inItematId;
	}

	public int getIsChange() {
		return isChange;
	}

	public void setIsChange(int isChange) {
		this.isChange = isChange;
	}

	public String getChangeApplyUser() {
		return changeApplyUser;
	}

	public void setChangeApplyUser(String changeApplyUser) {
		this.changeApplyUser = changeApplyUser;
	}

	public String getChangeAuditUser() {
		return changeAuditUser;
	}

	public void setChangeAuditUser(String changeAuditUser) {
		this.changeAuditUser = changeAuditUser;
	}

	public String getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(String changeDate) {
		this.changeDate = changeDate;
	}

	public String getChangeFileId() {
		return changeFileId;
	}

	public void setChangeFileId(String changeFileId) {
		this.changeFileId = changeFileId;
	}

	public String getGatherItemid() {
		return gatherItemid;
	}

	public void setGatherItemid(String gatherItemid) {
		this.gatherItemid = gatherItemid;
	}

	public String getDivprovinceId() {
		return divprovinceId;
	}

	public void setDivprovinceId(String divprovinceId) {
		this.divprovinceId = divprovinceId;
	}

	public int getIsDiv() {
		return isDiv;
	}

	public void setIsDiv(int isDiv) {
		this.isDiv = isDiv;
	}

	public int getIsEnd() {
		return isEnd;
	}

	public void setIsEnd(int isEnd) {
		this.isEnd = isEnd;
	}

	public int getIsCommon() {
		return isCommon;
	}

	public void setIsCommon(int isCommon) {
		this.isCommon = isCommon;
	}

	public String getBackremark() {
		return backremark;
	}

	public void setBackremark(String backremark) {
		this.backremark = backremark;
	}

	public String getDivstandardId() {
		return divstandardId;
	}

	public void setDivstandardId(String divstandardId) {
		this.divstandardId = divstandardId;
	}

	public String getDivstandardCode() {
		return divstandardCode;
	}

	public void setDivstandardCode(String divstandardCode) {
		this.divstandardCode = divstandardCode;
	}

	public int getDivMode() {
		return divMode;
	}

	public void setDivMode(int divMode) {
		this.divMode = divMode;
	}

	public String getCenterValue() {
		return centerValue;
	}

	public void setCenterValue(String centerValue) {
		this.centerValue = centerValue;
	}

	public String getProvinceValue() {
		return provinceValue;
	}

	public void setProvinceValue(String provinceValue) {
		this.provinceValue = provinceValue;
	}

	public String getCityValue() {
		return cityValue;
	}

	public void setCityValue(String cityValue) {
		this.cityValue = cityValue;
	}

	public String getCountyValue() {
		return countyValue;
	}

	public void setCountyValue(String countyValue) {
		this.countyValue = countyValue;
	}

	public int getIsParent() {
		return isParent;
	}

	public void setIsParent(int isParent) {
		this.isParent = isParent;
	}

	public int getIsAllUse() {
		return isAllUse;
	}

	public void setIsAllUse(int isAllUse) {
		this.isAllUse = isAllUse;
	}

	@Override
	public int compareTo(IncomeItem o) {
		return this.getChrCode().compareTo(o.getChrCode());
	}
}
