package com.liangliang.bookmanager.bean;

public class User {
    private Integer userId;

    private String username;

    private String password;

    private String nickname;

    public User(Integer userId, String username, String password, String nickname) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }

    public User() {
        super();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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