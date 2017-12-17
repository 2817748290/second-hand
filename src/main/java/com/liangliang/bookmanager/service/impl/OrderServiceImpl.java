package com.liangliang.bookmanager.service.impl;

import com.liangliang.bookmanager.bean.*;
import com.liangliang.bookmanager.mapper.BookMapper;
import com.liangliang.bookmanager.mapper.OrderMapper;
import com.liangliang.bookmanager.mapper.UserMapper;
import com.liangliang.bookmanager.service.BookService;
import com.liangliang.bookmanager.service.OrderService;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BookMapper bookMapper;
    @Override
    public List<Order> getOrderList() throws Exception {
        List<Order> orderList = new ArrayList<>();
        try {
            orderList = orderMapper.getOrderList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return orderList;
    }

    @Override
    public boolean addOrder(Order order) {
        boolean state = false;
        try {
            state = orderMapper.addOrder(order) == 1 ? true : false;
        }catch (Exception e){
            e.printStackTrace();
        }
        return state;
    }

    @Override
    public boolean updateOrder(Order order) {
        boolean state = false;
        try {
            state = orderMapper.updateOrder(order) == 1 ? true : false;
        }catch (Exception e){
            e.printStackTrace();
        }
        return state;
    }

    @Override
    public boolean deleteOrder(int orderId) {
        boolean state = false;
        try {
            state = orderMapper.deleteOrder(orderId) == 1 ? true : false;
        }catch (Exception e){
            e.printStackTrace();
        }
        return state;
    }

    @Override
    public Order getOrderById(int orderId) throws Exception {
        Order order = new Order();

        try {
            order = orderMapper.getOrderById(orderId);
            order.setBorrower(userMapper.getUserById(order.getBorrowerId()));
            order.setBook(bookMapper.getBookInfoById(order.getBookId()));
        }catch (Exception e){
            e.printStackTrace();
        }
        return order;
    }

    @Override
    public TableMessage searchOrder(TableMessageForOrder tableMessage) throws Exception {
        List<Order> orderList = new ArrayList<>();
        //1.判断你昵称和用户组搜索条件是否为空,若为空则返回所有数据
        try {
            tableMessage.setSearch("%"+tableMessage.getSearch()+"%");
            tableMessage.setUsernameValue("%"+tableMessage.getUsernameValue()+"%");
            tableMessage.setBookNameValue("%"+tableMessage.getBookNameValue()+"%");
            orderList = orderMapper.searchOrder(tableMessage);
            for (Order order: orderList){
                Integer userId = order.getBorrowerId();
                Integer bookId = order.getBookId();
                order.setBorrower(userMapper.getUserById(userId));
                order.setBook(bookMapper.getBookInfoById(bookId));
                System.out.println("日期:"+order.getCreateDate());
            }
            tableMessage.setRows(orderList);
            Integer total = orderMapper.searchOrderCount(tableMessage);
            tableMessage.setTotal(total);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return tableMessage;
    }
}
