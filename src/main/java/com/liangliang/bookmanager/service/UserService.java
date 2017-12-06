package com.liangliang.bookmanager.service;

import com.liangliang.bookmanager.bean.User;

import java.util.List;

public interface UserService {
    /**
     * 获取用户列表
     * @return
     * @throws Exception
     */
    public List<User> getUserList() throws Exception;

    /**
     * 根据id获取用户
     * @param id
     * @return
     * @throws Exception
     */
    public User selectById(Integer id) throws Exception;

    /**
     * 新增一个用户
     * @param user
     * @return
     * @throws Exception
     */
    public boolean add(User user) throws Exception;
    /**
     * 修改一个用户
     * @param user
     * @return
     * @throws Exception
     */
    public boolean modify(User user) throws Exception;

}
