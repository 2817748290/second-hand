package com.liangliang.bookmanager.service;

import com.liangliang.bookmanager.bean.Admin;

public interface AdminService {

    public Integer adminLogin(Admin admin) throws Exception;

    public void adminLoginOut() throws Exception;
}
