package com.liangliang.bookmanager.mapper;

import com.liangliang.bookmanager.bean.TableMessage;
import com.liangliang.bookmanager.bean.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    /**
     * 查询所有用户列表
     * @return用户列表
     * @throws Exception
     */
    public List<User> getUserList() throws Exception;

    /**
     * 根据主键获取用户
     * @param id
     * @return
     * @throws Exception
     */
    public User getUserById(Integer id) throws Exception;

    /**
     * 根据TableMessage和用户组别查询用户
     * @param tableMessage
     * @param group
     * @return
     */
    public List<User> searchUser(TableMessage tableMessage, int group);
    /**
     * 新增一个用户
     * @param user
     * @return
     */
    public int addUser(User user) throws Exception;

    /**
     * 修改一个用户
     */
    public int updateUser(User user) throws Exception;

    /**
     * 修改一个用户
     */
    public int deleteUser(Integer id) throws Exception;
}
