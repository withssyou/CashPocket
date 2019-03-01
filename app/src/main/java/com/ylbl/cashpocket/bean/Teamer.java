package com.ylbl.cashpocket.bean;

public class Teamer {
    private String id;
    private String headImg;
    private String mobilePhone;
    private String registerTime;
    private String name;

    public Teamer() {
    }

    public Teamer(String id, String headImg, String mobilePhone, String registerTime, String name) {
        this.id = id;
        this.headImg = headImg;
        this.mobilePhone = mobilePhone;
        this.registerTime = registerTime;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
