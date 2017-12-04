package com.liangliang.bookmanager.service;

import com.liangliang.bookmanager.bean.Book;


import java.util.List;

public interface BookService {

    public List<Book> getBookList() throws Exception;

}
