package com.liangliang.bookmanager.bean;

public class Group {
    private Integer userGroupId;

    private String userGroupInfo;

    public Group(Integer userGroupId, String userGroupInfo) {
        this.userGroupId = userGroupId;
        this.userGroupInfo = userGroupInfo;
    }

    public Group() {
        super();
    }

    public Integer getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(Integer userGroupId) {
        this.userGroupId = userGroupId;
    }

    public String getUserGroupInfo() {
        return userGroupInfo;
    }

    public void setUserGroupInfo(String userGroupInfo) {
        this.userGroupInfo = userGroupInfo == null ? null : userGroupInfo.trim();
    }
}