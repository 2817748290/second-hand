package com.liangliang.bookmanager.mapper;

import com.liangliang.bookmanager.bean.Book;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookMapper {

    public List<Book> getBookList() throws Exception;

}
