package com.ylbl.cashpocket.bean;

public class AdsenseInfo {
    private String name;
    private String price;
    private String exposureNum;
    private String integralNum;
    private String sort;
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getExposureNum() {
        return exposureNum;
    }

    public void setExposureNum(String exposureNum) {
        this.exposureNum = exposureNum;
    }

    public String getIntegralNum() {
        return integralNum;
    }

    public void setIntegralNum(String integralNum) {
        this.integralNum = integralNum;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AdsenseInfo() {
    }

    public AdsenseInfo(String name, String price, String exposureNum, String integralNum, String sort, String id) {
        this.name = name;
        this.price = price;
        this.exposureNum = exposureNum;
        this.integralNum = integralNum;
        this.sort = sort;
        this.id = id;
    }
}
