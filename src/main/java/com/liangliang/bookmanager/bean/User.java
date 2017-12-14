package com.liangliang.bookmanager.bean;

public class User {
    private Integer userId;

    private String username;

    private String password;

    private String nickname;

    private Integer points;

    private Integer group;

    private Integer userState;

    private String email;

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", points=" + points +
                ", group=" + group +
                ", userState=" + userState +
                ", email='" + email + '\'' +
                '}';
    }

    public User() {
    }

    public User(Integer userId, String username, String password, String nickname, Integer points, Integer group, Integer userState, String email) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.points = points;
        this.group = group;
        this.userState = userState;
        this.email = email;
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
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    public Integer getUserState() {
        return userState;
    }

    public void setUserState(Integer userState) {
        this.userState = userState;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}