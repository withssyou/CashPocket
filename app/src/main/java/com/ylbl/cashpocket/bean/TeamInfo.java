package com.ylbl.cashpocket.bean;

import java.util.List;

public class TeamInfo {
    private int parentId;
    private String parentName;
    private String parentMobile;
    private TeamMoney team;
    private List<Teamer> children;

    public TeamInfo() {
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentMobile() {
        return parentMobile;
    }

    public void setParentMobile(String parentMobile) {
        this.parentMobile = parentMobile;
    }

    public TeamMoney getTeam() {
        return team;
    }

    public void setTeam(TeamMoney team) {
        this.team = team;
    }

    public List<Teamer> getChildren() {
        return children;
    }

    public void setChildren(List<Teamer> children) {
        this.children = children;
    }
}
