package com.spring.bot.demo.lombok;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import lombok.SneakyThrows;

/**
 * like this
 * 
 * public void read() throws FileNotFoundException {
 * InputStream inputStream = new FileInputStream("");
 * }
 * 
 * public void write() throws UnsupportedEncodingException {
 * throw new UnsupportedEncodingException();
 * }
 * 
 */
public class SneakyThrowsDemo {

    @SneakyThrows
    public void read() {
        InputStream is = new FileInputStream("");
        is.close();
    }

    @SneakyThrows
    public void write() {
        throw new UnsupportedEncodingException(null);
    }
}
