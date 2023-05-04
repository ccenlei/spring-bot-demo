package com.spring.bot.demo.configuration;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.spring.bot.demo.utils.BookBeanMapper;
import com.spring.bot.demo.utils.MintNftBeanMapper;
import com.spring.bot.demo.utils.MintUserBeanMapper;

@Configuration
public class BeanMapperConfig {

    @Bean
    BookBeanMapper bookBeanMapper() {
        return Mappers.getMapper(BookBeanMapper.class);
    }

    @Bean
    MintNftBeanMapper nftBeanMapper() {
        return Mappers.getMapper(MintNftBeanMapper.class);
    }

    @Bean
    MintUserBeanMapper userBeanMapper() {
        return Mappers.getMapper(MintUserBeanMapper.class);
    }
}
