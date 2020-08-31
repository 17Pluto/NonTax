package com.xcmis.feefax.modules.entity;

import java.util.List;

/**
 * 功能
 *
 * @author
 * @see
 */
public class BillfundConfirm {
    private String date;
    private String accconfirm_no;
    private int succeedcount;
    private int failcount;

    private List<Fail> faillist;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAccconfirm_no() {
        return accconfirm_no;
    }

    public void setAccconfirm_no(String accconfirm_no) {
        this.accconfirm_no = accconfirm_no;
    }

    public int getSucceedcount() {
        return succeedcount;
    }

    public void setSucceedcount(int succeedcount) {
        this.succeedcount = succeedcount;
    }

    public int getFailcount() {
        return failcount;
    }

    public void setFailcount(int failcount) {
        this.failcount = failcount;
    }

    public List<Fail> getFaillist() {
        return faillist;
    }

    public void setFaillist(List<Fail> faillist) {
        this.faillist = faillist;
    }
}
