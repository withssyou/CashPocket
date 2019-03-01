package com.ylbl.cashpocket.bean;

public class Order{
    private String order_id;
    private String gathering_name;
    private String qrcode;
    private String qrcode_url;
    private String qrcode_url2;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getGathering_name() {
        return gathering_name;
    }

    public void setGathering_name(String gathering_name) {
        this.gathering_name = gathering_name;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getQrcode_url() {
        return qrcode_url;
    }

    public void setQrcode_url(String qrcode_url) {
        this.qrcode_url = qrcode_url;
    }

    public String getQrcode_url2() {
        return qrcode_url2;
    }

    public void setQrcode_url2(String qrcode_url2) {
        this.qrcode_url2 = qrcode_url2;
    }
}
