package com.liangliang.bookmanager.bean;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.liangliang.bookmanager.config.CustomJsonDateDeserializer;

import java.util.Date;

public class Book {
    private Integer bookId;

    private String bookName;

    private String author;

    private String imageUrl;

    private String location;

    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    private Date borrowDate;

    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    private Date returnDate;

    private Integer typeId;

    private String isbn;

    private Integer state;

    private Integer userId;

    private User user;

    private Type type;

    public Book(Integer bookId, String bookName, String author, String imageUrl, String location, Date borrowDate, Date returnDate, Integer typeId, String isbn, Integer state) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.author = author;
        this.imageUrl = imageUrl;
        this.location = location;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.typeId = typeId;
        this.isbn = isbn;
        this.state = state;
    }

    public Book() {
        super();
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName == null ? null : bookName.trim();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn == null ? null : isbn.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}