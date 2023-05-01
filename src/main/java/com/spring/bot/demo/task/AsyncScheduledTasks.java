package com.spring.bot.demo.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.spring.bot.demo.component.LibraryProperties;

@Component
@EnableAsync
public class AsyncScheduledTasks {

    @Autowired
    private LibraryProperties library;

    @Scheduled(cron = "1-2 * * * * ? ")
    public void reportLibrary() {
        // todo need to log.
        System.out.println(String.format("Current Thread : %s", Thread.currentThread().getName()));
        System.out.println(library.getBooks());
    }
}
