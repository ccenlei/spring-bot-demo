package com.spring.bot.demo.utils;

import org.mapstruct.Mapper;

import com.spring.bot.demo.dto.MintUserDto;
import com.spring.bot.demo.entity.MintUser;

@Mapper
public interface MintUserBeanMapper {

    MintUserDto tDto(MintUser user);

    MintUser toSource(MintUserDto userDto);
}
