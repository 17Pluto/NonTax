package com.xcmis.framework.modules.dto;


import java.io.Serializable;

/**
 * 
 * 
 */

public class LoginInfo implements Serializable{
    private static final long serialVersionUID = -8399145456817349715L;
    private Integer deviceType;
    private Integer userType;
    private String word;
    private Long loginTime;
    private String ip;
    private Long loseTime;
    private Integer status;

    public LoginInfo(Integer deviceType, Integer userType, String word, Long loginTime, String ip, Integer status) {
        this.deviceType = deviceType;
        this.userType = userType;
        this.word = word;
        this.loginTime = loginTime;
        this.ip = ip;
        this.status = status;
    }

	public Integer getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
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

	public Long getLoseTime() {
		return loseTime;
	}

	public void setLoseTime(Long loseTime) {
		this.loseTime = loseTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
