package com.ylbl.cashpocket.bean;

public class SendDetailsInfo {
    private String time;
    private String isSend;
    private String adIcon;
    private String adTitle;
    private String sendNum;
    private String ReceivedNum;

    public SendDetailsInfo(String time, String isSend, String adIcon, String adTitle, String sendNum, String receivedNum) {
        this.time = time;
        this.isSend = isSend;
        this.adIcon = adIcon;
        this.adTitle = adTitle;
        this.sendNum = sendNum;
        ReceivedNum = receivedNum;
    }

    public SendDetailsInfo() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIsSend() {
        return isSend;
    }

    public void setIsSend(String isSend) {
        this.isSend = isSend;
    }

    public String getAdIcon() {
        return adIcon;
    }

    public void setAdIcon(String adIcon) {
        this.adIcon = adIcon;
    }

    public String getAdTitle() {
        return adTitle;
    }

    public void setAdTitle(String adTitle) {
        this.adTitle = adTitle;
    }

    public String getSendNum() {
        return sendNum;
    }

    public void setSendNum(String sendNum) {
        this.sendNum = sendNum;
    }

    public String getReceivedNum() {
        return ReceivedNum;
    }

    public void setReceivedNum(String receivedNum) {
        ReceivedNum = receivedNum;
    }
}
