package com.liangliang.bookmanager.service;

import com.liangliang.bookmanager.bean.Right;
import com.liangliang.bookmanager.bean.TableMessage;


public interface RightService {

    public Integer addRight(Right right) throws Exception;

    public TableMessage getInitRights(TableMessage tableMessage) throws Exception;

    public Integer deleteRight(int rightId) throws Exception;

    public Integer updateRight(Right right) throws Exception;

    public Right getRightInfoById(int rightId) throws Exception;

}
