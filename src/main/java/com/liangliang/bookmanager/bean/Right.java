package com.liangliang.bookmanager.bean;

import java.util.Date;

public class Right {
    private Integer rightId;

    private String rightTitle;

    private Integer rightTypeId;

    private Date rightDate;

    private Integer rightStateId;

    private Integer orderId;

    private String rightContent;

    private Order order;

    public Right(Integer rightId, String rightTitle, Integer rightTypeId, Date rightDate, Integer rightStateId, Integer orderId, String rightContent) {
        this.rightId = rightId;
        this.rightTitle = rightTitle;
        this.rightTypeId = rightTypeId;
        this.rightDate = rightDate;
        this.rightStateId = rightStateId;
        this.orderId = orderId;
        this.rightContent = rightContent;
    }

    public Right() {
        super();
    }

    public Integer getRightId() {
        return rightId;
    }

    public void setRightId(Integer rightId) {
        this.rightId = rightId;
    }

    public String getRightTitle() {
        return rightTitle;
    }

    public void setRightTitle(String rightTitle) {
        this.rightTitle = rightTitle == null ? null : rightTitle.trim();
    }

    public Integer getRightTypeId() {
        return rightTypeId;
    }

    public void setRightTypeId(Integer rightTypeId) {
        this.rightTypeId = rightTypeId;
    }

    public Date getRightDate() {
        return rightDate;
    }

    public void setRightDate(Date rightDate) {
        this.rightDate = rightDate;
    }

    public Integer getRightStateId() {
        return rightStateId;
    }

    public void setRightStateId(Integer rightStateId) {
        this.rightStateId = rightStateId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getRightContent() {
        return rightContent;
    }

    public void setRightContent(String rightContent) {
        this.rightContent = rightContent == null ? null : rightContent.trim();
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}