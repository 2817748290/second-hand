package com.liangliang.bookmanager.mapper;

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
    public User selectById(Integer id) throws Exception;

    /**
     * 新增一个用户
     * @param user
     * @return
     */
    public int insert(User user) throws Exception;

    /**
     * 修改一个用户
     */
    public int update(User user) throws Exception;

}
