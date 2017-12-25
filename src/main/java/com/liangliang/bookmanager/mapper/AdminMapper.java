package com.liangliang.bookmanager.mapper;

import com.liangliang.bookmanager.bean.Admin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMapper {

    /**
     *用户登录
     */
    public Admin adminLogin(Admin admin) throws Exception;

    /**
     *用户退出
     */
    public int adminLoginOut() throws Exception;

}
