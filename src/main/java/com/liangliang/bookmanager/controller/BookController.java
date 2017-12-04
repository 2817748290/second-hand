package com.liangliang.bookmanager.controller;

import com.liangliang.bookmanager.bean.Book;
import com.liangliang.bookmanager.bean.Message;
import com.liangliang.bookmanager.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/getBookList", method = RequestMethod.POST)
    @ResponseBody
    public Message getBookList(){

        List<Book> bookList = new ArrayList<>();

        try {
            bookList = bookService.getBookList();
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"获取图书列表失败！",null);
        }
        return new Message(Message.SUCCESS,"获取图书列表成功！",bookList);
    }



}
