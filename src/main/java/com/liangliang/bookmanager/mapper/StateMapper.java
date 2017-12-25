package com.liangliang.bookmanager.mapper;

import com.liangliang.bookmanager.bean.State;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StateMapper {

   public State getStateInfoById(int stateId) throws Exception;

}
