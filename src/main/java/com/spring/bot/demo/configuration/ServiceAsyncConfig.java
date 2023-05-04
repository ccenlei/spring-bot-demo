package com.spring.bot.demo.configuration;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import lombok.Getter;
import lombok.Setter;

@Configuration
@EnableAsync
@ConfigurationProperties(prefix = "demo.service.executor")
@Getter
@Setter
public class ServiceAsyncConfig implements AsyncConfigurer {

    private Integer coreSize;

    private Integer maxSize;

    private Integer queueCapacity;

    private final AtomicInteger counter = new AtomicInteger(0);

    @Bean(name = "serviceExecutor")
    public Executor serviceExecutor() {
        return new ThreadPoolExecutor(coreSize, maxSize, 1, TimeUnit.MINUTES, queue(), threadFactory(), handler());
    }

    BlockingQueue<Runnable> queue() {
        return new LinkedBlockingDeque<>(queueCapacity);
    }

    ThreadFactory threadFactory() {
        return new ThreadFactory() {
            public Thread newThread(Runnable r) {
                String name = String.format("service-executor-%d", counter.incrementAndGet());
                Thread thread = new Thread(r);
                thread.setName(name);
                return thread;
            }
        };
    }

    RejectedExecutionHandler handler() {
        return new ThreadPoolExecutor.CallerRunsPolicy();
    }
}
