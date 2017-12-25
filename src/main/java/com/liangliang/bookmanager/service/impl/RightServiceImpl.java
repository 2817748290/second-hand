package com.liangliang.bookmanager.service.impl;

import com.liangliang.bookmanager.bean.Order;
import com.liangliang.bookmanager.bean.Right;
import com.liangliang.bookmanager.bean.TableMessage;
import com.liangliang.bookmanager.bean.User;
import com.liangliang.bookmanager.mapper.OrderMapper;
import com.liangliang.bookmanager.mapper.RightMapper;
import com.liangliang.bookmanager.mapper.UserMapper;
import com.liangliang.bookmanager.service.RightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RightServiceImpl implements RightService {

    @Autowired
    private RightMapper rightMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Integer addRight(Right right){

        int state = 0;
        try {
            state = rightMapper.addRight(right);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

        return state;
    }

    @Override
    public TableMessage getInitRights(TableMessage tableMessage){

        List<Right> rightList = new ArrayList<>();
        try {
            rightList = rightMapper.getInitRights(tableMessage);
            if(tableMessage.getSearch()!=null){
                if(tableMessage.getSearch().equals("")){
                    rightList = rightMapper.getInitRights(tableMessage);
                    for (Right right : rightList) {
                        Order order = orderMapper.getOrderById(right.getOrderId());
                        User user = userMapper.getUserById(order.getBorrowerId());
                        order.setBorrower(user);
                        right.setOrder(order);
                    }
                    tableMessage.setRows(rightList);
                    tableMessage.setTotal(rightMapper.getInitRightsCount(tableMessage));
                }else {
                    tableMessage.setSearch("%"+tableMessage.getSearch()+"%");
                    List<Right> searchBookList = rightMapper.getInitRights(tableMessage);
                    tableMessage.setRows(searchBookList);
                    for (Right right : searchBookList) {
                        Order order = orderMapper.getOrderById(right.getOrderId());
                        User user = userMapper.getUserById(order.getBorrowerId());
                        order.setBorrower(user);
                        right.setOrder(order);
                    }
                    tableMessage.setTotal(rightMapper.getInitRightsCount(tableMessage));
                }

            }else {
                tableMessage.setRows(rightList);
                tableMessage.setTotal(rightMapper.getInitRightsCount(tableMessage));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return tableMessage;
    }

    @Override
    public Integer deleteRight(int rightId){

        int state = 0;
        try {
            state = rightMapper.deleteRight(rightId);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

        return state;
    }

    @Override
    public Integer updateRight(Right right){

        int state = 0;
        try {
            state = rightMapper.updateRight(right);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

        return state;
    }

    @Override
    public Right getRightInfoById(int rightId){

        Right right = new Right();

        try {
            right = rightMapper.getRightInfoById(rightId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return right;
    }
}
