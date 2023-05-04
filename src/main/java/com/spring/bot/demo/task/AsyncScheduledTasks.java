package com.spring.bot.demo.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.spring.bot.demo.component.LibraryProperties;

import lombok.extern.slf4j.Slf4j;

@Component
@EnableAsync
@Slf4j
public class AsyncScheduledTasks {

    @Autowired
    private LibraryProperties library;

    @Scheduled(cron = "1-2 * * * * ? ")
    public void reportLibrary() {
        log.info(String.format("Current Thread : %s", Thread.currentThread().getName()));
        log.info(library.getBooks().toString());
    }
}
