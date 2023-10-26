package com.spring.bot.demo.utils;

import com.spring.bot.demo.entity.Book;

import lombok.var;

/**
 * ThreadLocal Demo
 * 
 * Usually used for thread unsafe object.
 * 
 */
public class BookContext implements AutoCloseable {

    static final ThreadLocal<Book> ctx = new ThreadLocal<>();

    public BookContext(Book book) {
        ctx.set(book);
    }

    public static Book currBook() {
        return ctx.get();
    }

    @Override
    public void close() throws Exception {
        ctx.remove();
    }

    public static void main(String[] args) {
        try (var bctx = new BookContext(Book.builder().id(3).build())) {
            Book book = BookContext.currBook();
            System.out.println(book.getId());
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
