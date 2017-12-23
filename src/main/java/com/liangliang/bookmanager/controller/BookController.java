package com.liangliang.bookmanager.controller;

import com.liangliang.bookmanager.bean.Book;
import com.liangliang.bookmanager.bean.Message;
import com.liangliang.bookmanager.bean.TableMessage;
import com.liangliang.bookmanager.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @RequestMapping(value = "/addBook", method = RequestMethod.POST)
    @ResponseBody
    public Message addBook(@RequestBody Book book){

        int state = 0;

        try {
            book.setBookDate(new Date());
            state = bookService.addBook(book);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"添加图书信息失败！",state);
        }
            return new Message(Message.SUCCESS,"添加图书信息成功！",state);
    }

    @RequestMapping(value = "/updateBook", method = RequestMethod.POST)
    @ResponseBody
    public Message updateBook(@RequestBody Book book){

        int state = 0;

        try {
            state = bookService.updateBook(book);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"修改图书信息失败！",state);
        }
        return new Message(Message.SUCCESS,"修改图书信息成功！",state);
    }

    @RequestMapping(value = "/deleteBook", method = RequestMethod.POST)
    @ResponseBody
    public Message deleteBook(@RequestParam("bookId") int bookId){

        int state = 0;

        try {
            state = bookService.deleteBook(bookId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"删除图书信息失败！",state);
        }
        return new Message(Message.SUCCESS,"删除图书信息成功！",state);
    }

    @RequestMapping(value = "/getBookInfoById", method = RequestMethod.POST)
    @ResponseBody
    public Message getBookInfoById(@RequestParam("bookId") int bookId){

        Book book = new Book();

        try {
            book = bookService.getBookInfoById(bookId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(Message.ERROR,"获取对应id图书信息失败！",null);
        }
        return new Message(Message.SUCCESS,"获取对应id图书信息成功！！",book);
    }

    @RequestMapping(value = "/getInitBookList", method = RequestMethod.POST)
    @ResponseBody
    public Map searchBook(@RequestBody TableMessage tableMessage) throws Exception {
        return bookService.searchBook(tableMessage).result();
    }

}
