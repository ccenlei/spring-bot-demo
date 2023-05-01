package com.spring.bot.demo.controller;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;
import com.spring.bot.demo.component.LibraryProperties;
import com.spring.bot.demo.entity.Book;
import com.spring.bot.demo.exception.BookException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/book")
@Api(value = "book", tags = "book api")
public class BookController {

    private LibraryProperties library;

    private AtomicInteger ids;

    @PostMapping("/add")
    public ResponseEntity<List<Book>> add(@RequestBody Book book) {
        book.setId(ids.getAndIncrement());
        library.getBooks().add(book);
        return ResponseEntity.ok(library.getBooks());
    }

    @DeleteMapping("/{id}")
    public <T> ResponseEntity<T> deleteById(@PathVariable("id") int id) {
        library.getBooks().stream().filter(book -> id == book.getId())
                .findFirst()
                .ifPresent(del -> library.getBooks().remove(del));
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/name")
    @ApiOperation(value = "Get books", notes = "Get books detail by book name", httpMethod = "GET")
    public ResponseEntity<List<Book>> getByName(@RequestParam("name") String name) {
        List<Book> targets = library.getBooks().stream()
                .filter(book -> name.equals(book.getName()))
                .collect(Collectors.toList());
        if (ObjectUtils.isEmpty(targets)) {
            throw new BookException(ImmutableMap.of("book name:", name));
        }
        return ResponseEntity.ok(targets);
    }

    @Autowired
    public void setLibrary(LibraryProperties library) {
        this.library = library;
        ids = new AtomicInteger(library.getStart());
    }
}
