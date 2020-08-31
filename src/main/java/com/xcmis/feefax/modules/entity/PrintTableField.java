package com.xcmis.feefax.modules.entity;

public class PrintTableField {
    private String chrId;
    private String mainId;
    private String fieldCode;
    private String fieldName;
    private double fieldTop;
    private double fieldLeft;
    private double fieldHeight;
    private double fieldWidth;
    private String isShow;


    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getChrId() {
        return chrId;
    }

    public void setChrId(String chrId) {
        this.chrId = chrId;
    }

    public String getMainId() {
        return mainId;
    }

    public void setMainId(String mainId) {
        this.mainId = mainId;
    }

    public String getFieldCode() {
        return fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public double getFieldTop() {
        return fieldTop;
    }

    public void setFieldTop(double fieldTop) {
        this.fieldTop = fieldTop;
    }

    public double getFieldLeft() {
        return fieldLeft;
    }

    public void setFieldLeft(double fieldLeft) {
        this.fieldLeft = fieldLeft;
    }

    public double getFieldHeight() {
        return fieldHeight;
    }

    public void setFieldHeight(double fieldHeight) {
        this.fieldHeight = fieldHeight;
    }

    public double getFieldWidth() {
        return fieldWidth;
    }

    public void setFieldWidth(double fieldWidth) {
        this.fieldWidth = fieldWidth;
    }
}
