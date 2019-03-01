package com.ylbl.cashpocket.bean;

import java.util.List;

public class GoodsInfo {
    private String id;
    private String redPacketMoney;
    private String content;
    private String memHeadImg;
    private String memName;
    private String linkTitle;
    private String linkAddress;
    private List<String> imgList;
//    private List<RecordsInfo> recLogs;

    public GoodsInfo(String id, String redPacketMoney, String content, String memHeadImg, String memName,
                     String linkTitle, String linkAddress , List<String> imgList/*, List<RecordsInfo> recLogs*/) {
        this.id = id;
        this.redPacketMoney = redPacketMoney;
        this.content = content;
        this.memHeadImg = memHeadImg;
        this.memName = memName;
        this.linkTitle = linkTitle;
        this.imgList = imgList;
//        this.recLogs = recLogs;
        this.linkAddress = linkAddress;
    }

    public GoodsInfo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRedPacketMoney() {
        return redPacketMoney;
    }

    public void setRedPacketMoney(String redPacketMoney) {
        this.redPacketMoney = redPacketMoney;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMemHeadImg() {
        return memHeadImg;
    }

    public void setMemHeadImg(String memHeadImg) {
        this.memHeadImg = memHeadImg;
    }

    public String getMemName() {
        return memName;
    }

    public void setMemName(String memName) {
        this.memName = memName;
    }

    public String getLinkTitle() {
        return linkTitle;
    }

    public void setLinkTitle(String linkTitle) {
        this.linkTitle = linkTitle;
    }

    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }

//    public List<RecordsInfo> getRecLogs() {
//        return recLogs;
//    }
//
//    public void setRecLogs(List<RecordsInfo> recLogs) {
//        this.recLogs = recLogs;
//    }

    public String getLinkAddress() {
        return linkAddress;
    }

    public void setLinkAddress(String linkAddress) {
        this.linkAddress = linkAddress;
    }
}
