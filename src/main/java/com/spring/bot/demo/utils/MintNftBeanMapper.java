package com.spring.bot.demo.utils;

import org.mapstruct.Mapper;

import com.spring.bot.demo.dto.MintNftDto;
import com.spring.bot.demo.entity.MintNft;

@Mapper
public interface MintNftBeanMapper {

    MintNftDto tDto(MintNft nft);

    MintNft toSource(MintNftDto nftDto);
}
