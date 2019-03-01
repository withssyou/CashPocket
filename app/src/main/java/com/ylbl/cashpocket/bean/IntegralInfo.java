package com.ylbl.cashpocket.bean;

public class IntegralInfo {
    private String id;
    private String logType;
    private String integralNum;
    private String createTime;

    public IntegralInfo() {
    }

    public IntegralInfo(String id, String logType, String integralNum, String createTime) {
        this.id = id;
        this.logType = logType;
        this.integralNum = integralNum;
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getIntegralNum() {
        return integralNum;
    }

    public void setIntegralNum(String integralNum) {
        this.integralNum = integralNum;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
