package com.xcmis.feefax.modules.entity;

import java.util.List;

public class Ienusermanag {
    private String chrId;
    private String ienId;
    private String userId;
    private String setYear;
    private String lastVer;
    private int isBillmanager;

    private String eieChrCode;
    private String eieChrName;
    private String ruChrCode;
    private String ruChrName;
    private List<String> ienIds;

    public List<String> getIenIds() {
        return ienIds;
    }

    public void setIenIds(List<String> ienIds) {
        this.ienIds = ienIds;
    }

    public String getEieChrCode() {
        return eieChrCode;
    }

    public void setEieChrCode(String eieChrCode) {
        this.eieChrCode = eieChrCode;
    }

    public String getEieChrName() {
        return eieChrName;
    }

    public void setEieChrName(String eieChrName) {
        this.eieChrName = eieChrName;
    }

    public String getRuChrCode() {
        return ruChrCode;
    }

    public void setRuChrCode(String ruChrCode) {
        this.ruChrCode = ruChrCode;
    }

    public String getRuChrName() {
        return ruChrName;
    }

    public void setRuChrName(String ruChrName) {
        this.ruChrName = ruChrName;
    }

    public String getChrId() {
        return chrId;
    }

    public void setChrId(String chrId) {
        this.chrId = chrId;
    }

    public String getIenId() {
        return ienId;
    }

    public void setIenId(String ienId) {
        this.ienId = ienId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSetYear() {
        return setYear;
    }

    public void setSetYear(String setYear) {
        this.setYear = setYear;
    }

    public String getLastVer() {
        return lastVer;
    }

    public void setLastVer(String lastVer) {
        this.lastVer = lastVer;
    }

    public int getIsBillmanager() {
        return isBillmanager;
    }

    public void setIsBillmanager(int isBillmanager) {
        this.isBillmanager = isBillmanager;
    }
}
