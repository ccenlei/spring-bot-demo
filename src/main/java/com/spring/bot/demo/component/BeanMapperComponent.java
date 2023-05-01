package com.spring.bot.demo.component;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.spring.bot.demo.utils.BookBeanMapper;
import com.spring.bot.demo.utils.MintNftBeanMapper;
import com.spring.bot.demo.utils.MintUserBeanMapper;

@Component
public class BeanMapperComponent {

    @Bean
    public BookBeanMapper bookBeanMapper() {
        return Mappers.getMapper(BookBeanMapper.class);
    }

    @Bean
    public MintNftBeanMapper nftBeanMapper() {
        return Mappers.getMapper(MintNftBeanMapper.class);
    }

    @Bean
    public MintUserBeanMapper userBeanMapper() {
        return Mappers.getMapper(MintUserBeanMapper.class);
    }
}
