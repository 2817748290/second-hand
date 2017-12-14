package com.liangliang.bookmanager.service;

import com.liangliang.bookmanager.bean.Type;

import java.util.List;

public interface TypeService {
    public List<Type> getTypeList() throws Exception;

    public Integer addType(Type type) throws Exception;

    public Integer updateType(Type type) throws Exception;

    public Integer deleteType(int typeId) throws Exception;

    public Type getTypeById(int typeId) throws Exception;
}
