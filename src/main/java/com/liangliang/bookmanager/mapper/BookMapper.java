package com.liangliang.bookmanager.mapper;

import com.liangliang.bookmanager.bean.Book;
import com.liangliang.bookmanager.bean.TableMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookMapper {

    public List<Book> getBookList() throws Exception;

    public Integer addBook(Book book) throws Exception;

    public Integer updateBook(Book book) throws Exception;

    public Integer deleteBook(int bookId) throws Exception;

    public Book getBookInfoById(int bookId) throws Exception;

    public List<Book> searchBook(TableMessage tableMessage) throws Exception;

}
