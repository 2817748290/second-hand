package com.liangliang.bookmanager.mapper;

import com.liangliang.bookmanager.bean.Admin;
import com.liangliang.bookmanager.bean.Right;
import com.liangliang.bookmanager.bean.TableMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RightMapper {

    public Integer addRight(Right right) throws Exception;

    public List<Right> getInitRights(TableMessage tableMessage) throws Exception;

    public Integer getInitRightsCount(TableMessage tableMessage) throws Exception;

    public Integer deleteRight(int rightId) throws Exception;

    public Integer updateRight(Right right) throws Exception;

    public Right getRightInfoById(int rightId) throws Exception;

}
