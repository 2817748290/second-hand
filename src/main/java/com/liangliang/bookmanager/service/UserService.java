package com.liangliang.bookmanager.service;

import com.liangliang.bookmanager.bean.TableMessage;
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
    public User getUserById(Integer id) throws Exception;

    public TableMessage searchUser(TableMessage tableMessage, int group) throws Exception;
    /**
     * 新增一个用户
     * @param user
     * @return
     * @throws Exception
     */
    public boolean addUser(User user);
    /**
     * 修改一个用户
     * @param user
     * @return
     * @throws Exception
     */
    public boolean updateUser(User user);

    /**
     * 删除一个用户
     * @param id
     * @return
     * @throws Exception
     */
    public boolean deleteUser(Integer id);

}
