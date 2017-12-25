package com.liangliang.bookmanager.bean;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.liangliang.bookmanager.config.CustomJsonDateDeserializer;

import java.util.Date;

/**
 * @Author: YannYao
 * @Description:
 * @Date Created in 16:27 2017/12/13
 */
public class Order {
    private Integer orderId;

    private Integer bookId;

    private Integer borrowerId;

    private Integer status;

    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    private Date createDate;

    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    private Date updateDate;

    private User borrower;

    private Book book;

    private Date readyTime;

    public Order() {
    }

    public Order(Integer orderId, Integer bookId, Integer borrowerId, Integer status, Date createDate, Date updateDate, User borrower, Book book) {
        this.orderId = orderId;
        this.bookId = bookId;
        this.borrowerId = borrowerId;
        this.status = status;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.borrower = borrower;
        this.book = book;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(Integer borrowerId) {
        this.borrowerId = borrowerId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public User getBorrower() {
        return borrower;
    }

    public void setBorrower(User borrower) {
        this.borrower = borrower;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Date getReadyTime() {
        return readyTime;
    }

    public void setReadyDate(Date readyTime) {
        this.readyTime = readyTime;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", bookId=" + bookId +
                ", borrowerId=" + borrowerId +
                ", status=" + status +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", borrower=" + borrower +
                ", book=" + book +
                '}';
    }
}
