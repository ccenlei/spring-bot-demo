package com.spring.bot.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hello")
public class HelloWorldController {

    @GetMapping("say")
    public String sayHello(@RequestParam(value = "name", defaultValue = "World")  String name) {
        return String.format("Hello %s!", name);
    }
}
