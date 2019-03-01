package com.ylbl.cashpocket.bean;

public class VersionInfo {
    private String version;
    private String version_android;
    private String ios_jump_url;
    private String android_jump_url;
    private String note;

    public VersionInfo(String version, String version_android, String ios_jump_url, String android_jump_url, String note) {
        this.version = version;
        this.version_android = version_android;
        this.ios_jump_url = ios_jump_url;
        this.android_jump_url = android_jump_url;
        this.note = note;
    }

    public VersionInfo() {
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion_android() {
        return version_android;
    }

    public void setVersion_android(String version_android) {
        this.version_android = version_android;
    }

    public String getIos_jump_url() {
        return ios_jump_url;
    }

    public void setIos_jump_url(String ios_jump_url) {
        this.ios_jump_url = ios_jump_url;
    }

    public String getAndroid_jump_url() {
        return android_jump_url;
    }

    public void setAndroid_jump_url(String android_jump_url) {
        this.android_jump_url = android_jump_url;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
