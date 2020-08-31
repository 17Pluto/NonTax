package com.xcmis.framework.modules.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class User implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String userId;
	private String userName;	// 登录名
	private String password;	// 密码
	private String userCode;		// 姓名
	private String rgCode;


	private String orgType;
	private String orgCode;
	private int levelNum;
	private int isleaf;
	private int gender;
	private String telephone;
	private String mobile;
	private int enabled;
	private String headshipCode;
	private String birthday;
	private String address;
	private String email;
	private int userType;
	private int isAudit;
	private String auditDate;
	private String auditUser;
	private String nickname;
	private String lastVer;
	private String mbId;
	private String belongOrg;
	private String belongType;
	private String securityLevel;
	private long initPassword;
	private String caSerial;
	private String identityCard;
	private String gpOrg;
	private String titleTech;
	private String rtxuin;
	private String photo;
	private String photoBlobid;
	private String coName;
	private int empIndex;
	private String paymentPassword;
	private String setYear;
	private int isEditPwd;
	private int isThreeSecurity;
	private int isEverLocked;
	private String lockedDate;
	private String imsi;
	private String imei;
	private String weixin;
	private String isBlacklist;
	private String belongOrgAll;


	private List<UserOrg> userOrgList;
	private List<UserRule> userRuleList;
	private List<UserRoleRule> userRoleRuleList;


	public String getBelongOrgAll() {
		return belongOrgAll;
	}

	public void setBelongOrgAll(String belongOrgAll) {
		this.belongOrgAll = belongOrgAll;
	}

	public String getRgCode() {
		return rgCode;
	}

	public void setRgCode(String rgCode) {
		this.rgCode = rgCode;
	}

	public List<UserRule> getUserRuleList() {
		return userRuleList;
	}

	public void setUserRuleList(List<UserRule> userRuleList) {
		this.userRuleList = userRuleList;
	}

	public List<UserOrg> getUserOrgList() {
		return userOrgList;
	}

	public void setUserOrgList(List<UserOrg> userOrgList) {
		this.userOrgList = userOrgList;
	}

	public List<UserRoleRule> getUserRoleRuleList() {
		return userRoleRuleList;
	}

	public void setUserRoleRuleList(List<UserRoleRule> userRoleRuleList) {
		this.userRoleRuleList = userRoleRuleList;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public int getLevelNum() {
		return levelNum;
	}

	public void setLevelNum(int levelNum) {
		this.levelNum = levelNum;
	}

	public int getIsleaf() {
		return isleaf;
	}

	public void setIsleaf(int isleaf) {
		this.isleaf = isleaf;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public String getHeadshipCode() {
		return headshipCode;
	}

	public void setHeadshipCode(String headshipCode) {
		this.headshipCode = headshipCode;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public int getIsAudit() {
		return isAudit;
	}

	public void setIsAudit(int isAudit) {
		this.isAudit = isAudit;
	}

	public String getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(String auditDate) {
		this.auditDate = auditDate;
	}

	public String getAuditUser() {
		return auditUser;
	}

	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getLastVer() {
		return lastVer;
	}

	public void setLastVer(String lastVer) {
		this.lastVer = lastVer;
	}

	public String getMbId() {
		return mbId;
	}

	public void setMbId(String mbId) {
		this.mbId = mbId;
	}

	public String getBelongOrg() {
		return belongOrg;
	}

	public void setBelongOrg(String belongOrg) {
		this.belongOrg = belongOrg;
	}

	public String getBelongType() {
		return belongType;
	}

	public void setBelongType(String belongType) {
		this.belongType = belongType;
	}

	public String getSecurityLevel() {
		return securityLevel;
	}

	public void setSecurityLevel(String securityLevel) {
		this.securityLevel = securityLevel;
	}

	public long getInitPassword() {
		return initPassword;
	}

	public void setInitPassword(long initPassword) {
		this.initPassword = initPassword;
	}

	public String getCaSerial() {
		return caSerial;
	}

	public void setCaSerial(String caSerial) {
		this.caSerial = caSerial;
	}

	public String getIdentityCard() {
		return identityCard;
	}

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}

	public String getGpOrg() {
		return gpOrg;
	}

	public void setGpOrg(String gpOrg) {
		this.gpOrg = gpOrg;
	}

	public String getTitleTech() {
		return titleTech;
	}

	public void setTitleTech(String titleTech) {
		this.titleTech = titleTech;
	}

	public String getRtxuin() {
		return rtxuin;
	}

	public void setRtxuin(String rtxuin) {
		this.rtxuin = rtxuin;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getPhotoBlobid() {
		return photoBlobid;
	}

	public void setPhotoBlobid(String photoBlobid) {
		this.photoBlobid = photoBlobid;
	}

	public String getCoName() {
		return coName;
	}

	public void setCoName(String coName) {
		this.coName = coName;
	}

	public int getEmpIndex() {
		return empIndex;
	}

	public void setEmpIndex(int empIndex) {
		this.empIndex = empIndex;
	}

	public String getPaymentPassword() {
		return paymentPassword;
	}

	public void setPaymentPassword(String paymentPassword) {
		this.paymentPassword = paymentPassword;
	}

	public String getSetYear() {
		return setYear;
	}

	public void setSetYear(String setYear) {
		this.setYear = setYear;
	}

	public int getIsEditPwd() {
		return isEditPwd;
	}

	public void setIsEditPwd(int isEditPwd) {
		this.isEditPwd = isEditPwd;
	}

	public int getIsThreeSecurity() {
		return isThreeSecurity;
	}

	public void setIsThreeSecurity(int isThreeSecurity) {
		this.isThreeSecurity = isThreeSecurity;
	}

	public int getIsEverLocked() {
		return isEverLocked;
	}

	public void setIsEverLocked(int isEverLocked) {
		this.isEverLocked = isEverLocked;
	}

	public String getLockedDate() {
		return lockedDate;
	}

	public void setLockedDate(String lockedDate) {
		this.lockedDate = lockedDate;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getWeixin() {
		return weixin;
	}

	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}

	public String getIsBlacklist() {
		return isBlacklist;
	}

	public void setIsBlacklist(String isBlacklist) {
		this.isBlacklist = isBlacklist;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
}
