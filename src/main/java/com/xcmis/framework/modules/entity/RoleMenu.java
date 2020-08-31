package com.xcmis.framework.modules.entity;

import java.io.Serializable;

public class RoleMenu implements Serializable{
    private long roleId;
    private String menuId;
    private int setYear;
    private String lastVer;
    private String rgCode;

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public int getSetYear() {
        return setYear;
    }

    public void setSetYear(int setYear) {
        this.setYear = setYear;
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
}
