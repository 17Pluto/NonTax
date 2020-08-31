package com.xcmis.feefax.modules.entity;

import java.math.BigDecimal;

/**
 * 功能
 *
 * @author
 * @see
 */
public class Paylist {
    private String item_code;
    private String item_name;
    private BigDecimal item_amount;
    private String unit;
    private double num;
    private String stdtype;
    private String standard;


    public String getItem_code() {
        return item_code;
    }

    public void setItem_code(String item_code) {
        this.item_code = item_code;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public BigDecimal getItem_amount() {
        return item_amount.setScale(2, BigDecimal.ROUND_DOWN);
    }

    public void setItem_amount(BigDecimal item_amount) {
        this.item_amount = item_amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getNum() {
        return num;
    }

    public void setNum(double num) {
        this.num = num;
    }

    public String getStdtype() {
        return stdtype;
    }

    public void setStdtype(String stdtype) {
        this.stdtype = stdtype;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }
}
