package com.liangliang.bookmanager.controller;

import com.liangliang.bookmanager.bean.Message;
import com.liangliang.bookmanager.bean.Right;
import com.liangliang.bookmanager.bean.TableMessage;
import com.liangliang.bookmanager.service.RightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/right")
public class RightController {

    @Autowired
    private RightService rightService;

    @ResponseBody
    @RequestMapping(value = "/addRight", method = RequestMethod.POST)
    public Message addRight(@RequestBody Right right){

        int state = 0;
        Date date = new Date();
        right.setRightDate(date);
        try {
            state = rightService.addRight(right);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"添加申诉信息失败！",state);
        }
        return new Message(Message.SUCCESS,"添加申诉信息成功！",state);
    }

    @ResponseBody
    @RequestMapping(value = "/updateRight", method = RequestMethod.POST)
    public Message updateRight(@RequestBody Right right){

        int state = 0;

        try {
            state = rightService.updateRight(right);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"修改申诉信息失败！",state);
        }
        return new Message(Message.SUCCESS,"修改申诉信息成功！",state);
    }

    @ResponseBody
    @RequestMapping(value = "/deleteRight", method = RequestMethod.POST)
    public Message deleteRight(@RequestParam("rightId") int rightId){

        int state = 0;

        try {
            state = rightService.deleteRight(rightId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"删除申述信息失败！",state);
        }
        return new Message(Message.SUCCESS,"删除申述信息成功！",state);
    }

    @RequestMapping(value = "/getRightInfoById", method = RequestMethod.POST)
    @ResponseBody
    public Message getRightInfoById(@RequestParam("rightId") int rightId){

        Right right = new Right();

        try {
            right = rightService.getRightInfoById(rightId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"获取对应id申述信息失败！",null);
        }
        return new Message(Message.SUCCESS,"获取对应id申述信息成功！！",right);
    }

    @RequestMapping(value = "/getInitRights", method = RequestMethod.POST)
    @ResponseBody
    public Map getInitRights(@RequestBody TableMessage tableMessage) throws Exception {
        return rightService.getInitRights(tableMessage).result();
    }

}
