package com.xcmis.framework.modules.entity;

import java.io.Serializable;

public class Element implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2374303179359734190L;
	
	private String chrId;
	private String setYear;
	private String eleSource;
	private String eleCode;
	private String eleName;
	private int enabled;
	private int dispmode;
	private int refMode;
	private int isRightfilter;
	private int maxLevel;
	private String codeRule;
	private String levelName;
	private String createDate;
	private String createUser;
	private String latestOpDate;
	private String latestOpUser;
	
	private int isDeleted;
	private int isNolevel;
	private int isLocal;
	private int isSystem;
	private int eleType;
	private int isView;
	private String czgbCode;
	private String lastVer;
	private int dispOrder;
	private String sysId;
	private int isOperate;
	private String rgCode;
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getChrId() {
		return chrId;
	}
	public void setChrId(String chrId) {
		this.chrId = chrId;
	}
	public String getSetYear() {
		return setYear;
	}
	public void setSetYear(String setYear) {
		this.setYear = setYear;
	}
	public String getEleSource() {
		return eleSource;
	}
	public void setEleSource(String eleSource) {
		this.eleSource = eleSource;
	}
	public String getEleCode() {
		return eleCode;
	}
	public void setEleCode(String eleCode) {
		this.eleCode = eleCode;
	}
	public String getEleName() {
		return eleName;
	}
	public void setEleName(String eleName) {
		this.eleName = eleName;
	}
	public int getEnabled() {
		return enabled;
	}
	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
	public int getDispmode() {
		return dispmode;
	}
	public void setDispmode(int dispmode) {
		this.dispmode = dispmode;
	}
	public int getRefMode() {
		return refMode;
	}
	public void setRefMode(int refMode) {
		this.refMode = refMode;
	}
	public int getIsRightfilter() {
		return isRightfilter;
	}
	public void setIsRightfilter(int isRightfilter) {
		this.isRightfilter = isRightfilter;
	}
	public int getMaxLevel() {
		return maxLevel;
	}
	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
	}
	public String getCodeRule() {
		return codeRule;
	}
	public void setCodeRule(String codeRule) {
		this.codeRule = codeRule;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getLatestOpDate() {
		return latestOpDate;
	}
	public void setLatestOpDate(String latestOpDate) {
		this.latestOpDate = latestOpDate;
	}
	public String getLatestOpUser() {
		return latestOpUser;
	}
	public void setLatestOpUser(String latestOpUser) {
		this.latestOpUser = latestOpUser;
	}
	public int getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}
	public int getIsNolevel() {
		return isNolevel;
	}
	public void setIsNolevel(int isNolevel) {
		this.isNolevel = isNolevel;
	}
	public int getIsLocal() {
		return isLocal;
	}
	public void setIsLocal(int isLocal) {
		this.isLocal = isLocal;
	}
	public int getIsSystem() {
		return isSystem;
	}
	public void setIsSystem(int isSystem) {
		this.isSystem = isSystem;
	}
	public int getEleType() {
		return eleType;
	}
	public void setEleType(int eleType) {
		this.eleType = eleType;
	}
	public int getIsView() {
		return isView;
	}
	public void setIsView(int isView) {
		this.isView = isView;
	}
	public String getCzgbCode() {
		return czgbCode;
	}
	public void setCzgbCode(String czgbCode) {
		this.czgbCode = czgbCode;
	}
	public String getLastVer() {
		return lastVer;
	}
	public void setLastVer(String lastVer) {
		this.lastVer = lastVer;
	}
	public int getDispOrder() {
		return dispOrder;
	}
	public void setDispOrder(int dispOrder) {
		this.dispOrder = dispOrder;
	}
	public String getSysId() {
		return sysId;
	}
	public void setSysId(String sysId) {
		this.sysId = sysId;
	}
	public int getIsOperate() {
		return isOperate;
	}
	public void setIsOperate(int isOperate) {
		this.isOperate = isOperate;
	}
	public String getRgCode() {
		return rgCode;
	}
	public void setRgCode(String rgCode) {
		this.rgCode = rgCode;
	}

}
