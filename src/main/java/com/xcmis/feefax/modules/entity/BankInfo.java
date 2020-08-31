package com.xcmis.feefax.modules.entity;

/**
 * @Author fangzhe
 * @Date 2020/8/18 12:16 下午
 * @Version 1.0
 */
public class BankInfo {
    private String chrId;
    private String createDate;
    private String createType;
    private String content;

    public String getChrId() {
        return chrId;
    }

    public void setChrId(String chrId) {
        this.chrId = chrId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateType() {
        return createType;
    }

    public void setCreateType(String createType) {
        this.createType = createType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
