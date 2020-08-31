package com.xcmis.framework.modules.entity;

import java.io.Serializable;

public class Permission implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4622802027392209267L;
	

    private String name;// 权限名称

    private String permission;// 权限字符串

    private String description;// 权限描述
    
    private String id;
    
    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	

}
