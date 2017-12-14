package com.liangliang.bookmanager.mapper;

import com.liangliang.bookmanager.bean.Order;
import com.liangliang.bookmanager.bean.TableMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {

    public List<Order> getOrderList() throws Exception;

    public Integer addOrder(Order order) throws Exception;

    public Integer updateOrder(Order order) throws Exception;

    public Integer deleteOrder(int orderId) throws Exception;

    public Order getOrderById(int orderId) throws Exception;

    public List<Order> searchOrder(TableMessage tableMessage) throws Exception;

    public Integer searchOrderCount(TableMessage tableMessage) throws Exception;

    public List<Order> getOrderAndUserList(TableMessage tableMessage) throws Exception;

    public Integer orderCount(TableMessage tableMessage) throws Exception;

}
