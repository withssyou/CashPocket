package com.ylbl.cashpocket.bean;

import java.util.List;

public class SendInfo {
    private String date;
    private List<SendDetailsInfo> data;

    public SendInfo() {
    }

    public SendInfo(String date, List<SendDetailsInfo> data) {
        this.date = date;
        this.data = data;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<SendDetailsInfo> getData() {
        return data;
    }

    public void setData(List<SendDetailsInfo> data) {
        this.data = data;
    }
}
