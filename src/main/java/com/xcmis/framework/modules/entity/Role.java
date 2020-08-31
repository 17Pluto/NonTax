package com.xcmis.framework.modules.entity;

import java.io.Serializable;
import java.util.List;

public class Role implements Serializable{
    private String roleId;
    private String roleCode;
    private String roleName;
    private String userSysId;
    private int enabled;
    private String roleType;
    private String lastVer;
    private String rgCode;
    private int setYear;


    public String getRoleId() {        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getUserSysId() {
        return userSysId;
    }

    public void setUserSysId(String userSysId) {
        this.userSysId = userSysId;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getLastVer() {
        return lastVer;
    }

    public void setLastVer(String lastVer) {
        this.lastVer = lastVer;
    }

    public String getRgCode() {
        return rgCode;
    }

    public void setRgCode(String rgCode) {
        this.rgCode = rgCode;
    }

    public int getSetYear() {
        return setYear;
    }

    public void setSetYear(int setYear) {
        this.setYear = setYear;
    }
}
