package com.xcmis.feefax.modules.entity;

import java.util.List;

public class Module {
    private long moduleId;
    private String moduleCode;
    private String moduleName;
    private String sysId;
    private int moduleType;
    private int enabled;
    private String lastVer;
    private List<Button> buttonList;
    private List<Status> statusList;
    private List<ModuleStatusButton> moduleStatusButtonList;


    private long menuId;

    public long getModuleId() {
        return moduleId;
    }

    public void setModuleId(long moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getSysId() {
        return sysId;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId;
    }

    public int getModuleType() {
        return moduleType;
    }

    public void setModuleType(int moduleType) {
        this.moduleType = moduleType;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public List<Button> getButtonList() {
        return buttonList;
    }

    public void setButtonList(List<Button> buttonList) {
        this.buttonList = buttonList;
    }

    public List<Status> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Status> statusList) {
        this.statusList = statusList;
    }

    public String getLastVer() {
        return lastVer;
    }

    public void setLastVer(String lastVer) {
        this.lastVer = lastVer;
    }

    public long getMenuId() {
        return menuId;
    }

    public void setMenuId(long menuId) {
        this.menuId = menuId;
    }

    public List<ModuleStatusButton> getModuleStatusButtonList() {
        return moduleStatusButtonList;
    }

    public void setModuleStatusButtonList(List<ModuleStatusButton> moduleStatusButtonList) {
        this.moduleStatusButtonList = moduleStatusButtonList;
    }
}
