package com.ylbl.cashpocket.bean;

import java.util.List;

public class MemberConfigInfo {
    private MemberInfo memberInfo;
    private ConfigInfo sysconfig;

    public MemberConfigInfo() {
    }


    public MemberInfo getMemberInfo() {
        return memberInfo;
    }

    public void setMemberInfo(MemberInfo memberInfo) {
        this.memberInfo = memberInfo;
    }

    public ConfigInfo getSysconfig() {
        return sysconfig;
    }

    public void setSysconfig(ConfigInfo sysconfig) {
        this.sysconfig = sysconfig;
    }
}
