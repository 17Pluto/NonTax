package com.xcmis.feefax.modules.entity;

import java.util.List;

public class PrintTable {

    private String chrId;
    private String chrCode;
    private String chrName;
    private String billtypeId;
    private String userId;
    private double tableX;
    private double tableY;
    private String tableImage;

    private List<PrintTableField> printTableFieldList;


    public List<PrintTableField> getPrintTableFieldList() {
        return printTableFieldList;
    }

    public void setPrintTableFieldList(List<PrintTableField> printTableFieldList) {
        this.printTableFieldList = printTableFieldList;
    }

    public double getTableX() {
        return tableX;
    }

    public void setTableX(double tableX) {
        this.tableX = tableX;
    }

    public double getTableY() {
        return tableY;
    }

    public void setTableY(double tableY) {
        this.tableY = tableY;
    }

    public String getTableImage() {
        return tableImage;
    }

    public void setTableImage(String tableImage) {
        this.tableImage = tableImage;
    }

    public String getChrId() {
        return chrId;
    }

    public void setChrId(String chrId) {
        this.chrId = chrId;
    }

    public String getChrCode() {
        return chrCode;
    }

    public void setChrCode(String chrCode) {
        this.chrCode = chrCode;
    }

    public String getChrName() {
        return chrName;
    }

    public void setChrName(String chrName) {
        this.chrName = chrName;
    }

    public String getBilltypeId() {
        return billtypeId;
    }

    public void setBilltypeId(String billtypeId) {
        this.billtypeId = billtypeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
