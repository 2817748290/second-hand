package com.liangliang.bookmanager.service.impl;

import com.liangliang.bookmanager.bean.TableMessage;
import com.liangliang.bookmanager.bean.User;
import com.liangliang.bookmanager.mapper.UserMapper;
import com.liangliang.bookmanager.service.UserService;
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
    public User getUserById(Integer id) {
        User user = new User();

        try {
            user = userMapper.getUserById(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public TableMessage searchUser(TableMessage tableMessage, int group) throws Exception {
        List<User> userList = new ArrayList<>();
        //1.判断你昵称和用户组搜索条件是否为空,若为空则返回所有数据
        try {
            userList = userMapper.searchUser(tableMessage,group);
            System.out.println(userList);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return tableMessage;
    }

    @Override
    public boolean addUser(User user){
        boolean state = false;
        try {
            state = userMapper.addUser(user) == 1 ? true : false;
        }catch (Exception e){
            e.printStackTrace();
        }
        return state;
    }

    @Override
    public boolean updateUser(User user){
        boolean state = false;
        try {
            state = userMapper.updateUser(user) == 1 ? true : false;
        }catch (Exception e){
            e.printStackTrace();
        }
        return state;
    }

    @Override
    public boolean deleteUser(Integer id) {
        boolean state = false;
        try {
            state = userMapper.deleteUser(id) == 1 ? true : false;
        }catch (Exception e){
            e.printStackTrace();
        }
        return state;
    }


}
