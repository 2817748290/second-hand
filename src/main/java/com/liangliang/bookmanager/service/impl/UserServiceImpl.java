package com.liangliang.bookmanager.service.impl;

import com.liangliang.bookmanager.bean.User;
import com.liangliang.bookmanager.mapper.UserMapper;
import com.liangliang.bookmanager.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public List<User> getUserList() {

        List<User> userList = new ArrayList<>();
        try {
            userList = userMapper.getUserList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return userList;
    }

    @Override
    public User selectById(Integer id) throws Exception {
        User user = userMapper.selectById(id);
        return user;
    }

    @Override
    public boolean add(User user) throws Exception {
        return userMapper.insert(user) == 1;
    }

    @Override
    public boolean modify(User user) throws Exception {
        return userMapper.update(user) == 1;
    }


}
