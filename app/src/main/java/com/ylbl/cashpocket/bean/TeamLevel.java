package com.ylbl.cashpocket.bean;

import java.io.Serializable;

public class TeamLevel implements Serializable{
    private int level;
    private int num;
    private double money;

    public TeamLevel() {
    }

    public TeamLevel(int level, int num, double money) {
        this.level = level;
        this.num = num;
        this.money = money;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
