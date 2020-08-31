package com.xcmis.feefax.modules.entity;

public class Orgtype {
    private String orgtypeCode;
    private String orgtypeName;
    private String eleCode;
    private String lastVer;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOrgtypeCode() {
        return orgtypeCode;
    }

    public void setOrgtypeCode(String orgtypeCode) {
        this.orgtypeCode = orgtypeCode;
    }

    public String getOrgtypeName() {
        return orgtypeName;
    }

    public void setOrgtypeName(String orgtypeName) {
        this.orgtypeName = orgtypeName;
    }

    public String getEleCode() {
        return eleCode;
    }

    public void setEleCode(String eleCode) {
        this.eleCode = eleCode;
    }

    public String getLastVer() {
        return lastVer;
    }

    public void setLastVer(String lastVer) {
        this.lastVer = lastVer;
    }
}
