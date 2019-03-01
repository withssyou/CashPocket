package com.ylbl.cashpocket.bean;

public class AdLordInfo {
    private String id;
    private String name;
    private String price;
    private String exposureNum;
    private String intetralNum;
    private String isPublish;
    private String createTime;
    private String icon;

    public AdLordInfo() {
    }

    public AdLordInfo(String id, String name, String price, String exposureNum,
                      String intetralNum, String isPublish, String createTime ,String icon) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.exposureNum = exposureNum;
        this.intetralNum = intetralNum;
        this.isPublish = isPublish;
        this.createTime = createTime;
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getIntetralNum() {
        return intetralNum;
    }

    public void setIntetralNum(String intetralNum) {
        this.intetralNum = intetralNum;
    }

    public String getIsPublish() {
        return isPublish;
    }

    public void setIsPublish(String isPublish) {
        this.isPublish = isPublish;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
