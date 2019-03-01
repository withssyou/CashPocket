package com.ylbl.cashpocket.bean;

public class DrawCashInfo {
    private long id;
    private double cashNum;
    private int wdWay;
    private String createTime;
    private int state;


    public DrawCashInfo() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getCashNum() {
        return cashNum;
    }

    public void setCashNum(double cashNum) {
        this.cashNum = cashNum;
    }

    public int getWdWay() {
        return wdWay;
    }

    public void setWdWay(int wdWay) {
        this.wdWay = wdWay;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
