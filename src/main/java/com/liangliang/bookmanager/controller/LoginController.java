package com.liangliang.bookmanager.controller;

import com.liangliang.bookmanager.bean.Admin;
import com.liangliang.bookmanager.bean.Message;
import com.liangliang.bookmanager.bean.User;
import com.liangliang.bookmanager.service.AdminService;
import com.liangliang.bookmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by YannYao on 2017/12/10.
 */
@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/validate",method = RequestMethod.POST)
    @ResponseBody
    public Message validate(@RequestBody User user){
        try{
            if(userService.validate(user)){
                return new Message(Message.SUCCESS,"登录成功！",null);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new Message(Message.ERROR,"登录失败！",null);
        }
        return new Message(Message.ERROR,"登录失败！",null);
    }

    //user
    @RequestMapping(value = "/userLogin",method = RequestMethod.POST)
    @ResponseBody
    public Message userLogin(HttpServletRequest request, @RequestBody User user){
        int userId = 0;
        try {
            userId = userService.userLogin(user);
            if(userId == -1){
                return new Message(Message.ERROR,"用户登录失败！",null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"用户登录失败！",null);
        }
        request.getSession().setAttribute("userId", userId);
        return new Message(Message.SUCCESS,"用户登录成功！",userId);
    }

    @RequestMapping(value = "/userLoginOut",method = RequestMethod.POST)
    @ResponseBody
    public Message userLoginOut(HttpServletRequest request, @RequestBody User user) {

        request.getSession().removeAttribute("userId");

        return new Message(Message.SUCCESS,"用户退出成功！",null);
    }

    //admin
    @RequestMapping(value = "/adminLogin",method = RequestMethod.POST)
    @ResponseBody
    public Message adminLogin(HttpServletRequest request, @RequestBody Admin admin){
        int adminId = 0;
        try {
            adminId = adminService.adminLogin(admin);
            if(adminId == -1){
                return new Message(Message.ERROR,"管理员登录失败！",null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"管理员登录失败！",null);
        }
        request.getSession().setAttribute("userId", adminId);
        return new Message(Message.SUCCESS,"管理员登录成功！",adminId);
    }

    @RequestMapping(value = "/adminLoginOut",method = RequestMethod.POST)
    @ResponseBody
    public Message adminLoginOut(HttpServletRequest request, @RequestBody Admin admin) {

        request.getSession().removeAttribute("adminId");

        return new Message(Message.SUCCESS,"管理员退出成功！",null);
    }
}
