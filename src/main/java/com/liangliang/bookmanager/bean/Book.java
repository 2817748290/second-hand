package com.liangliang.bookmanager.bean;

import java.util.Date;

public class Book {
    private Integer bookId;

    private String bookName;

    private String author;

    private String imageUrl;

    private String location;

    private Date borrowDate;

    private Integer type;

    private String isbn;

    private Integer state;

    public Book(Integer bookId, String bookName, String author, String imageUrl, String location, Date borrowDate, Integer type, String isbn, Integer state) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.author = author;
        this.imageUrl = imageUrl;
        this.location = location;
        this.borrowDate = borrowDate;
        this.type = type;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
}