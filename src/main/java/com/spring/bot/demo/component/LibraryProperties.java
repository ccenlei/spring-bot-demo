package com.spring.bot.demo.component;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.spring.bot.demo.entity.Book;

import lombok.Getter;
import lombok.Setter;

/**
 * @PropertySource: this this annotation means read library.properties file from resources dir.
 * 
 * @ConfigurationProperties: this this annotation means load properties with the prefix = "library"
 */
@Component
@PropertySource("classpath:library.properties")
@ConfigurationProperties(prefix = "library")
@Getter
@Setter
public class LibraryProperties {

    private String location;

    private Integer start;

    private List<Book> books;

}
