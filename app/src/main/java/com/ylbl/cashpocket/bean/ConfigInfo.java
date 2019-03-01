package com.ylbl.cashpocket.bean;

import java.io.Serializable;

/**
 * 配置信息
 */
public class ConfigInfo implements Serializable{
    private String iosVersion;
    private String iosRemark;
    private String iosLink;
    private String androidVersion;
    private String androidRemark;
    private String androidLink;
    private String withdrawalSwitch;
    private String withdrawalType;
    private String withdrawalRate;
    private String advertiserDays;
    private String advertiserRate;
    private String exposurePrice;
    private String exposureIntegral;
    private String commentSwitch;
    private String commentWorkday;
    private String androidAutoUpdate;

    public ConfigInfo() {
    }

    public ConfigInfo(String iosVersion, String iosRemark, String iosLink, String androidVersion, String androidRemark,
                      String androidLink, String withdrawalSwitch, String withdrawalType, String withdrawalRate,
                      String advertiserDays, String advertiserRate, String exposurePrice, String exposureIntegral,
                      String commentSwitch, String commentWorkday, String androidAutoUpdate) {
        this.iosVersion = iosVersion;
        this.iosRemark = iosRemark;
        this.iosLink = iosLink;
        this.androidVersion = androidVersion;
        this.androidRemark = androidRemark;
        this.androidLink = androidLink;
        this.withdrawalSwitch = withdrawalSwitch;
        this.withdrawalType = withdrawalType;
        this.withdrawalRate = withdrawalRate;
        this.advertiserDays = advertiserDays;
        this.advertiserRate = advertiserRate;
        this.exposurePrice = exposurePrice;
        this.exposureIntegral = exposureIntegral;
        this.commentSwitch = commentSwitch;
        this.commentWorkday = commentWorkday;
        this.androidAutoUpdate = androidAutoUpdate;
    }

    public String getIosVersion() {
        return iosVersion;
    }

    public void setIosVersion(String iosVersion) {
        this.iosVersion = iosVersion;
    }

    public String getIosRemark() {
        return iosRemark;
    }

    public void setIosRemark(String iosRemark) {
        this.iosRemark = iosRemark;
    }

    public String getIosLink() {
        return iosLink;
    }

    public void setIosLink(String iosLink) {
        this.iosLink = iosLink;
    }

    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }

    public String getAndroidRemark() {
        return androidRemark;
    }

    public void setAndroidRemark(String androidRemark) {
        this.androidRemark = androidRemark;
    }

    public String getAndroidLink() {
        return androidLink;
    }

    public void setAndroidLink(String androidLink) {
        this.androidLink = androidLink;
    }

    public String getWithdrawalSwitch() {
        return withdrawalSwitch;
    }

    public void setWithdrawalSwitch(String withdrawalSwitch) {
        this.withdrawalSwitch = withdrawalSwitch;
    }

    public String getAndroidAutoUpdate() {
        return androidAutoUpdate;
    }

    public void setAndroidAutoUpdate(String androidAutoUpdate) {
        this.androidAutoUpdate = androidAutoUpdate;
    }

    public String getWithdrawalType() {
        return withdrawalType;
    }

    public void setWithdrawalType(String withdrawalType) {
        this.withdrawalType = withdrawalType;
    }

    public String getWithdrawalRate() {
        return withdrawalRate;
    }

    public void setWithdrawalRate(String withdrawalRate) {
        this.withdrawalRate = withdrawalRate;
    }

    public String getAdvertiserDays() {
        return advertiserDays;
    }

    public void setAdvertiserDays(String advertiserDays) {
        this.advertiserDays = advertiserDays;
    }

    public String getAdvertiserRate() {
        return advertiserRate;
    }

    public void setAdvertiserRate(String advertiserRate) {
        this.advertiserRate = advertiserRate;
    }

    public String getExposurePrice() {
        return exposurePrice;
    }

    public void setExposurePrice(String exposurePrice) {
        this.exposurePrice = exposurePrice;
    }

    public String getExposureIntegral() {
        return exposureIntegral;
    }

    public void setExposureIntegral(String exposureIntegral) {
        this.exposureIntegral = exposureIntegral;
    }

    public String getCommentSwitch() {
        return commentSwitch;
    }

    public void setCommentSwitch(String commentSwitch) {
        this.commentSwitch = commentSwitch;
    }

    public String getCommentWorkday() {
        return commentWorkday;
    }

    public void setCommentWorkday(String commentWorkday) {
        this.commentWorkday = commentWorkday;
    }
}
