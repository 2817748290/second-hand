package com.liangliang.bookmanager.bean;

import org.springframework.web.multipart.MultipartFile;

public class User {
    private Integer userId;

    private String username;

    private String password;

    private String nickname;

    /**
     * 传给数据库的头像图片名
     */
    private String avatarImage;

    private Integer points;

    private Integer group;

    private Integer userState;

    private String email;

    /**
     * 传输给前台的头像图片名
     */
    private String imageName;

    private MultipartFile avatarImageFile;

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", avatarImage='" + avatarImage + '\'' +
                ", points=" + points +
                ", group=" + group +
                ", userState=" + userState +
                ", email='" + email + '\'' +
                ", imageName='" + imageName + '\'' +
                ", avatarImageFile=" + avatarImageFile +
                '}';
    }

    public User() {
    }

    public User(Integer userId, String username, String password, String nickname, String avatarImage, Integer points, Integer group, Integer userState, String email, String imageName, MultipartFile avatarImageFile) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.avatarImage = avatarImage;
        this.points = points;
        this.group = group;
        this.userState = userState;
        this.email = email;
        this.imageName = imageName;
        this.avatarImageFile = avatarImageFile;
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

    public String getAvatarImage() {
        return avatarImage;
    }

    public void setAvatarImage(String avatarImage) {
        this.avatarImage = avatarImage;
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

    public MultipartFile getAvatarImageFile() {
        return avatarImageFile;
    }

    public void setAvatarImageFile(MultipartFile avatarImageFile) {
        this.avatarImageFile = avatarImageFile;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}