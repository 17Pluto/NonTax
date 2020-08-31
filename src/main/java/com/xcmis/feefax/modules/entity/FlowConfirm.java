package com.xcmis.feefax.modules.entity;

import java.util.List;

/**
 * 功能
 *
 * @author
 * @see
 */
public class FlowConfirm {
    private String date;
    private String accflow_no;
    private int succeedcount;
    private int failcount;

    private List<Fail> faillist;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getAccflow_no() {
        return accflow_no;
    }

    public void setAccflow_no(String accflow_no) {
        this.accflow_no = accflow_no;
    }
}
