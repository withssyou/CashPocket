package com.ylbl.cashpocket.bean;

public class RecordsInfo {
    private String memId;
    private String memName;
    private String memHeadImg;
    private String recTime;
    private String money;

    public RecordsInfo(String memId, String memName, String memHeadImg, String recTime,String money) {
        this.memId = memId;
        this.memName = memName;
        this.memHeadImg = memHeadImg;
        this.recTime = recTime;
        this.money = money;
    }

    public RecordsInfo() {
    }

    public String getMemId() {
        return memId;
    }

    public void setMemId(String memId) {
        this.memId = memId;
    }

    public String getMemName() {
        return memName;
    }

    public void setMemName(String memName) {
        this.memName = memName;
    }

    public String getMemHeadImg() {
        return memHeadImg;
    }

    public void setMemHeadImg(String memHeadImg) {
        this.memHeadImg = memHeadImg;
    }

    public String getRecTime() {
        return recTime;
    }

    public void setRecTime(String recTime) {
        this.recTime = recTime;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
