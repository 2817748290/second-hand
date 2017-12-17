package com.liangliang.bookmanager.controller;

import com.liangliang.bookmanager.bean.Message;
import com.liangliang.bookmanager.bean.TableMessageForUser;
import com.liangliang.bookmanager.bean.User;
import com.liangliang.bookmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.annotation.MultipartConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@MultipartConfig
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 获取用户列表
     * @return
     */
    @RequestMapping(value = "/getUserList", method = RequestMethod.POST)
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
    @RequestMapping(value = "/getUserById", method = RequestMethod.POST)
    @ResponseBody
    public Message getUserById(@RequestParam("userId") Integer id){
        User user = new User();
        try {
            user = userService.getUserById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"获取用户失败！", null);
        }
        return new Message(Message.SUCCESS,"获取用户成功！",user);
    }

    /**
     * 根据
     * @param tableMessage
     * @return
     */
    @RequestMapping(value = "/getSearchUser", method = RequestMethod.POST)
    @ResponseBody
    public Map getSearchUser(@RequestBody TableMessageForUser tableMessage) throws Exception{
        System.out.println(tableMessage);
        return userService.searchUser(tableMessage).result();
    }
    /**
     * 新增用户
     * @param request
     * @return
     */
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    @ResponseBody
    public Message addUser(MultipartHttpServletRequest request){
        try {
            MultipartFile avatarImageFile = request.getFile("avatarImage");
            String username = request.getParameter("username");
            String nickname = request.getParameter("nickname");
            String password = request.getParameter("password");
            Integer group = Integer.parseInt(request.getParameter("group"));
            Integer points = Integer.parseInt(request.getParameter("points"));
            Integer userState = Integer.parseInt(request.getParameter("userState"));
            String email = request.getParameter("email");
            String imageName = null;
            if(avatarImageFile != null && avatarImageFile.getSize() > 0){
                imageName = avatarImageFile.getOriginalFilename();
            }
            User user = new User(null,username,password,nickname,null,points,group,userState,email,imageName,avatarImageFile);
            if(userService.addUser(user)){
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
     * @param request
     * @return
     */
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    @ResponseBody
    public Message updateUser(MultipartHttpServletRequest request){
        try {
            MultipartFile avatarImageFile = request.getFile("avatarImage");
            String username = request.getParameter("username");
            String nickname = request.getParameter("nickname");
            String password = request.getParameter("password");
            Integer group = Integer.parseInt(request.getParameter("group"));
            Integer points = Integer.parseInt(request.getParameter("points"));
            Integer userId = Integer.parseInt(request.getParameter("userId"));

            Integer userState = Integer.parseInt(request.getParameter("userState"));
            String email = request.getParameter("email");
            String imageName = null;
            if(avatarImageFile != null && avatarImageFile.getSize() > 0){
                imageName = avatarImageFile.getOriginalFilename();
            }
            User user = new User(userId,username,password,nickname,null,points,group,userState,email,imageName,avatarImageFile);
            if(userService.updateUser(user)){
                return new Message(Message.SUCCESS,"修改用户成功！",null);
            }else{
                return new Message(Message.ERROR,"修改用户失败！",null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"修改用户失败！",null);
        }
    }
    /**
     * 删除用户
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    @ResponseBody
    public Message deleteUser(@RequestParam(value = "userId") Integer id){
                try {
            if(userService.deleteUser(id)){
                return new Message(Message.SUCCESS,"删除用户成功！",null);
            }else{
                return new Message(Message.ERROR,"删除用户失败！",null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"删除用户失败！",null);
        }
    }
}
