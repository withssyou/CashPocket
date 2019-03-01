package com.ylbl.cashpocket.bean;

import java.io.Serializable;

public class ResultInfo<T> implements Serializable {

    private String msg;
    private T data;
    private String code;
    private String total;
    private String totalMoney;
    private String poolMoney;
    private String token;
    private String qrCode;
    private String extUrl;



    public ResultInfo() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getPoolMoney() {
        return poolMoney;
    }

    public void setPoolMoney(String poolMoney) {
        this.poolMoney = poolMoney;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getExtUrl() {
        return extUrl;
    }

    public void setExtUrl(String extUrl) {
        this.extUrl = extUrl;
    }

    public ResultInfo(String msg, T data, String code, String total, String totalMoney, String poolMoney, String token) {
        this.msg = msg;
        this.data = data;
        this.code = code;
        this.total = total;
        this.totalMoney = totalMoney;
        this.poolMoney = poolMoney;
        this.token = token;
    }
}
