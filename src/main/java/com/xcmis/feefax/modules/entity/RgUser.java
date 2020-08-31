package com.xcmis.feefax.modules.entity;

public class RgUser extends CommonEntity{

    private String RgCode1;
    private String RgId;
    private String RgCodeId;


    public String getRgCodeId() {
        return RgCodeId;
    }

    public void setRgCodeId(String rgCodeId) {
        RgCodeId = rgCodeId;
    }

    public String getRgId() {
        return RgId;
    }

    public void setRgId(String rgId) {
        RgId = rgId;
    }

    public String getRgCode1() {
        return RgCode1;
    }

    public void setRgCode1(String rgCode1) {
        RgCode1 = rgCode1;
    }
}
