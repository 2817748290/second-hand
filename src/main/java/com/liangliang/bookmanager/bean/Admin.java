package com.liangliang.bookmanager.bean;

public class Admin {
    private Integer adminId;

    private String username;

    private String password;

    private String nickname;

    public Admin(Integer adminId, String username, String password, String nickname) {
        this.adminId = adminId;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }

    public Admin() {
        super();
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }
}