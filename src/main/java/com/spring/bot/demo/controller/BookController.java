package com.spring.bot.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.bot.demo.entity.Book;

@RestController
@RequestMapping("/book")
public class BookController {

    private AtomicInteger ids = new AtomicInteger(0);

    private List<Book> books = new ArrayList<>();

    @PostMapping("/add")
    public ResponseEntity<List<Book>> add(@RequestBody Book book) {
        book.setId(ids.incrementAndGet());
        books.add(book);
        return ResponseEntity.ok(books);
    }

    @DeleteMapping("/{id}")
    public <T> ResponseEntity<T> deleteById(@PathVariable("id") int id) {
        books.stream().filter(book -> id == book.getId())
                .findFirst()
                .ifPresent(del -> books.remove(del));
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/name")
    public ResponseEntity<List<Book>> getByName(@RequestParam("name") String name) {
        List<Book> targets = books.stream()
                .filter(book -> name.equals(book.getName()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(targets);
    }
}
