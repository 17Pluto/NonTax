package com.xcmis.feefax.modules.entity;

import java.util.List;

public class Menu {
    private long menuId;
    private String menuCode;
    private String menuName;
    private int enabled;
    private int levelNum;
    private int isLeaf;
    private String userSysId;
    private String lastVer;
    private String screentype;
    private int dispOrder;
    private String userName;
    private String userId;
    private String resourceName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getDispOrder() {
        return dispOrder;
    }

    public void setDispOrder(int dispOrder) {
        this.dispOrder = dispOrder;
    }

    private List<MenuModule> menuModuleList;

    public long getMenuId() {
        return menuId;
    }

    public void setMenuId(long menuId) {
        this.menuId = menuId;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public int getLevelNum() {
        return levelNum;
    }

    public void setLevelNum(int levelNum) {
        this.levelNum = levelNum;
    }

    public int getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(int isLeaf) {
        this.isLeaf = isLeaf;
    }

    public String getUserSysId() {
        return userSysId;
    }

    public void setUserSysId(String userSysId) {
        this.userSysId = userSysId;
    }

    public String getLastVer() {
        return lastVer;
    }

    public void setLastVer(String lastVer) {
        this.lastVer = lastVer;
    }

    public String getScreentype() {
        return screentype;
    }

    public void setScreentype(String screentype) {
        this.screentype = screentype;
    }

    public List<MenuModule> getMenuModuleList() {
        return menuModuleList;
    }

    public void setMenuModuleList(List<MenuModule> menuModuleList) {
        this.menuModuleList = menuModuleList;
    }
}
