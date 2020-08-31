package com.xcmis.feefax.modules.entity;

import com.xcmis.framework.common.utils.StringUtils;

import java.io.Serializable;

/**
 * 功能
 *
 * @author
 * @see
 */
public class CommonEntity implements Serializable {
    private String setYear;
    private String chrId;
    private String chrCode;
    private String dispCode;
    private String chrName;
    private int levelNum;
    private int isLeaf;
    private int enabled;
    private String createDate;
    private String createUser;
    private String latestOpDate;
    private int isDeleted;
    private String latestOpUser;
    private String lastVer;
    private String chrCode1;
    private String chrCode2;
    private String chrCode3;
    private String chrCode4;
    private String chrCode5;
    private String rgCode;

    private String parentId;

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    private String parentName;
    private String chrId1;
    private String chrId2;
    private String chrId3;
    private String chrId4;
    private String chrId5;

    public String getSetYear() {
        return setYear;
    }

    public void setSetYear(String setYear) {
        this.setYear = setYear;
    }

    public String getChrId() {
        return chrId;
    }

    public void setChrId(String chrId) {
        this.chrId = chrId;
    }

    public String getChrCode() {
        chrCode = StringUtils.strNullToEmpty(chrCode);
        return chrCode;
    }

    public void setChrCode(String chrCode) {
        this.chrCode = chrCode;
    }

    public String getDispCode() {
        return dispCode;
    }

    public void setDispCode(String dispCode) {
        this.dispCode = dispCode;
    }

    public String getChrName() {
        return chrName;
    }

    public void setChrName(String chrName) {
        this.chrName = chrName;
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

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getLatestOpDate() {
        return latestOpDate;
    }

    public void setLatestOpDate(String latestOpDate) {
        this.latestOpDate = latestOpDate;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getLatestOpUser() {
        return latestOpUser;
    }

    public void setLatestOpUser(String latestOpUser) {
        this.latestOpUser = latestOpUser;
    }

    public String getLastVer() {
        return lastVer;
    }

    public void setLastVer(String lastVer) {
        this.lastVer = lastVer;
    }

    public String getChrCode1() {
        return chrCode1;
    }

    public void setChrCode1(String chrCode1) {
        this.chrCode1 = chrCode1;
    }

    public String getChrCode2() {
        return chrCode2;
    }

    public void setChrCode2(String chrCode2) {
        this.chrCode2 = chrCode2;
    }

    public String getChrCode3() {
        return chrCode3;
    }

    public void setChrCode3(String chrCode3) {
        this.chrCode3 = chrCode3;
    }

    public String getChrCode4() {
        return chrCode4;
    }

    public void setChrCode4(String chrCode4) {
        this.chrCode4 = chrCode4;
    }

    public String getChrCode5() {
        return chrCode5;
    }

    public void setChrCode5(String chrCode5) {
        this.chrCode5 = chrCode5;
    }

    public String getRgCode() {
        return rgCode;
    }

    public void setRgCode(String rgCode) {
        this.rgCode = rgCode;
    }


    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getChrId1() {
        return chrId1;
    }

    public void setChrId1(String chrId1) {
        this.chrId1 = chrId1;
    }

    public String getChrId2() {
        return chrId2;
    }

    public void setChrId2(String chrId2) {
        this.chrId2 = chrId2;
    }

    public String getChrId3() {
        return chrId3;
    }

    public void setChrId3(String chrId3) {
        this.chrId3 = chrId3;
    }

    public String getChrId4() {
        return chrId4;
    }

    public void setChrId4(String chrId4) {
        this.chrId4 = chrId4;
    }

    public String getChrId5() {
        return chrId5;
    }

    public void setChrId5(String chrId5) {
        this.chrId5 = chrId5;
    }
}
