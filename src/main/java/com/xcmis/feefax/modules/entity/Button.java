package com.xcmis.feefax.modules.entity;

public class Button {
    private String buttonId;
    private String actionId;
    private long moduleId;
    private int displayOrder;
    private String displayTitle;
    private String buttonDisplayTitle;
    private String lastVer;

    private String statusId;
    private String parentButtonId;

    private String actionCode;
    private String actionName;

    public String getButtonId() {
        return buttonId;
    }

    public void setButtonId(String buttonId) {
        this.buttonId = buttonId;
    }

    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public long getModuleId() {
        return moduleId;
    }

    public void setModuleId(long moduleId) {
        this.moduleId = moduleId;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getDisplayTitle() {
        return displayTitle;
    }

    public void setDisplayTitle(String displayTitle) {
        this.displayTitle = displayTitle;
    }

    public String getLastVer() {
        return lastVer;
    }

    public void setLastVer(String lastVer) {
        this.lastVer = lastVer;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getParentButtonId() {
        return parentButtonId;
    }

    public void setParentButtonId(String parentButtonId) {
        this.parentButtonId = parentButtonId;
    }

    public String getButtonDisplayTitle() {
        return buttonDisplayTitle;
    }

    public void setButtonDisplayTitle(String buttonDisplayTitle) {
        this.buttonDisplayTitle = buttonDisplayTitle;
    }

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }
}
