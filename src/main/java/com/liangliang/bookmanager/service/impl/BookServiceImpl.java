package com.liangliang.bookmanager.service.impl;

import com.liangliang.bookmanager.bean.Book;
import com.liangliang.bookmanager.bean.TableMessage;
import com.liangliang.bookmanager.bean.Type;
import com.liangliang.bookmanager.bean.User;
import com.liangliang.bookmanager.mapper.BookMapper;
import com.liangliang.bookmanager.mapper.TypeMapper;
import com.liangliang.bookmanager.mapper.UserMapper;
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

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TypeMapper typeMapper;

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

    @Override
    public Integer addBook(Book book){

        int state = 0;
        try {
            state = bookMapper.addBook(book);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

        return state;
    }

    @Override
    public Integer updateBook(Book book){

        int state = 0;
        try {
            state = bookMapper.updateBook(book);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

        return state;
    }

    @Override
    public Integer deleteBook(int bookId) throws Exception {

        int state = 0;
        try {
            state = bookMapper.deleteBook(bookId);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

        return state;
    }

    @Override
    public Book getBookInfoById(int bookId) {

        Book book = new Book();

        try {
            book = bookMapper.getBookInfoById(bookId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return book;
    }

    @Override
    public TableMessage searchBook(TableMessage tableMessage){

        List<Book> bookList = new ArrayList<>();

        try {
            bookList = bookMapper.getBookAndUserList(tableMessage);

            if(tableMessage.getSearch()!=null){
                if(tableMessage.getSearch().equals("")){
                    bookList = bookMapper.getBookAndUserList(tableMessage);
                    for (Book book : bookList) {
                        Type type = typeMapper.getTypeById(book.getTypeId());
                        book.setType(type);
                        User user = userMapper.getUserById(book.getUserId());
                        book.setUser(user);
                    }
                    tableMessage.setRows(bookList);
                    tableMessage.setTotal(bookMapper.bookCount(tableMessage));
                }else {
                    tableMessage.setSearch("%"+tableMessage.getSearch()+"%");
                    List<Book> searchBookList = bookMapper.searchBook(tableMessage);
                    tableMessage.setRows(searchBookList);
                    for (Book book : searchBookList) {
                        int userId = book.getUserId();
                        System.out.println(userId);
                        int typeId = book.getTypeId();
                        System.out.println(typeId);
                        Type type = typeMapper.getTypeById(typeId);
                        System.out.println(type);
                        book.setType(type);
                        User user = userMapper.getUserById(userId);
                        book.setUser(user);
                    }
                    tableMessage.setTotal(bookMapper.searchBookCount(tableMessage));
                }

            }else {
                tableMessage.setRows(bookList);
                tableMessage.setTotal(bookMapper.bookCount(tableMessage));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return tableMessage;
    }
}
