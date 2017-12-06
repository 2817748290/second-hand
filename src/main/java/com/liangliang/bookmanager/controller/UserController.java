package com.liangliang.bookmanager.controller;

import com.liangliang.bookmanager.bean.Message;
import com.liangliang.bookmanager.bean.User;
import com.liangliang.bookmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 获取用户列表
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Message getUserList(){

        List<User> userList = new ArrayList<>();

        try {
            userList = userService.getUserList();
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"获取用户列表失败！",null);
        }
        return new Message(Message.SUCCESS,"获取用户列表成功！",userList);
    }

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public Message getUserById(@PathVariable("userId") Integer id){
        User user = new User();
        try {
            user = userService.selectById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"获取用户成功！",null);
        }
        return new Message(Message.SUCCESS,"获取用户失败！",null);
    }

    /**
     * 新增用户
     * @param user
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public Message add(@RequestBody User user){

        try {
            if(userService.add(user)){
                return new Message(Message.SUCCESS,"新增用户成功！",null);
            }else{
                return new Message(Message.ERROR,"新增用户失败！",null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"新增用户失败！",null);
        }
    }

    /**
     * 修改用户
     * @param user
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Message modify(@RequestBody User user){
        try {
            if(userService.modify(user)){
                return new Message(Message.SUCCESS,"修改用户成功！",null);
            }else{
                return new Message(Message.ERROR,"修改用户失败！",null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"修改用户失败！",null);
        }
    }

}
