package com.xcmis.framework.modules.vo;

import com.xcmis.framework.modules.dto.LoginInfo;

/**
 * 
 */
public class LoginInfoVO {
    private Integer userType;
    private String word;
    private Long loginTime;
    private String ip;

    public LoginInfoVO(LoginInfo info) {
        this.userType = info.getUserType();
        this.word = info.getWord();
        this.loginTime = info.getLoginTime();
        this.ip = info.getIp();
    }

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public Long getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Long loginTime) {
		this.loginTime = loginTime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
