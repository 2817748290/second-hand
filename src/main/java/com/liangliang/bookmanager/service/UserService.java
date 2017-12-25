package com.liangliang.bookmanager.service;

import com.liangliang.bookmanager.bean.TableMessage;
import com.liangliang.bookmanager.bean.TableMessageForUser;
import com.liangliang.bookmanager.bean.User;
import sun.security.util.Password;

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

    /**
     * 根据TableMessage获取查询到的用户
     * @param tableMessage
     * @return
     * @throws Exception
     */
    public TableMessage searchUser(TableMessageForUser tableMessage) throws Exception;
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

    /**
     * 用户登录验证
     */
    public boolean validate(User user);

    public Integer userLogin(User user) throws Exception;

}
