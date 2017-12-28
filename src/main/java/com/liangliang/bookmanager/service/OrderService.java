package com.liangliang.bookmanager.service;

import com.liangliang.bookmanager.bean.Order;
import com.liangliang.bookmanager.bean.TableMessage;
import com.liangliang.bookmanager.bean.TableMessageForOrder;

import java.util.List;

public interface OrderService {

    public List<Order> getOrderList() throws Exception;

    public boolean addOrder(Order order) throws Exception;

    public boolean updateOrder(Order order) throws Exception;

    public boolean deleteOrder(int orderId) throws Exception;

    public Order getOrderById(int orderId) throws Exception;

    public TableMessage searchOrder(TableMessageForOrder tableMessage) throws Exception;

    public List<Order> getOrderByMore(int bookId, int status) throws Exception;
}
