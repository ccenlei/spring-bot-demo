package com.spring.bot.demo.utils;

import org.mapstruct.Mapper;

import com.spring.bot.demo.dto.BookDto;
import com.spring.bot.demo.entity.Book;

@Mapper
public interface BookBeanMapper {

    BookDto tDto(Book book);

    Book toSource(BookDto bDto);
}
