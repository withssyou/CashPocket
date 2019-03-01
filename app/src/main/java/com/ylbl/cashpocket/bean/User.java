package com.ylbl.cashpocket.bean;

public class User {
    private String name;
    private String phone;
    private String pwd;
    private String token;
    public User() {
    }

    public User(String name, String phone, String pwd , String token) {
        this.name = name;
        this.phone = phone;
        this.pwd = pwd;
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
