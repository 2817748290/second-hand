package com.liangliang.bookmanager.controller;

import com.liangliang.bookmanager.bean.Message;
import com.liangliang.bookmanager.bean.User;
import com.liangliang.bookmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by YannYao on 2017/12/10.
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private UserService userService;
    @RequestMapping(value = "/validate",method = RequestMethod.POST)
    public Message validate(@RequestBody User user){
        System.out.println(user.toString());
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
}
