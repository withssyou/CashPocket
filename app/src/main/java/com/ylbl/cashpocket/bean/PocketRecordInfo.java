package com.ylbl.cashpocket.bean;

public class PocketRecordInfo {
    private long redPacketId;
    private long adverId;
    private String imagePath;
    private String linkTitle;
    private String occTime;
    private String state;
    private double price;

    public PocketRecordInfo() {
    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }



    public String getLinkTitle() {
        return linkTitle;
    }

    public void setLinkTitle(String linkTitle) {
        this.linkTitle = linkTitle;
    }

    public String getOccTime() {
        return occTime;
    }

    public void setOccTime(String occTime) {
        this.occTime = occTime;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public long getRedPacketId() {
        return redPacketId;
    }

    public void setRedPacketId(long redPacketId) {
        this.redPacketId = redPacketId;
    }

    public long getAdverId() {
        return adverId;
    }

    public void setAdverId(long adverId) {
        this.adverId = adverId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
