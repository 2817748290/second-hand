package com.liangliang.bookmanager.service.impl;

import com.liangliang.bookmanager.bean.Book;
import com.liangliang.bookmanager.mapper.BookMapper;
import com.liangliang.bookmanager.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookServiceImpl implements BookService{

    @Autowired
    private BookMapper bookMapper;

    @Override
    public List<Book> getBookList() {

        List<Book> bookList = new ArrayList<>();
        try {
            bookList = bookMapper.getBookList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return bookList;
    }
}
