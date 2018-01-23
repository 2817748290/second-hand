package com.liangliang.bookmanager.service.impl;

import com.liangliang.bookmanager.bean.Admin;
import com.liangliang.bookmanager.mapper.AdminMapper;
import com.liangliang.bookmanager.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Integer adminLogin(Admin admin) throws Exception {

        int adminId = 0;

        Admin admin1 = new Admin();
        try {
            admin1 = adminMapper.adminLogin(admin1);
            if(admin1 == null) {
                return -1;
            }else{
                adminId = admin1.getAdminId();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

        return adminId;
    }

}
