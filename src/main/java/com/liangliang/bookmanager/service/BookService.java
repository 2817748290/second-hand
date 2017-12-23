package com.liangliang.bookmanager.service;

import com.liangliang.bookmanager.bean.Book;
import com.liangliang.bookmanager.bean.TableMessage;
import com.liangliang.bookmanager.bean.TableMessageForClient;


import java.util.List;

public interface BookService {

    public List<Book> getBookList() throws Exception;

    public Integer addBook(Book book) throws Exception;

    public Integer updateBook(Book book) throws Exception;

    public Integer deleteBook(int bookId) throws Exception;

    public Book getBookInfoById(int bookId) throws Exception;

    public TableMessage searchBook(TableMessage tableMessage) throws Exception;

    public TableMessageForClient getBookListByType(TableMessageForClient tableMessageForClient) throws Exception;

}
