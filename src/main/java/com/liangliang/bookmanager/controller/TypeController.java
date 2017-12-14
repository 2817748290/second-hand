package com.liangliang.bookmanager.controller;


import com.liangliang.bookmanager.bean.Message;
import com.liangliang.bookmanager.bean.Type;
import com.liangliang.bookmanager.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/type")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @RequestMapping(value = "/getTypeList", method = RequestMethod.POST)
    @ResponseBody
    public Message getTypeList(){

        List<Type> typeList = new ArrayList<>();

        try {
            typeList = typeService.getTypeList();
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"获取失败！",null);
        }
        return new Message(Message.SUCCESS,"获取成功！",typeList);
    }

    @RequestMapping(value = "/addType", method = RequestMethod.POST)
    @ResponseBody
    public Message addType(@RequestParam("typeName") String typeName){
        Type type=new Type();
        type.setTypeName(typeName);
        int state = 0;

        try {
            state = typeService.addType(type);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"添加失败！",state);
        }
        return new Message(Message.SUCCESS,"添加成功！",state);
    }

    @RequestMapping(value = "/updateType", method = RequestMethod.POST)
    @ResponseBody
    public Message updateType(@RequestParam("typeId") int typeId,@RequestParam("typeName") String typeName){
        Type type=new Type();
        type.setTypeId(typeId);
        type.setTypeName(typeName);
        int state = 0;

        try {
            state = typeService.updateType(type);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"修改失败！",state);
        }
        return new Message(Message.SUCCESS,"修改成功！",state);
    }

    @RequestMapping(value = "/deleteType", method = RequestMethod.POST)
    @ResponseBody
    public Message deleteType(@RequestParam("typeId") int typeId){

        int state = 0;

        try {
            state = typeService.deleteType(typeId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"删除失败！",state);
        }
        return new Message(Message.SUCCESS,"删除成功！",state);
    }

    @RequestMapping(value = "/getTypeById", method = RequestMethod.POST)
    @ResponseBody
    public Message getTypeById(@RequestParam("typeId") int typeId){

        Type type = new Type();

        try {
            type = typeService.getTypeById(typeId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"获取信息失败！",null);
        }
        return new Message(Message.SUCCESS,"获取信息成功！",type);
    }


}
