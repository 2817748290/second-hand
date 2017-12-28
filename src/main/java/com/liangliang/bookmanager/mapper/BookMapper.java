package com.liangliang.bookmanager.mapper;

import com.liangliang.bookmanager.bean.Book;
import com.liangliang.bookmanager.bean.TableMessage;
import com.liangliang.bookmanager.bean.TableMessageForClient;
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

    public Integer searchBookCount(TableMessage tableMessage) throws Exception;

    public List<Book> getBookAndUserList(TableMessage tableMessage) throws Exception;

    public Integer bookCount(TableMessage tableMessage) throws Exception;

}
