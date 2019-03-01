package com.ylbl.cashpocket.bean;

/**
 *
 */
public class DepositRecordInfo {
    private long id;
    private int logType;
    private double cashNum;
    private double afterCashNum;
    private String createTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getLogType() {
        return logType;
    }

    public void setLogType(int logType) {
        this.logType = logType;
    }

    public double getCashNum() {
        return cashNum;
    }

    public void setCashNum(double cashNum) {
        this.cashNum = cashNum;
    }

    public double getAfterCashNum() {
        return afterCashNum;
    }

    public void setAfterCashNum(double afterCashNum) {
        this.afterCashNum = afterCashNum;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
