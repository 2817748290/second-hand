package com.liangliang.bookmanager.mapper;

import com.liangliang.bookmanager.bean.Type;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TypeMapper {
    public List<Type> getTypeList() throws Exception;

    public Integer addType(Type type) throws Exception;

    public Integer updateType(Type type) throws Exception;

    public Integer deleteType(int typeId) throws Exception;

    public Type getTypeById(int typeId) throws Exception;
}
