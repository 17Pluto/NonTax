package com.xcmis.framework.modules.vo;

import com.xcmis.framework.modules.entity.User;

public class ManagerVO {
	private Long id;
    private String userName;
    private String password;
    //private String loginDate;
    private String token;
    private String ip;
    
    public ManagerVO() {
    	
    }
    
    public ManagerVO(User user, String token) {
        //this.id = user.getId();
        this.userName = user.getUserName();
        this.password = user.getPassword();
        //this.loginDate = user.getLoginDate();
        this.token = token;
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

	//public String getLoginDate() {
		//return loginDate;
	//}

	//public void setLoginDate(String loginDate) {
		//this.loginDate = loginDate;
	//}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}


	public String getIp() {
		return ip;
	}


	public void setIp(String ip) {
		this.ip = ip;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}