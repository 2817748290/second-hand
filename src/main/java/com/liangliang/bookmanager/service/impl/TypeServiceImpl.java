package com.liangliang.bookmanager.service.impl;

import com.liangliang.bookmanager.bean.Type;
import com.liangliang.bookmanager.mapper.TypeMapper;
import com.liangliang.bookmanager.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class TypeServiceImpl implements TypeService{

    @Autowired
    private TypeMapper typeMapper;

    @Override
    public List<Type> getTypeList() throws Exception {
        List<Type> typeList = new ArrayList<>();
        try {
            typeList = typeMapper.getTypeList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return typeList;
    }

    @Override
    public Integer addType(Type type) throws Exception {
        int state = 0;
        try {
            state = typeMapper.addType(type);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

        return state;
    }

    @Override
    public Integer updateType(Type type) throws Exception {
        int state = 0;
        try {
            state = typeMapper.updateType(type);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

        return state;
    }

    @Override
    public Integer deleteType(int typeId) throws Exception {
        int state = 0;
        try {
            state = typeMapper.deleteType(typeId);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

        return state;
    }

    @Override
    public Type getTypeById(int typeId) throws Exception {
       Type type = new Type();

        try {
            type = typeMapper.getTypeById(typeId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return type;
    }
}
